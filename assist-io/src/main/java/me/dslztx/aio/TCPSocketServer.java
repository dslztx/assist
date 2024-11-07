package me.dslztx.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPSocketServer {

    private static final int PORT = 56000;

    public static void main(String[] args) throws Exception {

        // 事件处理线程池
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, new SynchronousQueue<>());
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPoolExecutor);

        AsynchronousServerSocketChannel asyncServerSocketChannel = AsynchronousServerSocketChannel.open(group);

        // 监听端口，允许所有来源IP
        asyncServerSocketChannel.bind(new InetSocketAddress("0.0.0.0", PORT));

        // 监听一个TCP Socket连接建立事件
        asyncServerSocketChannel.accept(new AcceptAttachment(asyncServerSocketChannel), new AcceptEventHandler());

        // 保持主线程存活，避免进程退出
        TimeUnit.MINUTES.sleep(30);
    }
}

class AcceptAttachment {
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AcceptAttachment(AsynchronousServerSocketChannel asynchronousServerSocketChannel) {
        this.asynchronousServerSocketChannel = asynchronousServerSocketChannel;
    }

    public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
        return asynchronousServerSocketChannel;
    }
}

/**
 * 处理TCP Socket连接建立事件
 */
class AcceptEventHandler implements CompletionHandler<AsynchronousSocketChannel, AcceptAttachment> {

    /**
     * 
     * @param asynchronousSocketChannel 建立的TCP Socket实例对象
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel asynchronousSocketChannel, AcceptAttachment attachment) {

        registerAgain(attachment);

        // TCP Socket连接建立后，生成一个实例对象asynchronousSocketChannel

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        ServerReadAttachment serverReadAttachment = new ServerReadAttachment(asynchronousSocketChannel, readBuffer);

        // 监听一个TCP Socket数据读取完成事件
        asynchronousSocketChannel.read(readBuffer, serverReadAttachment, new ReadEventHandler());
    }

    /**
     * 一次注册，一次响应：每次都要重新注册监听TCP Socket连接建立事件，但是由于“文件状态标示符”是独享的，所以不需要担心有“漏掉的”事件
     */
    private void registerAgain(AcceptAttachment attachment) {
        // 传递AsynchronousSocketChannel实例对象的唯一用途
        attachment.getAsynchronousServerSocketChannel().accept(attachment, this);
    }

    @Override
    public void failed(Throwable exc, AcceptAttachment attachment) {
        exc.printStackTrace();
    }
}

class ReadEventHandler implements CompletionHandler<Integer, ServerReadAttachment> {

    @Override
    public void completed(Integer result, ServerReadAttachment serverReadAttachment) {
        // 如果条件成立，说明客户端主动终止了TCP Socket连接，这时服务端终止就可以了
        if (result == -1) {
            try {
                serverReadAttachment.getAsynchronousSocketChannel().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        ByteBuffer byteBuffer = serverReadAttachment.getReadBuffer();
        System.out.println("has read data: " + new String(byteBuffer.array(), 0, result));

        serverReadAttachment.getAsynchronousSocketChannel()
            .write(ByteBuffer.wrap("hello client, I am server".getBytes(StandardCharsets.UTF_8)));

        // 继续复用
        byteBuffer.clear();

        // 还要继续监听(一次监听一次通知)
        serverReadAttachment.getAsynchronousSocketChannel().read(byteBuffer, serverReadAttachment, this);
    }

    @Override
    public void failed(Throwable exc, ServerReadAttachment serverReadAttachment) {
        try {
            System.out.println("=====发现客户端异常关闭，服务器将关闭TCP通道");
            serverReadAttachment.getAsynchronousSocketChannel().close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

class ServerReadAttachment {
    private AsynchronousSocketChannel asynchronousSocketChannel;

    private ByteBuffer readBuffer;

    public ServerReadAttachment(AsynchronousSocketChannel asynchronousSocketChannel, ByteBuffer readBuffer) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.readBuffer = readBuffer;
    }

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

}
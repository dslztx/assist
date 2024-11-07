package me.dslztx.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

public class TCPSocketClient {

    private static final int PORT = 56000;

    public static void main(String[] args) {

        AsynchronousSocketChannel client = null;
        try {
            // 事件处理线程池
            ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, new SynchronousQueue<>());
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPoolExecutor);

            client = AsynchronousSocketChannel.open(group);

            Future<Void> result = client.connect(new InetSocketAddress("127.0.0.1", PORT));
            System.out.println("waiting for socket connected");
            result.get();

            // 发
            String reqMessage = "hello server, I am client (" + client.hashCode() + ")";
            ByteBuffer reqBuffer = ByteBuffer.wrap(reqMessage.getBytes(StandardCharsets.UTF_8));
            client.write(reqBuffer, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("send success, the sent byte array size is " + result);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.err.println("send fail" + exc);
                }
            });

            // 收
            ByteBuffer resBuffer = ByteBuffer.allocate(1024);

            client.read(resBuffer, new ClientReadAttachment(resBuffer),
                new CompletionHandler<Integer, ClientReadAttachment>() {
                    @Override
                    public void completed(Integer result, ClientReadAttachment attachment) {
                        System.out.println("the server response is: "
                            + new String(attachment.getByteBuffer().array(), 0, result, StandardCharsets.UTF_8));
                    }

                    @Override
                    public void failed(Throwable exc, ClientReadAttachment attachment) {
                        System.err.println("fail to receive server response" + exc);
                    }
                });

            // 避免Java进程退出
            TimeUnit.MINUTES.sleep(30);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(client)) {
                    client.close();
                }
            } catch (Exception ignored) {

            }
        }
    }
}

class ClientReadAttachment {

    ByteBuffer byteBuffer;

    public ClientReadAttachment(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

}
package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GroupChatServer {

    public static final int PORT = 6667;

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    public GroupChatServer() {
        try {
            // 打开一个选择器
            selector = Selector.open();

            // 打开serverSocketChannel
            serverSocketChannel = ServerSocketChannel.open();

            // 绑定地址，端口号
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", PORT));

            // 设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            // 把serverSocketChannel注册到选择器中，关注SelectionKey.OP_ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatServer chatServer = new GroupChatServer();

        // 启动服务器，监听
        chatServer.listen();
    }

    /**
     * 监听，接受客户端连接，读取客户端信息并转发到其他客户端
     */
    public void listen() {
        try {
            while (true) {
                // 获取监听的事件总数
                int count = selector.select(2000);
                if (count > 0) {
                    // 获取SelectionKey集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> it = selectionKeys.iterator();

                    while (it.hasNext()) {
                        // 依次处理事件
                        SelectionKey key = it.next();

                        // 如果是获取连接事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // 设置为非阻塞
                            socketChannel.configureBlocking(false);
                            // socketChannel注册到选择器中，关注SelectionKey.OP_READ事件
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }

                        // 如果是读就绪事件
                        if (key.isReadable()) {
                            // 读取客户端消息，并且转发到其他客户端
                            readDataAndForwardToAllOtherClients(key);
                        }

                        // 这个事件已经处理好
                        it.remove();
                    }
                } else {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取客户端发送过来的消息，并转发给其他所有客户端
    private void readDataAndForwardToAllOtherClients(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            // 从selectionKey中获取channel
            socketChannel = (SocketChannel)selectionKey.channel();

            // 创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            // 读取通道数据，count是实际读取到数据字节大小
            int count = socketChannel.read(byteBuffer);

            // count>0才表示实际读取到数据
            if (count > 0) {
                String msg = new String(byteBuffer.array(), 0, count, StandardCharsets.UTF_8);

                // 输出该消息到控制台
                System.out.println(msg);

                // 转发到其他客户端，不发送给socketChannel自己
                notifyAllOtherClients(msg, socketChannel);
            }
        } catch (Exception e) {
            try {
                // 取消注册
                selectionKey.cancel();
                // 关闭流
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 转发消息到其他客户端，排除noNotifyChannel
     */
    private void notifyAllOtherClients(String msg, SocketChannel noNotifyChannel) throws Exception {
        for (SelectionKey selectionKey : selector.keys()) {
            Channel channel = selectionKey.channel();

            // channel的实际类型须是SocketChannel，并且排除不需要通知的通道
            if (channel instanceof SocketChannel && channel != noNotifyChannel) {

                SocketChannel socketChannel = (SocketChannel)channel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                socketChannel.write(byteBuffer);
            }
        }
    }
}
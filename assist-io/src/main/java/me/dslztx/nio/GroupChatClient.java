package me.dslztx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {

    private Selector selector;

    private SocketChannel socketChannel;

    private String clientName;

    public GroupChatClient() {
        try {
            // 打开选择器
            selector = Selector.open();

            // 连接服务器
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", GroupChatServer.PORT));

            // 设置为非阻塞
            socketChannel.configureBlocking(false);

            // socketChannel注册到选择器中，关注SelectionKey.OP_READ事件
            socketChannel.register(selector, SelectionKey.OP_READ);

            // 获取客户名
            clientName = "[客户端-" + socketChannel.getLocalAddress().toString().substring(1) + "]";
            System.out.println(clientName + " is ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatClient chatClient = new GroupChatClient();

        // 启动线程，读取服务器转发过来的消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readMsg();

                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 主线程发送消息到服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }

    // 发送消息到服务端
    private void sendMsg(String msg) {
        msg = clientName + "说： " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取服务端发送过来的消息
    private void readMsg() {
        try {
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    // 判断是读就绪事件
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel)selectionKey.channel();
                        // 创建一个缓冲区
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        // 从通道中读取数据到缓冲区，actualLen是实际读取到数据字节大小
                        int actualLen = channel.read(byteBuffer);

                        // 缓冲区的数据，转成字符串，并打印
                        System.out.println(new String(byteBuffer.array(), 0, actualLen, StandardCharsets.UTF_8));
                    }

                    // 该事件已经处理好
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
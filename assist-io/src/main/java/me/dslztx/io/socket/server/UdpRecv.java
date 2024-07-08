package me.dslztx.io.socket.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * https://blog.csdn.net/swadian2008/article/details/123273552
 */
public class UdpRecv {

    /**
     * 中断标记
     */
    public static final String QUIT = "quit";

    public static void main(String[] args) throws Exception {
        // 创建套接字
        DatagramSocket ds = new DatagramSocket(3000);
        byte[] buf = new byte[1024];
        while (true) {
            // 创建接收消息数据包
            DatagramPacket recive = new DatagramPacket(buf, 1024);
            // 接收消息，如果没有消息，进入阻塞状态
            ds.receive(recive);
            String strRecv = new String(recive.getData(), 0, recive.getLength()) + " from "
                + recive.getAddress().getHostAddress() + ":" + recive.getPort();
            // 打印接收到的消息
            System.out.println(strRecv);

            System.out.println("请输入：");
            Scanner sc = new Scanner(System.in);
            String next = sc.next();
            if (next.equals(QUIT)) {
                ds.close();
                System.out.println("程序退出...");
                break;
            }

            // 创建发送消息数据包
            DatagramPacket send = new DatagramPacket(next.getBytes(), next.length(), InetAddress.getByName("127.0.0.1"),
                recive.getPort());
            // 发送消息
            ds.send(send);
            System.out.println("消息已经发送...");
        }
    }
}
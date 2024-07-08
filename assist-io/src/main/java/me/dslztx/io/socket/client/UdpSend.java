package me.dslztx.io.socket.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpSend {

    public static final String QUIT = "quit";

    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        byte[] buf = new byte[1024];

        while (true) {
            System.out.println("请输入：");
            Scanner sc = new Scanner(System.in);
            String next = sc.next();
            if (next.equals(QUIT)) {
                // 发送通知退出消息
                DatagramPacket send =
                    new DatagramPacket(next.getBytes(), next.length(), InetAddress.getByName("127.0.0.1"), 3000);
                ds.send(send);
                ds.close();
                System.out.println("程序退出...");
                break;
            }
            // 发送消息数据包
            DatagramPacket send =
                new DatagramPacket(next.getBytes(), next.length(), InetAddress.getByName("127.0.0.1"), 3000);
            // 发送消息
            ds.send(send);
            System.out.println("消息已经发送...");

            // 接收消息数据包
            DatagramPacket recive = new DatagramPacket(buf, 1024);
            // 接收消息
            ds.receive(recive);
            String strRecv = new String(recive.getData(), 0, recive.getLength()) + " from "
                + send.getAddress().getHostAddress() + ":" + recive.getPort();
            System.out.println(strRecv);
        }
    }
}
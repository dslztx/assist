package me.dslztx.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient {

    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = "hello udp server, i am udp client".getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet =
            new DatagramPacket(sendData, 0, sendData.length, InetAddress.getByName("127.0.0.1"), 8888);
        socket.send(packet);

        byte[] rcvData = new byte[1000];

        DatagramPacket inPacket = new DatagramPacket(rcvData, 0, rcvData.length);

        socket.receive(inPacket);

        // 将接收到的字节数组转为对应的字符串
        String msg = new String(inPacket.getData(), 0, inPacket.getLength(), StandardCharsets.UTF_8);

        System.out.println(msg);
    }

}
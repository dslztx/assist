package me.dslztx.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(8888);

        while (true) {
            DatagramPacket rcvPacket = new DatagramPacket(new byte[1000], 1000);
            socket.receive(rcvPacket);

            byte[] data = rcvPacket.getData();

            System.out.println(new String(data, 0, rcvPacket.getLength(), StandardCharsets.UTF_8));

            DatagramPacket sendPacket =
                new DatagramPacket(new byte[1000], 1000, rcvPacket.getAddress(), rcvPacket.getPort());

            sendPacket.setData("hello udp client, i am udp server".getBytes(StandardCharsets.UTF_8));

            socket.send(sendPacket);
        }
    }
}
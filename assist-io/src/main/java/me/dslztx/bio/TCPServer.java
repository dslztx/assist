package me.dslztx.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);

            while (true) {
                Socket socket = ss.accept();

                new Thread(new SocketHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SocketHandler implements Runnable {
    Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            byte[] data = new byte[1000];
            InputStream in = socket.getInputStream();
            int actualLen = in.read(data);

            System.out.println("the client talk is " + new String(data, 0, actualLen, StandardCharsets.UTF_8));

            OutputStream out = socket.getOutputStream();
            out.write("hello client, i am server".getBytes(StandardCharsets.UTF_8));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

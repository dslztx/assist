package me.dslztx.io.socket.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPSocketClient {

    private Socket socket;

    public TCPSocketClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    public TCPSocketClient(String host, int port, int timeout) throws IOException {
        socket = new Socket(host, port);

        // 毫秒
        socket.setSoTimeout(timeout);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Socket socket = null;
        try {
            System.out.println("create " + System.currentTimeMillis());
            socket = new Socket("127.0.0.1", 10020);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream out = socket.getOutputStream();

        for (int i = 0; i < 100; i++) {
            out.write("hello world".getBytes());
            out.flush();
        }

        Thread.sleep(10000);

        System.out.println("terminate " + System.currentTimeMillis());

        socket.shutdownOutput();

        Thread.sleep(10000);

        socket.close();

        Thread.sleep(100000);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

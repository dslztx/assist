package me.dslztx.assist.client.socket;

import java.io.IOException;
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

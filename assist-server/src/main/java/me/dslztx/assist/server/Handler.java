package me.dslztx.assist.server;

import java.net.Socket;

public abstract class Handler implements Runnable {

    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        handle(socket);
    }

    abstract void handle(Socket socket);
}

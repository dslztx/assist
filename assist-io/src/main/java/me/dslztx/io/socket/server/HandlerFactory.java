package me.dslztx.io.socket.server;

import java.net.Socket;

public interface HandlerFactory {
    Handler createHandler(Socket socket);
}

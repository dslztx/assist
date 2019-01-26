package me.dslztx.assist.server;

import java.net.Socket;

public interface HandlerFactory {
    Handler createHandler(Socket socket);
}

package me.dslztx.assist.server;

import static me.dslztx.assist.util.ObjectAssist.isNotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(TCPSocketServer.class);

    String host;

    int port;

    int requestQueueLenMax = 100;

    int processThreadNumMax = 10;

    int rejectProcessThreadNumMax = 10;

    volatile boolean running = false;

    public TCPSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public TCPSocketServer(String host, int port, int requestQueueLenMax, int processThreadNumMax,
        int rejectProcessThreadNumMax) {
        this.host = host;
        this.port = port;
        this.requestQueueLenMax = requestQueueLenMax;
        this.processThreadNumMax = processThreadNumMax;
        this.rejectProcessThreadNumMax = rejectProcessThreadNumMax;
    }

    public void start(final HandlerFactory handlerFactory, final HandlerFactory rejectHandlerFactory) {
        if (!running) {
            synchronized (this) {
                if (!running) {
                    try {
                        SocketAddress endpoint =
                            (host == null) ? new InetSocketAddress(port) : new InetSocketAddress(host, port);

                        final ServerSocket ss = new ServerSocket();
                        ss.bind(endpoint, requestQueueLenMax);

                        final ScheduledExecutorService threadPool = obtainThreadPool();

                        final ScheduledExecutorService rejectThreadPool = obtainRejectThreadPool();

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    Socket socket = null;
                                    try {
                                        socket = ss.accept();
                                    } catch (IOException e) {
                                        logger.error("", e);
                                        continue;
                                    }

                                    try {
                                        threadPool.execute(handlerFactory.createHandler(socket));
                                    } catch (RejectedExecutionException e) {
                                        logger.error("", e);
                                        if (isNotNull(rejectHandlerFactory)) {
                                            rejectThreadPool.execute(rejectHandlerFactory.createHandler(socket));
                                        }
                                    }
                                }
                            }
                        });

                        thread.start();
                    } catch (Exception e) {
                        logger.error("", e);
                    } finally {
                        running = true;
                    }
                }
            }
        }
    }

    private ScheduledExecutorService obtainRejectThreadPool() {
        return Executors.newScheduledThreadPool(rejectProcessThreadNumMax, new ThreadFactory() {

            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "TCPSocketServerRejectProcessThread");
                t.setDaemon(true);
                return t;
            }

        });
    }

    private ScheduledExecutorService obtainThreadPool() {
        return Executors.newScheduledThreadPool(processThreadNumMax, new ThreadFactory() {

            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "TCPSocketServerProcessThread");
                t.setDaemon(true);
                return t;
            }

        });
    }
}

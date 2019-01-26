package me.dslztx.assist.server;

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

    int maxRequest;

    int maxProcessThreads;

    int maxRejectProcessThreads;

    volatile boolean running = false;

    public TCPSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public TCPSocketServer(String host, int port, int maxRequest, int maxProcessThreads, int maxRejectProcessThreads) {
        this.host = host;
        this.port = port;
        this.maxRequest = maxRequest;
        this.maxProcessThreads = maxProcessThreads;
        this.maxRejectProcessThreads = maxRejectProcessThreads;
    }

    public void start(final HandlerFactory handlerFactory, final HandlerFactory rejectHandlerFactory) {
        if (!running) {
            synchronized (this) {
                if (!running) {
                    try {
                        SocketAddress endpoint =
                            (host == null) ? new InetSocketAddress(port) : new InetSocketAddress(host, port);

                        final ServerSocket ss = new ServerSocket();
                        ss.bind(endpoint, maxRequest);

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
                                        if (rejectHandlerFactory != null) {
                                            rejectThreadPool.execute(rejectHandlerFactory.createHandler(socket));
                                        }
                                    }
                                }
                            }
                        });

                        thread.start();
                    } catch (Exception e) {
                    } finally {
                        running = true;
                    }
                }
            }
        }
    }

    private ScheduledExecutorService obtainRejectThreadPool() {
        ScheduledExecutorService threadPool =
            Executors.newScheduledThreadPool(maxRejectProcessThreads, new ThreadFactory() {

                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "FixedRateScheduleThread");
                    t.setDaemon(true);
                    return t;
                }
            });
        return threadPool;
    }

    private ScheduledExecutorService obtainThreadPool() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(maxProcessThreads, new ThreadFactory() {

            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "FixedRateScheduleThread");
                t.setDaemon(true);
                return t;
            }
        });
        return threadPool;
    }
}

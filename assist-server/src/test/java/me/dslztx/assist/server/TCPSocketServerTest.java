package me.dslztx.assist.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPSocketServerTest {

    private static final Logger logger = LoggerFactory.getLogger(TCPSocketServer.class);

    @Test
    public void test() {
        try {
            TCPSocketServer tcpSocketServer = new TCPSocketServer("127.0.0.1", 10020);
            tcpSocketServer.start(new HandlerFactoryTest(), null);

            Thread.sleep(10000);

            multiClients();

            Thread.sleep(1000000);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    private void multiClients() {
        for (int index = 0; index < 10; index++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("127.0.0.1", 10020);
                        socket.setSoTimeout(5000);
                        OutputStream out = socket.getOutputStream();

                        String s = "hello world";

                        out.write(s.getBytes());

                        out.flush();

                        s = "this is my first socket program";
                        out.write(s.getBytes());
                        out.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

class HandlerFactoryTest implements HandlerFactory {

    @Override
    public Handler createHandler(Socket socket) {
        return new HandlerTest(socket);
    }
}

class HandlerTest extends Handler {

    private static final Logger logger = LoggerFactory.getLogger(HandlerTest.class);

    public HandlerTest(Socket socket) {
        super(socket);
    }

    @Override
    void handle(Socket socket) {
        try {
            InputStream in = socket.getInputStream();

            int loop = 20;
            while (true) {
                if (loop == 0)
                    break;

                byte[] bb = new byte[1024];

                int cnt = 0;
                while ((cnt = in.read(bb)) != -1) {
                    System.out.println("test cnt" + cnt);
                    System.out.println(new String(bb, 0, cnt));
                }

                loop--;

                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
package me.dslztx.assist.server;

import java.io.InputStream;
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

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
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
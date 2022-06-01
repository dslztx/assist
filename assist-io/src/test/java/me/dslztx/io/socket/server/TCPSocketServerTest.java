package me.dslztx.io.socket.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.CloseableAssist;
import me.dslztx.io.socket.client.TCPSocketClient;

@Ignore
public class TCPSocketServerTest {

    private static final Logger logger = LoggerFactory.getLogger(TCPSocketServer.class);

    @Test
    public void test() {
        try {
            TCPSocketServer tcpSocketServer = new TCPSocketServer("127.0.0.1", 10020);
            tcpSocketServer.start(new HandlerFactoryTest(), null);

            Thread.sleep(10000);

            multiClients();

            Thread.sleep(100000);
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
                    OutputStream out = null;
                    InputStream in = null;

                    try {
                        TCPSocketClient tcpSocketClient = new TCPSocketClient("127.0.0.1", 10020, 5000);

                        Socket socket = tcpSocketClient.getSocket();

                        out = socket.getOutputStream();

                        String s = "hello world";

                        out.write(s.getBytes());

                        out.flush();

                        s = "this is my first socket program";
                        out.write(s.getBytes());
                        out.flush();

                        socket.shutdownOutput();

                        in = socket.getInputStream();

                        // socket.shutdownInput();

                        byte[] bb = new byte[1024];

                        int cnt = 0;
                        while ((cnt = in.read(bb)) != -1) {
                            System.out.println(new String(bb, 0, cnt));
                        }

                        socket.close();
                    } catch (Exception e) {
                        logger.error("", e);
                    } finally {
                        CloseableAssist.closeQuietly(in);
                        CloseableAssist.closeQuietly(out);
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
        InputStream in = null;
        OutputStream out = null;

        try {
            in = socket.getInputStream();

            byte[] bb = new byte[1024];

            int cnt = 0;
            while ((cnt = in.read(bb)) != -1) {
                System.out.println(new String(bb, 0, cnt));
            }

            out = socket.getOutputStream();
            out.write("socket finish".getBytes());
            out.flush();

            socket.shutdownOutput();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.closeQuietly(in);
            CloseableAssist.closeQuietly(out);
        }
    }
}
package me.dslztx.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 8888);

            OutputStream out = s.getOutputStream();
            out.write("hello server, i am client".getBytes(StandardCharsets.UTF_8));
            out.flush();

            InputStream in = s.getInputStream();
            byte[] data = new byte[1000];
            int actualLen = in.read(data);
            System.out.println("the server talk is " + new String(data, 0, actualLen, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

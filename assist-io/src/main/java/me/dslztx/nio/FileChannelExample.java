package me.dslztx.nio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;

public class FileChannelExample {

    public static void main(String[] args) {

        write();

        read();
    }

    public static void write() {
        try {
            Files.createFile(Paths.get("/tmp/a.out"));

            FileChannel channel = FileChannel.open(Paths.get("/tmp/a.out"), StandardOpenOption.WRITE);

            ByteBuffer buf = ByteBuffer.wrap("Hello, Java NIO.".getBytes(StandardCharsets.UTF_8));

            channel.write(buf);

            channel.force(false);
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void read() {
        try {
            FileChannel channel = FileChannel.open(Paths.get("/tmp/a.out"), StandardOpenOption.READ);

            ByteBuffer buf = ByteBuffer.allocate(1024);

            int actualLen;
            while ((actualLen = channel.read(buf)) != -1) {
                System.out.print(new String(buf.array(), 0, actualLen, StandardCharsets.UTF_8));
                buf.clear();
            }
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

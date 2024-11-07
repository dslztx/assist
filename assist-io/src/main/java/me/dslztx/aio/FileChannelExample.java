package me.dslztx.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class FileChannelExample {

    public static void main(String[] args) throws InterruptedException {
        write();

        read();

        TimeUnit.SECONDS.sleep(30);
    }

    public static void write() {
        try {
            Path path = Paths.get("/tmp/b.out");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

            ByteBuffer writeBuffer = ByteBuffer.wrap("test data".getBytes(StandardCharsets.UTF_8));

            fileChannel.write(writeBuffer, 0, null, new CompletionHandler<Integer, Object>() {

                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println(result + " bytes is written");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read() {
        try {

            Path path = Paths.get("/tmp/b.out");

            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            long position = 0;

            fileChannel.read(readBuffer, position, new FileAttachment(readBuffer),
                new CompletionHandler<Integer, FileAttachment>() {
                    @Override
                    public void completed(Integer result, FileAttachment attachment) {
                        System.out.println("the read content is "
                            + new String(attachment.byteBuffer.array(), 0, result, StandardCharsets.UTF_8));
                    }

                    @Override
                    public void failed(Throwable exc, FileAttachment attachment) {
                        exc.printStackTrace();
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class FileAttachment {
    ByteBuffer byteBuffer;

    public FileAttachment(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }
}
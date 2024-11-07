package me.dslztx.bio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class FileExample {

    public static void main(String[] args) {
        try {
            File file = new File("/tmp/a.out");

            FileOutputStream out = new FileOutputStream(file);
            out.write("hello".getBytes(StandardCharsets.UTF_8));
            out.flush();

            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[1000];
            int actualLen = in.read(data);

            System.out.println(new String(data, 0, actualLen, StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

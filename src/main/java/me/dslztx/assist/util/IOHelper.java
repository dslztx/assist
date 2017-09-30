package me.dslztx.assist.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * IO工具类
 * 
 * @date 2015年5月8日
 * @author dslztx
 * @description 要记得关闭流
 */
public class IOHelper {
    /**
     * 以文件为流源头，打开一个缓冲读字节流
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static BufferedInputStream bufferedInputStream(File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * 以文件为流目的，打开一个缓冲写字节流
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static BufferedOutputStream bufferedOutputStream(File file) throws IOException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    /**
     * 以文件为流源头，打开一个缓冲读字符流
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static BufferedReader bufferedReader(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    /**
     * 以文件为流目的，打开一个缓冲写字符流
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static BufferedWriter bufferedWriter(File file) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }

    /**
     * 以字节数组的形式从一个缓冲读字节流中读取数据，本方法封装了读取下一个字节数组的逻辑
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] nextBytes(BufferedInputStream in) throws IOException {
        byte[] bytes = new byte[100];
        int num;
        num = in.read(bytes);
        if (num == -1)
            return null;
        if (num < 100) {
            byte[] result = new byte[num];
            for (int index = 0; index < num; index++)
                result[index] = bytes[index];
        }
        return bytes;
    }

    /**
     * 以字节数组的形式将数据写到缓冲写字节流中
     * 
     * @param out
     * @param bytes
     * @throws IOException
     */
    public static void writeBytes(BufferedOutputStream out, byte[] bytes) throws IOException {
        out.write(bytes);
    }


    /**
     * 以字符串的形式从一个缓冲读字符流中读取数据，本方法封装了读取下一行字符串的逻辑
     * 
     * @param reader
     * @return
     * @throws IOException
     */
    public static String nextStringLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * 以字符串的形式将数据写到缓冲写字符流中
     * 
     * @param writer
     * @param line
     * @throws IOException
     */
    public static void writeStringLine(BufferedWriter writer, String line) throws IOException {
        //"\r\n"是可靠的换行方式
        writer.write(line + "\r\n");
    }
}

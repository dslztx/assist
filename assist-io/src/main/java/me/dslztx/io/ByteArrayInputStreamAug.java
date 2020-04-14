package me.dslztx.io;

import java.io.ByteArrayInputStream;

/**
 * @author dslztx
 */
public class ByteArrayInputStreamAug extends ByteArrayInputStream {

    public ByteArrayInputStreamAug(byte[] buf) {
        super(buf);
    }

    public ByteArrayInputStreamAug(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public static void main(String[] args) {
        byte b = -1;

        int a = b & 0xff;

        System.out.println(a);

        int c = 255;

        System.out.println((byte)c);

        char d = (char)-3;

        System.out.println(d);

        byte[] aa = new byte[3];

        ByteArrayInputStream in = new ByteArrayInputStream(aa);

        System.out.println(in.read());
        System.out.println(in.read());
        System.out.println(in.read());
        System.out.println(in.read());
        System.out.println(in.read());
        System.out.println(in.read());

    }

    /**
     * 读取到\r\n为止
     */
    public String readLineToCRLF() {
        int start = pos;

        int a = -1;
        int b = -1;

        while ((a = read()) != -1) {
            if (a == '\r') {
                b = read();
                if (b != -1 && b == '\n') {
                    break;
                }
            }
        }

        if (a == '\r' && b == '\n') {
            return new String(buf, start, pos - 2 - start);
        } else {
            throw new RuntimeException("no new legal line");
        }
    }

    public ByteArrayInputStream createUsingRemain() {
        return new ByteArrayInputStream(buf, pos, count - pos);
    }

    public int remainLength() {
        return count - pos;
    }
}

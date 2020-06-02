package me.dslztx.assist.util;

import java.io.*;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class IOAssist {

    private static final Logger logger = LoggerFactory.getLogger(IOAssist.class);

    public static BufferedReader bufferedReader(File file, Charset charset) {
        if (file == null) {
            throw new RuntimeException("file is null");
        }
        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedReader bufferedReader(InputStream in, Charset charset) {
        if (in == null) {
            throw new RuntimeException("in is null");
        }
        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        return new BufferedReader(new InputStreamReader(in, charset));
    }

    public static BufferedReader bufferedReader(File file) {
        if (file == null) {
            throw new RuntimeException("file is null");
        }

        return bufferedReader(file, Charset.forName("UTF-8"));
    }

    public static BufferedReader bufferedReader(InputStream in) {
        if (in == null) {
            throw new RuntimeException("in is null");
        }

        return bufferedReader(in, Charset.forName("UTF-8"));
    }

    public static BufferedWriter bufferedWriter(File file, Charset charset) {
        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedWriter bufferedWriter(OutputStream out, Charset charset) {
        if (out == null) {
            throw new RuntimeException("out is null");
        }
        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        return new BufferedWriter(new OutputStreamWriter(out, charset));
    }

    public static BufferedWriter bufferedWriter(File file) {
        return bufferedWriter(file, Charset.forName("UTF-8"));
    }

    public static BufferedWriter bufferedWriter(OutputStream out) {
        if (out == null) {
            throw new RuntimeException("out is null");
        }

        return bufferedWriter(out, Charset.forName("UTF-8"));
    }

    public static BufferedInputStream bufferedInputStream(File file) {
        if (file == null) {
            throw new RuntimeException("file is null");
        }

        try {
            return bufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedInputStream bufferedInputStream(InputStream in) {
        if (in == null) {
            throw new RuntimeException("in is null");
        }

        if (in instanceof BufferedInputStream) {
            return (BufferedInputStream)in;
        }
        return new BufferedInputStream(in);
    }

    public static BufferedOutputStream bufferedOutputStream(File file) {
        try {
            return bufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedOutputStream bufferedOutputStream(OutputStream out) {
        if (out == null) {
            throw new RuntimeException("out is null");
        }

        if (out instanceof BufferedOutputStream) {
            return (BufferedOutputStream)out;
        }
        return new BufferedOutputStream(out);
    }

}

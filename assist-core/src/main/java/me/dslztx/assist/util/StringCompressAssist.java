package me.dslztx.assist.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringCompressAssist {

    public static String compressUsingGZIP(String s) {
        if (StringAssist.isBlank(s)) {
            return s;
        }

        ByteArrayOutputStream bos = null;
        GZIPOutputStream os = null;

        try {
            bos = new ByteArrayOutputStream();
            os = new GZIPOutputStream(bos);
            os.write(s.getBytes("UTF-8"));
            os.close();
            bos.close();

            byte[] bs = bos.toByteArray();
            return new String(bs, "iso-8859-1");
        } catch (Exception ex) {
            log.error("", ex);
            return s;
        }
    }

    public static String uncompressUsingGZIP(String str) {
        if (StringAssist.isBlank(str)) {
            return str;
        }

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes("iso-8859-1"));
            GZIPInputStream is = new GZIPInputStream(bis);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            bos.flush();
            bos.close();

            is.close();
            bis.close();

            return new String(bos.toByteArray(), "UTF-8");
        } catch (Exception ex) {
            log.error("", ex);
            return str;
        }
    }
}

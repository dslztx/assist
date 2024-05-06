package me.dslztx.assist.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeAssist {

    private static final Logger logger = LoggerFactory.getLogger(SerializeAssist.class);

    public static byte[] serializeToByteArray(Object obj) {
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();

            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            oo.flush();

            return bo.toByteArray();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            if (oo != null) {
                CloseableAssist.closeQuietly(oo);
            }
            if (bo != null) {
                CloseableAssist.closeQuietly(bo);
            }
        }
    }

    public static <T> T deserializeFromByteArray(byte[] bytes, Class<T> clz) {
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi = new ByteArrayInputStream(bytes);

            oi = new ObjectInputStream(bi);
            return clz.cast(oi.readObject());
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            if (oi != null) {
                CloseableAssist.closeQuietly(oi);
            }
            if (bi != null) {
                CloseableAssist.closeQuietly(bi);
            }
        }
    }
}

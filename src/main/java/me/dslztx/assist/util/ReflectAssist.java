package me.dslztx.assist.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectAssist {

    private static final Logger logger = LoggerFactory.getLogger(ReflectAssist.class);

    public static Object obtainInstanceFieldValue(Object obj, String fieldName) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field.get(obj);
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static Object obtainClassFieldValue(Class clz, String fieldName) {
        try {
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field.get(clz);
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}

package me.dslztx.assist.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// todo
public class BeanAssist {

    private static final Logger logger = LoggerFactory.getLogger(BeanAssist.class);

    public static void convertFromMap(Object bean, Map<String, Object> map, boolean ignoreCase) {
        if (ObjectAssist.isNull(bean)) {
            logger.error("bean obj is null");
        }
        if (ObjectAssist.isNull(map)) {
            logger.error("map source obj is null");
        }
        if (ignoreCase) {
            Map<String, Object> ignoreKeyCaseMap = new HashMap<String, Object>();
            for (String key : map.keySet()) {
                ignoreKeyCaseMap.put(key, map.get(key));
            }

            map = ignoreKeyCaseMap;
            Field[] fields = bean.getClass().getFields();
            Object value;
            for (Field field : fields) {
                value = map.get(field.getName().toLowerCase());
                if (ObjectAssist.isNull(value)) {
                    continue;
                }
                inject(bean, field, value);
            }
        } else {
            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {

                inject(bean, field, map.get(field.getName()));
            }
        }
    }

    private static void inject(Object bean, Field field, Object o) {
        field.setAccessible(true);
        Class type = field.getType();
        try {
            if (type == boolean.class || type == Boolean.class) {
                field.set(bean, Boolean.valueOf(o.toString()));

            } else if (type == byte.class || type == Byte.class) {
                field.set(bean, Byte.valueOf(o.toString()));

            } else if (type == short.class || type == Short.class) {
                field.set(bean, Short.valueOf(o.toString()));

            } else if (type == char.class || type == Character.class) {
                String s = o.toString();
                field.set(bean, s.charAt(0));

            } else if (type == int.class || type == Integer.class) {
                field.set(bean, Integer.valueOf(o.toString()));

            } else if (type == float.class || type == Float.class) {
                field.set(bean, Float.valueOf(o.toString()));

            } else if (type == long.class || type == Long.class) {
                field.set(bean, Long.valueOf(o.toString()));

            } else if (type == double.class || type == Double.class) {
                field.set(bean, Double.valueOf(o.toString()));

            } else if (type == String.class) {
                field.set(bean, String.valueOf(o));

            } else {
                return;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static void convertFromConfiguration(Object bean, Configuration configuration, boolean ignoreCase) {
        if (ObjectAssist.isNull(bean)) {
            logger.error("bean obj is null");
        }
        if (ObjectAssist.isNull(configuration)) {
            logger.error("map source obj is null");
        }
        if (ignoreCase) {
            Map<String, Object> ignoreKeyCaseMap = new HashMap<String, Object>();

            Configuration configuration0 = new PropertiesConfiguration();

            Iterator<String> iter = configuration.getKeys();
            while (iter.hasNext()) {
                String key = iter.next();
                ignoreKeyCaseMap.put(key, configuration.getProperty(key));
            }

            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {
                inject(bean, field, ignoreKeyCaseMap.get(field.getName().toLowerCase()));
            }
        } else {
            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {
                inject(bean, field, configuration.getProperty(field.getName()));
            }
        }
    }

    public static void convertFromProperties(Object bean, Properties properties, boolean ignoreCase) {
        if (ObjectAssist.isNull(bean)) {
            logger.error("bean obj is null");
        }
        if (ObjectAssist.isNull(properties)) {
            logger.error("map source obj is null");
        }
        if (ignoreCase) {
            Map<String, Object> ignoreKeyCaseMap = new HashMap<String, Object>();
            for (Entry<Object, Object> entry : properties.entrySet()) {

                ignoreKeyCaseMap.put(String.valueOf(entry.getKey()), entry.getValue());
            }

            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {
                inject(bean, field, ignoreKeyCaseMap.get(field.getName().toLowerCase()));
            }
        } else {
            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {
                inject(bean, field, properties.get(field.getName()));
            }
        }
    }
}

package me.dslztx.assist.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Map<String, Object> keyCaseInsensitiveMap = new HashMap<String, Object>();
            for (String key : map.keySet()) {
                keyCaseInsensitiveMap.put(key.toLowerCase(), map.get(key));
            }

            Field[] fields = bean.getClass().getDeclaredFields();
            Object value;
            for (Field field : fields) {
                value = keyCaseInsensitiveMap.get(field.getName().toLowerCase());

                if (ObjectAssist.isNull(value)) {
                    continue;
                }

                inject(bean, field, value);
            }
        } else {
            Field[] fields = bean.getClass().getDeclaredFields();
            Object value;
            for (Field field : fields) {
                value = map.get(field.getName());

                if (ObjectAssist.isNull(value)) {
                    continue;
                }

                inject(bean, field, value);
            }
        }
    }

    private static void inject(Object bean, Field field, Object value) {
        field.setAccessible(true);

        Class type = field.getType();
        try {
            if (type == boolean.class || type == Boolean.class) {
                field.set(bean, Boolean.valueOf(value.toString()));

            } else if (type == byte.class || type == Byte.class) {
                field.set(bean, Byte.valueOf(value.toString()));

            } else if (type == short.class || type == Short.class) {
                field.set(bean, Short.valueOf(value.toString()));

            } else if (type == char.class || type == Character.class) {
                String s = value.toString();
                if (s.length() == 1) {
                    field.set(bean, s.charAt(0));
                }

            } else if (type == int.class || type == Integer.class) {
                field.set(bean, Integer.valueOf(value.toString()));

            } else if (type == float.class || type == Float.class) {
                field.set(bean, Float.valueOf(value.toString()));

            } else if (type == long.class || type == Long.class) {
                field.set(bean, Long.valueOf(value.toString()));

            } else if (type == double.class || type == Double.class) {
                field.set(bean, Double.valueOf(value.toString()));

            } else if (type == String.class) {
                field.set(bean, value.toString());

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
            logger.error("configuration source obj is null");
        }
        if (ignoreCase) {
            Map<String, Object> keyCaseInsensitiveMap = new HashMap<String, Object>();

            Iterator<String> iter = configuration.getKeys();
            while (iter.hasNext()) {
                String key = iter.next();
                keyCaseInsensitiveMap.put(key.toLowerCase(), configuration.getProperty(key));
            }

            Field[] fields = bean.getClass().getDeclaredFields();
            Object value;
            for (Field field : fields) {
                value = keyCaseInsensitiveMap.get(field.getName().toLowerCase());
                if (ObjectAssist.isNull(value)) {
                    continue;
                }

                inject(bean, field, value);
            }
        } else {
            Field[] fields = bean.getClass().getDeclaredFields();
            Object value;
            for (Field field : fields) {
                value = configuration.getProperty(field.getName());

                if (ObjectAssist.isNull(value)) {
                    continue;
                }

                inject(bean, field, value);
            }
        }
    }

    public static void convertFromProperties(Object bean, Properties properties, boolean ignoreCase) {
        if (ObjectAssist.isNull(bean)) {
            logger.error("bean obj is null");
        }

        if (ObjectAssist.isNull(properties)) {
            logger.error("properties source obj is null");
        }

        if (ignoreCase) {
            Map<String, Object> keyCaseInsensitive = new HashMap<String, Object>();
            for (Entry<Object, Object> entry : properties.entrySet()) {
                keyCaseInsensitive.put(String.valueOf(entry.getKey()).toLowerCase(), entry.getValue());
            }

            Field[] fields = bean.getClass().getDeclaredFields();
            Object value;
            for (Field field : fields) {
                value = keyCaseInsensitive.get(field.getName().toLowerCase());
                if (ObjectAssist.isNull(value)) {
                    continue;
                }

                inject(bean, field, value);
            }
        } else {
            Field[] fields = bean.getClass().getDeclaredFields();
            Object value;
            for (Field field : fields) {
                value = properties.getProperty(field.getName());

                if (ObjectAssist.isNull(value)) {
                    continue;
                }

                inject(bean, field, value);
            }
        }
    }
}

package me.dslztx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dslztx
 * @date 2015年12月14日
 */
public class ConfigFile {
    private static final Logger logger = LoggerFactory.getLogger(ConfigFile.class);

    /**
     * 保存注释内容
     */
    List<String> comments;

    /**
     * 保存key/value对内容
     */
    Map<String, String> contents;

    public ConfigFile(List<String> comments, Map<String, String> contents) {
        this.comments = comments;
        this.contents = contents;
    }

    /**
     * 从文件加载
     * 
     * @param pFile
     * @param separator
     */
    public static ConfigFile loadFile(File pFile, char separator) {
        List<String> comments0 = new ArrayList<String>();
        Map<String, String> contents0 = new HashMap<String, String>();

        String line = null, key = null, value = null;
        int sepPosition = -1;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(pFile));
            while ((line = in.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    comments0.add(line.substring(1));
                } else {
                    sepPosition = line.indexOf(separator);
                    if (sepPosition == -1)
                        continue;
                    key = line.substring(0, sepPosition);
                    value = line.substring(sepPosition + 1);
                    contents0.put(key, value);
                }
            }

            return new ConfigFile(comments0, contents0);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
        return null;
    }

    /**
     * 存储到文件
     * 
     * @param pFile
     * @param separator
     */
    public void storeFile(File pFile, char separator) {
        BufferedWriter out = null;
        String value = null;
        try {
            out = new BufferedWriter(new FileWriter(pFile));

            if (comments != null && comments.size() != 0) {
                for (String comment : comments) {
                    out.write("#" + comment + "\n");
                }
                out.write("\n");
            }

            if (contents != null && contents.keySet().size() != 0) {
                for (String key : contents.keySet()) {
                    value = contents.get(key);
                    out.write(key + separator + value + "\n");
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    /**
     * 更新键值对
     * 
     * @param key
     * @param value
     */
    public void update(String key, String value) {
        if (contents != null) {
            if (!contents.containsKey(key)) {
                throw new RuntimeException("key not exist");
            } else {
                contents.put(key, value);
            }
        }
    }

    /**
     * 批量更新键值对
     * 
     * @param keys
     * @param values
     */
    public void batchUpdate(String[] keys, String[] values) {
        if (contents != null) {
            String key = null, value = null;
            for (int index = 0; index < keys.length; index++) {
                key = keys[index];
                value = values[index];
                if (!contents.containsKey(key)) {
                    throw new RuntimeException("key " + key + " not exist");
                } else {
                    contents.put(key, value);
                }
            }
        }
    }

    /**
     * 加入键值对
     * 
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        if (contents != null) {
            if (contents.containsKey(key)) {
                throw new RuntimeException("key exists");
            } else {
                contents.put(key, value);
            }
        }
    }

    /**
     * 批量加入键值对
     * 
     * @param keys
     * @param values
     */
    public void batchAdd(String[] keys, String[] values) {
        if (contents != null) {
            String key = null, value = null;
            for (int index = 0; index < keys.length; index++) {
                key = keys[index];
                value = values[index];
                if (contents.containsKey(key)) {
                    throw new RuntimeException("key " + key + " exists");
                } else {
                    contents.put(key, value);
                }
            }
        }
    }

    /**
     * 删除键值对
     * 
     * @param key
     */
    public void remove(String key) {
        if (contents != null) {
            if (!contents.containsKey(key)) {
                throw new RuntimeException("key " + key + " not exist");
            } else {
                contents.remove(key);
            }
        }
    }

    /**
     * 批量删除键值对
     * 
     * @param keys
     */
    public void batchRemove(String[] keys) {
        if (contents != null) {
            String key = null;
            for (int index = 0; index < keys.length; index++) {
                key = keys[index];
                if (!contents.containsKey(key)) {
                    throw new RuntimeException("key " + key + " not exist");
                } else {
                    contents.remove(key);
                }
            }
        }
    }
}

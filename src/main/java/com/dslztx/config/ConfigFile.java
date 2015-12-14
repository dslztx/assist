package com.dslztx.config;

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
    public static void loadFile(File pFile, char separator) {
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
     * 更新值对
     * 
     * @param key
     * @param value
     */
    public void update(String key, String value) {
        if (contents != null)
            contents.put(key, value);
    }

    /**
     * 批量更新值对
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
                contents.put(key, value);
            }
        }
    }
}

package com.dslztx.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dslztx
 * @date 2015年12月14日
 */
public class ConfigFileTest {
    private static final Logger logger = LoggerFactory.getLogger(ConfigFileTest.class);

    @Test
    public void testStoreFile() throws Exception {
        try {
            List<String> comments = new ArrayList<String>();
            comments.add("hello");
            comments.add("world");

            Map<String, String> contents = new HashMap<String, String>();
            contents.put("a", "b");
            contents.put("c", "d");
            contents.put("e", "f");

            ConfigFile configFile = new ConfigFile(comments, contents);

            configFile.update("a", "bb");
            configFile.batchUpdate(new String[] {"c", "e"}, new String[] {"dd", "ff"});

            configFile.storeFile(new File("/home/dsl/1.out"), ',');
            configFile.storeFile(new File("/home/dsl/2.out"), ':');
            configFile.storeFile(new File("/home/dsl/3.out"), '=');
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}

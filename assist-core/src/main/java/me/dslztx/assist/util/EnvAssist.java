package me.dslztx.assist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvAssist {

    private static final Logger logger = LoggerFactory.getLogger(EnvAssist.class);

    public static String obtainExecutePath() {
        return System.getProperty("user.dir");
    }

    public static String obtainClassPath() {
        return System.getProperty("java.class.path");
    }
}

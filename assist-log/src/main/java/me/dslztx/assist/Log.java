package me.dslztx.assist;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static final Logger logger = Logger.getLogger(Log.class);

    private static final org.apache.commons.logging.Log jclLogger = LogFactory.getLog(Log.class);

    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Log.class);

    public static void main(String[] args) {
        logger.error("log4j logging");

        jclLogger.error("jcl logging");

        slf4jLogger.error("slf4j logging");
    }
}

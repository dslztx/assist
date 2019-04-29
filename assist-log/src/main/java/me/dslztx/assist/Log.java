package me.dslztx.assist;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;

public class Log {

    private static final Logger logger = Logger.getLogger(Log.class);

    private static final org.apache.commons.logging.Log jclLogger = LogFactory.getLog(Log.class);

    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Log.class);

    private static final org.apache.logging.log4j.Logger log4j2Logger = LogManager.getLogger(Log.class);

    public static void main(String[] args) {
        info();
        warn();
        error();
        debug();
    }

    private static void info() {
        logger.info("log4j info");

        jclLogger.info("jcl info");

        slf4jLogger.info("slf4j info");

        log4j2Logger.info("log4j2 info");
    }

    private static void warn() {
        logger.warn("log4j warn");

        jclLogger.warn("jcl warn");

        slf4jLogger.warn("slf4j warn");

        log4j2Logger.warn("log4j2 warn");
    }

    private static void error() {
        logger.error("log4j error");
        logger.fatal("log4j fatal");

        jclLogger.error("jcl error");
        jclLogger.fatal("jcl fatal");

        slf4jLogger.error("slf4j error");

        log4j2Logger.error("log4j2 error");
        log4j2Logger.fatal("log4j2 fatal");
    }

    private static void debug() {
        logger.debug("log4j debug");
        logger.trace("log4j trace");

        jclLogger.debug("jcl debug");
        jclLogger.trace("jcl trace");

        slf4jLogger.debug("slf4j debug");
        slf4jLogger.trace("slf4j trace");

        log4j2Logger.debug("log4j2 debug");
        log4j2Logger.trace("log4j2 trace");
    }
}

package me.dslztx.assist.util;

import java.io.OutputStreamWriter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

public class LoggerAssist {

    public static void openLogAllConsole() {
        org.apache.log4j.Logger root = org.apache.log4j.Logger.getRootLogger();

        root.setLevel(Level.ALL);

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setWriter(new OutputStreamWriter(System.out));
        consoleAppender.setLayout(new PatternLayout("%-5p [%t]: %m%n"));

        root.addAppender(consoleAppender);
    }

    public static void loggerLevelSetDynamically(org.apache.log4j.Logger logger, Level level) {
        logger.setLevel(level);
    }
}

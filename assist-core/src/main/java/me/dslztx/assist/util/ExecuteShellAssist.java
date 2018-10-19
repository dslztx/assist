package me.dslztx.assist.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteShellAssist {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteShellAssist.class);

    public static void executeShellSync(String command, String... arguments) {
        if (StringAssist.isBlank(command)) {
            throw new RuntimeException("no command");
        }

        try {
            List<String> commandWithArguments = new ArrayList<String>();
            commandWithArguments.add(command);
            commandWithArguments.addAll(Arrays.asList(arguments));

            Process process = new ProcessBuilder(commandWithArguments).start();
            process.waitFor();
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static void executeShellAsync(String command, String... arguments) {
        if (StringAssist.isBlank(command)) {
            throw new RuntimeException("no command");
        }

        try {
            List<String> commandWithArguments = new ArrayList<String>();
            commandWithArguments.add(command);
            commandWithArguments.addAll(Arrays.asList(arguments));

            new ProcessBuilder(commandWithArguments).start();
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }
}

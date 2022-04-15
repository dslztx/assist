package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.io.IOException;
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

    public static List<String> executeShellSyncForResult(String command, String... arguments) {
        if (StringAssist.isBlank(command)) {
            throw new RuntimeException("no command");
        }

        try {
            List<String> commandWithArguments = new ArrayList<String>();
            commandWithArguments.add(command);
            commandWithArguments.addAll(Arrays.asList(arguments));

            List<String> result = new ArrayList<>();

            Process process = new ProcessBuilder(commandWithArguments).start();

            BufferedReader bufferedReader = IOAssist.bufferedReader(process.getInputStream());

            // 循环等待进程输出，判断进程存活则循环获取输出流数据
            while (process.isAlive()) {
                while (bufferedReader.ready()) {
                    String m = bufferedReader.readLine();
                    result.add(m);
                }
            }

            CloseableAssist.closeQuietly(bufferedReader);

            return result;
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] rags) throws IOException, InterruptedException {
        List<String> results = executeShellSyncForResult("/bin/bash", "/home/dslztx/Desktop/n.out");
        System.out.println(results);
    }
}

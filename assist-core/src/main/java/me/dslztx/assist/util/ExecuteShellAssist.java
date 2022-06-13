package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.concurrent.NamedThreadFactory;

public class ExecuteShellAssist {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteShellAssist.class);

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), new NamedThreadFactory("shell-execute", true));

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

            Process process = new ProcessBuilder(commandWithArguments).start();

            Future<List<String>> resultFuture = executor.submit(new SubprocessReadAsync(process));

            try {
                return resultFuture.get(3, TimeUnit.MINUTES);
            } catch (Exception e) {
                process.destroyForcibly();

                return new ArrayList<>();
            }
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

class SubprocessReadAsync implements Callable<List<String>> {

    Process process;

    public SubprocessReadAsync(Process process) {
        this.process = process;
    }

    @Override
    public List<String> call() {

        BufferedReader bufferedReader = null;

        InputStream inputStream = null;

        try {
            List<String> result = new ArrayList<>();

            inputStream = process.getInputStream();

            bufferedReader = IOAssist.bufferedReader(inputStream);

            // 循环等待进程输出，判断进程存活则循环获取输出流数据
            while (process.isAlive()) {
                while (bufferedReader.ready()) {
                    String m = bufferedReader.readLine();
                    result.add(m);
                }
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            CloseableAssist.closeQuietly(bufferedReader);
            CloseableAssist.closeQuietly(inputStream);
        }

    }
}

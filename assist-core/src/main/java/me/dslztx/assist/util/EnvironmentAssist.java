package me.dslztx.assist.util;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentAssist {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentAssist.class);

    private static volatile boolean init = false;

    private static String machineName = null;
    private static String shortMachineName = null;

    public static String obtainShortMachineName() {
        if (!init) {
            init();
        }

        return shortMachineName;
    }

    public static String obtainLongMachineName() {
        if (!init) {
            init();
        }

        return machineName;
    }

    private static void init() {
        if (!init) {
            synchronized (EnvironmentAssist.class) {
                if (!init) {
                    try {
                        machineName = (InetAddress.getLocalHost()).getHostName();

                        int index = machineName.indexOf(".");
                        if (index != -1) {
                            shortMachineName = machineName.substring(0, index);
                        } else {
                            shortMachineName = machineName;
                        }
                        init = true;
                    } catch (Exception e) {
                        logger.error("", e);

                        machineName = null;
                        shortMachineName = null;

                        init = false;
                    }
                }
            }
        }
    }

    /**
     * @return 返回执行java命令时，所在的目录
     */
    public static String obtainExecuteCWD() {
        return System.getProperty("user.dir");
    }

    /**
     * @return 返回类路径
     */
    public static String obtainClassPath() {
        return System.getProperty("java.class.path");
    }

    public static void main(String[] args) {
        System.out.println(obtainLongMachineName());
        System.out.println(obtainShortMachineName());
        System.out.println(obtainExecuteCWD());
        System.out.println(obtainClassPath());
    }

}

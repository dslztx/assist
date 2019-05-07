package me.dslztx.assist.util;

import java.net.*;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentAssist {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentAssist.class);

    private static volatile boolean init = false;

    private static String machineName = null;
    private static String shortMachineName = null;
    private static String lanIPv4 = null;

    private static List<NetworkInterface> networkInterfaceList = null;

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

    public static String obtainLanIPv4() {
        if (!init) {
            init();
        }

        return lanIPv4;
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

                        doObtainLanIPv4();

                        init = true;
                    } catch (Exception e) {
                        logger.error("", e);

                        init = true;
                    }
                }
            }
        }
    }

    private static void doObtainLanIPv4() {
        try {
            if (ObjectAssist.isNotNull(NetworkInterface.getNetworkInterfaces())) {
                networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());

                for (NetworkInterface networkInterface : networkInterfaceList) {
                    if (ObjectAssist.isNull(networkInterface)) {
                        continue;
                    }

                    List<InetAddress> inetAddressList = Collections.list(networkInterface.getInetAddresses());

                    for (InetAddress ia : inetAddressList) {
                        if (ObjectAssist.isNull(ia)) {
                            continue;
                        }

                        if (ia instanceof Inet4Address) {
                            if (IPAssist.isLanIPv4(ia.getHostAddress())) {
                                lanIPv4 = ia.getHostAddress();
                                return;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
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

    public static void main(String[] args) throws UnknownHostException, SocketException {
        System.out.println(obtainLongMachineName());
        System.out.println(obtainShortMachineName());
        System.out.println(obtainExecuteCWD());
        System.out.println(obtainClassPath());
        System.out.println(obtainLanIPv4());
    }

}

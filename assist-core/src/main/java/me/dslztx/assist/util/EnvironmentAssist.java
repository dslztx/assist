package me.dslztx.assist.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
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

    /**
     * 在复杂的网络情形中，获取本机局域网IP可能有多个结果，比如“在存在一个虚IP的情形中，获取本机局域网IP就有两个结果”，因此，最好不要依赖于本方法的获取值，而在配置文件中配置
     */
    private static void doObtainLanIPv4() {
        try {
            Inet4Address hit = null;

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
                                if (ObjectAssist.isNull(hit)) {
                                    hit = (Inet4Address)ia;
                                } else {
                                    // 存在多个本机局域网IP（可能是因为存在虚IP导致），则直接通过域名解析服务获得一个结果，当然这个结果值也依赖于获得的域名和域名解析服务，即这个结果也不一定是我们想要的
                                    InetAddress inetAddress = InetAddress.getLocalHost();
                                    if (inetAddress instanceof Inet4Address) {
                                        if (IPAssist.isLanIPv4(inetAddress.getHostAddress())) {
                                            lanIPv4 = inetAddress.getHostAddress();
                                        }
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }

            if (ObjectAssist.isNotNull(hit)) {
                lanIPv4 = hit.getHostAddress();
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

    public static void main(String[] args) throws IOException {
        System.out.println(obtainLongMachineName());
        System.out.println(obtainShortMachineName());
        System.out.println(obtainExecuteCWD());
        System.out.println(obtainClassPath());
        System.out.println(obtainLanIPv4());
    }

}

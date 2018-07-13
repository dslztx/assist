package me.dslztx.assist.util;

import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MachineAssist {

  private static final Logger logger = LoggerFactory.getLogger(MachineAssist.class);

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
      synchronized (MachineAssist.class) {
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

  public static void main(String[] args) {
    System.out.println(obtainLongMachineName());
    System.out.println(obtainShortMachineName());
  }

}

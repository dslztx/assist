package me.dslztx.assist.util;

import java.io.File;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigAssist {

  private static final Logger logger = LoggerFactory.getLogger(ConfigAssist.class);

  public static Configuration loadConfig(File configFile) {
    if (configFile == null) {
      throw new RuntimeException("config file is null");
    }

    if (!configFile.exists()) {
      throw new RuntimeException("config file does not exist");
    }

    try {
      Configurations configs = new Configurations();
      return configs.properties(configFile);
    } catch (Exception e) {
      logger.error("", e);
      return null;
    }
  }
}

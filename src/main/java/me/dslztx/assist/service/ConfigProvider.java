package me.dslztx.assist.service;

/**
 * @author dslztx
 */
public class ConfigProvider {

  ConfigFetcher configFetcher;

  public ConfigProvider(ConfigFetcher configFetcher) {
    this.configFetcher = configFetcher;
    this.configFetcher.run();
  }

  public String obtainFromKey(String key) {
    return configFetcher.obtainFromKey(key);
  }
}

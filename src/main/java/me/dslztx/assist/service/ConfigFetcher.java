package me.dslztx.assist.service;

/**
 * @author dslztx
 */
public interface ConfigFetcher {

  void run();

  String obtainFromKey(String key);
}

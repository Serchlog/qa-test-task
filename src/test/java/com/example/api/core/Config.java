package com.example.api.core;

public final class Config {
  private Config() {}

  // Base URL can be overridden with -DbaseUrl=...
  public static String baseUrl() {
    return System.getProperty("baseUrl", "https://jsonplaceholder.typicode.com");
  }

  // Global timeout (ms), override with -DtimeoutMs=...
  public static int timeoutMs() {
    return Integer.getInteger("timeoutMs", 10000);
  }
}

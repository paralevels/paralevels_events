package com.paralevels.events.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties(prefix = "oracle.soda")
public record OracleSodaProperties(Map<String, SodaTarget> types) {
  public record SodaTarget(String url, String user, String pass) {}
}

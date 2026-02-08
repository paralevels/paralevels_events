package com.paralevels.events.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OracleSodaProperties.class)
public class AppConfig {}

package com.ch_vadim.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.server")
public record ServerConfig (
        String url
){
}

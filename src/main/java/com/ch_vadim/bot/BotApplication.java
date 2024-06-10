package com.ch_vadim.bot;

import com.ch_vadim.bot.config.BotConfig;
import com.ch_vadim.bot.config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
@EnableConfigurationProperties({BotConfig.class, ServerConfig.class})
public class BotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}

}

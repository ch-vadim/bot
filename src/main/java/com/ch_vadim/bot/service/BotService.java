package com.ch_vadim.bot.service;

import com.ch_vadim.bot.command.Command;
import com.ch_vadim.bot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BotService extends TelegramLongPollingBot {
    private final BotConfig config;

    private final List<Command> commands;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    public BotService(BotConfig config, List<Command> commands) {
        super(config.token());
        this.config = config;
        this.commands = Collections.unmodifiableList(commands);
        System.out.println(commands);

        try {
            this.execute(new SetMyCommands(
                    this.commands.stream()
                            .map(command -> new BotCommand(
                                    command.name(),
                                    command.description()))
                            .collect(Collectors.toList()),
                    new BotCommandScopeDefault(),
                    null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            String[] commandAndText = message.split(" ", 2);

            SendMessage response = null;
            for (Command command : commands) {
                if (command.name().equals(commandAndText[0])) {
                    response = command.handle(chatId, message, update);
                }
            }
            if (response == null) {
                response = new SendMessage(String.valueOf(chatId), "Invalid command. Use /help command");
            }

            sendMessage(response);


        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }

    @Override
    public String getBotUsername() {
        return config.name();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    private void sendMessage(SendMessage message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Cant send message. " + e.getMessage());
        }
    }

    public void sendMessages(List<SendMessage> messages) {
        messages.forEach(this::sendMessage);
    }
}

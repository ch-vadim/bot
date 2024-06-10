package com.ch_vadim.bot.command;

import com.ch_vadim.bot.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command{
    private final ServerService serverService;
    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start bot";
    }

    @Override
    public SendMessage handle(long chatId, String text, Update update) {
        String responseMessage =
                serverService.registerUser(chatId, update.getMessage().getChat().getFirstName());
        return new SendMessage(String.valueOf(chatId), responseMessage);
    }
}

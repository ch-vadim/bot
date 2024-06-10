package com.ch_vadim.bot.command;

import com.ch_vadim.bot.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SetEmailCommand implements Command{

    private final ServerService serverService;
    @Override
    public String name() {
        return "/setemail";
    }

    @Override
    public String description() {
        return "Set your email to get notification about tasks on email";
    }

    @Override
    public SendMessage handle(long chatId, String text, Update update) {
        String responseMessage = serverService.setUserEmail(chatId, text);
        return new SendMessage(String.valueOf(chatId), responseMessage);
    }
}

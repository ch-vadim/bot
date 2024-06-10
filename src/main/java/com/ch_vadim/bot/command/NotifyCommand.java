package com.ch_vadim.bot.command;

import com.ch_vadim.bot.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class NotifyCommand implements Command{

    private final ServerService serverService;

    @Override
    public String name() {
        return "/notify";
    }

    @Override
    public String description() {
        return "Subscribe to notify your task";
    }

    @Override
    public SendMessage handle(long chatId, String text, Update update) {
        long taskId;
        try {
            taskId = Long.parseLong(text);
        } catch (NumberFormatException e) {
            return new SendMessage(String.valueOf(chatId), "Please send task id in number format");
        }
        String responseMessage = serverService.subscribeToNotify(chatId, taskId);
        return new SendMessage(String.valueOf(chatId), responseMessage);
    }
}

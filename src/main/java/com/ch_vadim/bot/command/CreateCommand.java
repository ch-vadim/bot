package com.ch_vadim.bot.command;

import com.ch_vadim.bot.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateCommand implements Command{

    private final ServerService serverService;

    @Override
    public String name() {
        return "/create";
    }

    @Override
    public String description() {
        return "Create new task";
    }

    @Override
    public SendMessage handle(long chatId, String text, Update update) {
        Long taskId = serverService.createTask(chatId, text);
        return new SendMessage(String.valueOf(chatId), "Task was created with id " + taskId);
    }
}

package com.ch_vadim.bot.command;

import com.ch_vadim.bot.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GetTasksCommand implements Command{

    private final ServerService serverService;

    @Override
    public String name() {
        return "/gettasks";
    }

    @Override
    public String description() {
        return "Get your tasks";
    }

    @Override
    public SendMessage handle(long chatId, String text, Update update) {
        String message = serverService.getAllTasks(chatId);
        return new SendMessage(String.valueOf(chatId), message);
    }
}


package com.ch_vadim.bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String name();

    String description();

    SendMessage handle(long chatId, String text, Update update);
}


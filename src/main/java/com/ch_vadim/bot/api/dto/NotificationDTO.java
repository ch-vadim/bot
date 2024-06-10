package com.ch_vadim.bot.api.dto;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public record NotificationDTO (
    long chatId,
    String message
){
    public static SendMessage toSendMessage(NotificationDTO notificationDTO) {
        return new SendMessage(String.valueOf(notificationDTO.chatId()),
                notificationDTO.message());
    }
}

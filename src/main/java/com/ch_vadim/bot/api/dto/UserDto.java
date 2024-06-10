package com.ch_vadim.bot.api.dto;

import lombok.Builder;

@Builder
public record UserDto (
        long chatId,
        String email,
        String name
){}

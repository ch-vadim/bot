package com.ch_vadim.bot.api.controller;
import com.ch_vadim.bot.api.dto.NotificationDTO;
import com.ch_vadim.bot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final BotService botService;

    @PostMapping("/notifications")
    public ResponseEntity<Void> sendNotifications(@RequestBody List<NotificationDTO> notifications) {
        botService.sendMessages(
                notifications.stream()
                        .map(NotificationDTO::toSendMessage)
                        .toList());
        return ResponseEntity.ok().build();
    }
}

package com.ch_vadim.bot.service;

import com.ch_vadim.bot.api.dto.TaskDto;
import com.ch_vadim.bot.api.dto.TaskState;
import com.ch_vadim.bot.api.dto.UserDto;
import com.ch_vadim.bot.config.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ServerService {
    private final RestTemplate restTemplate;
    private final ServerConfig serverConfig;

    public String registerUser(long chatId, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String registerUrl = serverConfig.url() + "/api/user";

        HttpEntity<UserDto> requestEntity = new HttpEntity<>(UserDto
                    .builder()
                    .chatId(chatId)
                    .name(name)
                    .build(),
                headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                registerUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.warn("Error to register: " + response);
            return "Sorry, smth wrong";
        } else {
            log.info("Registered");
            return "Welcome!";
        }
    }

    public String setUserEmail(long chatId, String email) {
        String url = serverConfig.url() + "/api/user/" + chatId + "/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", email);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.warn("Error to set email: " + response);
            return "Email updated successfully";
        } else {
            log.info("Success to set email");
            return "Failed to update email";
        }
    }

    public Long createTask(long chatId, String taskName) {
        String url = serverConfig.url() + "/api/task";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TaskDto taskDto = TaskDto.builder()
                .state(TaskState.CREATED)
                .chatId(chatId)
                .name(taskName)
                .build();

        HttpEntity<TaskDto> entity = new HttpEntity<>(taskDto, headers);

        ResponseEntity<TaskDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, TaskDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getId();
        } else {
            log.warn("task dont created");
            return -1L;
        }
    }

    public String getAllTasks(long chatId) {
        String url = serverConfig.url() + "/api/task" + chatId + "?onlyActual=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<TaskDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            List<TaskDto> tasks =  response.getBody();
            StringBuilder sb = new StringBuilder();
            sb.append("Your tasks: \n");
            for (TaskDto task: tasks) {
                sb.append(task.getId())
                        .append(" ")
                        .append(task.getName())
                        .append("\n");
            }
            sb.append("Good luck");
            return sb.toString();
        } else {
            log.error("Error get users tasks");
            return "Oops";
        }
    }

    public String completeTask(long chatId, long taskId) {
        return "Task " + taskId + " was completed";
    }

    public String subscribeToNotify(long chatId, long taskId) {
        return "Success subscribe to task with id " + taskId;
    }
}

package com.ch_vadim.bot.api.dto;


public enum TaskState {
    CREATED("created"),
    COMPLETED("done");

    private String state;

    TaskState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
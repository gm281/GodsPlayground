package com.godsplayground.server.model.client;

public class GameplayRequest {
    private String type;

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GameplayRequest type: " + type;
    }
}

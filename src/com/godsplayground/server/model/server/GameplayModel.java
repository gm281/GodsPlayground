package com.godsplayground.server.model.server;

import com.godsplayground.Gameplay;
import com.godsplayground.server.model.client.GameplayResponse;

public class GameplayModel implements Gameplay {
    private final int id;
    private final String mapName;

    public GameplayModel(final Gameplay gameplay) {
        this.id = gameplay.getId();
        this.mapName = "images/"+gameplay.getMapName()+".png";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    public GameplayResponse toResponse() {
        return new GameplayResponse(id, mapName);
    }

}

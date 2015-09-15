package com.godsplayground.server.model.client;

public class ModifyAvatarsRequest {
    private int gameplayId;
    private boolean addOrRemove;

    public int getGameplayId() {
        return gameplayId;
    }

    public boolean isAddOrRemove() {
        return addOrRemove;
    }
}

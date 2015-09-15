package com.godsplayground.controller;

import com.godsplayground.Gameplay;
import com.godsplayground.Player;


public class PlayerModel {

    private final Player uiPlayer;
    private final GameplayModel gameplay;

    public PlayerModel(final Player player) {
        this.uiPlayer = player;
        this.gameplay = new GameplayModel(this);
    }

    public GameplayModel getTheGameplay() {
        return gameplay;
    }

    public GameplayModel getGameplay(final Gameplay uiGameplay) {
        // Only one gameplay supporte atm
        assert(uiGameplay.getId() == gameplay.getId());
        return this.gameplay;
    }

    public void sendNotification(final Object notification) {
        uiPlayer.sendNotification(notification);
    }
}

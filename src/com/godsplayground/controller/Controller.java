package com.godsplayground.controller;

import com.godsplayground.Avatar;
import com.godsplayground.Gameplay;
import com.godsplayground.Player;
import com.godsplayground.UIController;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Controller implements UIController.Delegate {

    private static final Logger logger = Logger.getLogger(Controller.class);

    private final ConcurrentMap<Player.Identifier, PlayerModel> players;

    public Controller() {
        players = new ConcurrentHashMap<>();
    }

    @Override
    public void newPlayer(final Player player) {
        logger.info("Got new player " + player);
        final PlayerModel old = players.putIfAbsent(player.getId(), new PlayerModel(player));
        if (old != null) {
            logger.warn("Got the same player as before: " + old + ", ignoring the new one.");
            return;
        }


    }

    @Override
    public void playerWentAway(final Player player) {
        logger.info("Player gone away " + player);
        players.remove(player.getId());
    }

    @Override
    public Gameplay getGameplay(final Player uiPlayer, final String type) {
        final PlayerModel player = players.get(uiPlayer.getId());
        return player.getTheGameplay();
    }

    @Override
    public Set<Avatar> getAvatars(final Player uiPlayer, final Gameplay uiGameplay) {
        final PlayerModel player = players.get(uiPlayer.getId());
        final GameplayModel gameplay = player.getGameplay(uiGameplay);
        return new HashSet<>(gameplay.getAvatars());
    }

    @Override
    public void modifyAvatars(final Player uiPlayer, final Gameplay uiGameplay, final boolean addOrRemove) {
        final PlayerModel player = players.get(uiPlayer.getId());
        final GameplayModel gameplay = player.getGameplay(uiGameplay);
        gameplay.modifyAvatars(addOrRemove);
    }
}

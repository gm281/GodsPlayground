package com.godsplayground.controller;

import com.godsplayground.server.model.client.GodsPlaygroundInterestingDataStructure;
import com.godsplayground.Player;
import com.godsplayground.PlayerCreator;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Controller implements PlayerCreator.Delegate {

    private static final Logger logger = Logger.getLogger(Controller.class);

    private final ConcurrentMap<Player.Identifier, Player> players;

    public Controller() {
        players = new ConcurrentHashMap<>();
    }


    private Thread tmpThread;

    @Override
    public void newPlayer(final Player player) {
        logger.info("Got new player " + player);
        final Player old = players.putIfAbsent(player.getId(), player);
        if (old != null) {
            logger.warn("Got the same player as before: " + old + ", ignoring the new one.");
        }

        final Thread thread = new Thread() {
            public void run() {
                for(int i = 0;; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    final GodsPlaygroundInterestingDataStructure notification = new GodsPlaygroundInterestingDataStructure();
                    notification.someArray = new int[]{13, 14, 17};
                    notification.someLong = i;
                    notification.someString = "kukulka";
                    notification.someClass = new GodsPlaygroundInterestingDataStructure.SomeClass();
                    notification.someClass.someInt = 15;

                    player.sendNotification(notification);
                }
            }
        };
        thread.start();
        tmpThread = thread;
    }

    @Override
    public void playerWentAway(Player player) {
        logger.info("Player gone away " + player);
        players.remove(player.getId());
    }
}

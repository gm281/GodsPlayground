package com.godsplayground.server;

import com.godsplayground.PlayerCreator;
import com.godsplayground.server.model.client.Heartbeat;
import com.godsplayground.server.model.client.Login;
import com.godsplayground.server.model.client.Notification;
import com.godsplayground.server.model.server.Player;
import com.google.gson.Gson;

import java.util.*;

import static spark.Spark.*;

public class Server implements PlayerCreator {

    static final String COOKIE_IDENTIFIER = "user-identifier";
    static final long HEARTBEAT_PERIOD = 1000;


    private final Gson gson;
    private volatile Delegate playerDelegate;
    private final Map<Player.Identifier, Player> players;
    private Thread expiryThread;

    public Server() {
        gson = new Gson();
        players = new HashMap<>();
    }

    private long handleLogin(final Login login) {
        final Player player = new Player(login.getUsername(), login.getPassword());
        final Delegate delegate = playerDelegate;
        if (delegate != null) {
            delegate.newPlayer(player);
        }
        players.put(player.getId(), player);

        return player.getId().getId();
    }

    private synchronized Player getPlayer(final long id) {
        return players.get(new Player.Identifier(id));
    }

    private synchronized void tryExpire() {
        final long currentTime = System.currentTimeMillis();
        for (final Player player : players.values()) {
            if (currentTime > player.getLastSeenTimestamp() + HEARTBEAT_PERIOD * 2) {
                players.remove(player.getId());
                final Delegate delegate = playerDelegate;
                if (delegate != null) {
                    delegate.playerWentAway(player);
                }
            }
        }
    }

    private void initialisePlayerExpiryCheck() {
        expiryThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(HEARTBEAT_PERIOD / 5);
                        tryExpire();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
        expiryThread.start();
    }

    public void initialise() {
        initialisePlayerExpiryCheck();
        externalStaticFileLocation("/Users/gmilos/Dropbox/Unison/Documents/Projects/GodsPlayground/webpage-resources");

        post("/login", (req, res) -> {
            final Login login = gson.fromJson(req.body(), Login.class);
            long identifier = handleLogin(login);
            res.cookie(COOKIE_IDENTIFIER, Long.toString(identifier));
            return "";
        });


        get("/longpoll", (req, res) -> {
            final Player player = getPlayer(Long.parseLong(req.cookie(COOKIE_IDENTIFIER)));
            player.setLastSeenTimestamp(System.currentTimeMillis());
            System.out.println("long poll for player: " + player);
            Notification notification = player.getNotification(HEARTBEAT_PERIOD);
            if (notification == null) {
                notification = new Notification(new Heartbeat());
            }
            return notification;
       }, gson::toJson);
    }

    @Override
    public void registerPlayerDelegate(Delegate delegate) {
        playerDelegate = delegate;
    }
}

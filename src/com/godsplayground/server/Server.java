package com.godsplayground.server;

import com.godsplayground.Avatar;
import com.godsplayground.Gameplay;
import com.godsplayground.UIController;
import com.godsplayground.server.model.client.*;
import com.godsplayground.server.model.server.Config;
import com.godsplayground.server.model.server.GameplayModel;
import com.godsplayground.server.model.server.PlayerModel;
import com.godsplayground.server.model.server.PlayerLogin;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

import static spark.Spark.*;

public class Server implements UIController {

    private static final Logger logger = Logger.getLogger(Server.class);

    static final String CONFIG_FILE = "server-resources/server-config.json";
    static final String COOKIE_IDENTIFIER = "user-identifier";
    static final long HEARTBEAT_PERIOD = 1000;


    private final Gson gson;
    private Thread expiryThread;

    private volatile Delegate delegate;

    private Config config;
    private final Map<PlayerModel.Identifier, PlayerModel> players;
    private final Map<Integer, GameplayModel> gameplays;

    public Server() {
        gson = new Gson();
        players = new HashMap<>();
        gameplays = new HashMap<>();
    }

    public void readConfig() throws FileNotFoundException {
        Gson gson = new Gson();
        final InputStream in = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
        this.config = gson.fromJson(new InputStreamReader(in), Config.class);
    }

    private PlayerModel handleLogin(final LoginRequest login) {
        final PlayerModel player = new PlayerModel(login.getUsername(), login.getPassword());
        final Delegate delegate = this.delegate;
        if (delegate != null) {
            delegate.newPlayer(player);
        }
        players.put(player.getId(), player);

        return player;
    }

    private synchronized PlayerModel getPlayer(final long id) {
        return players.get(new PlayerModel.Identifier(id));
    }

    private synchronized void tryExpire() {
        final long currentTime = System.currentTimeMillis();
        for (final PlayerModel player : players.values()) {
            if (currentTime > player.getLastSeenTimestamp() + HEARTBEAT_PERIOD * 2) {
                players.remove(player.getId());
                final Delegate delegate = this.delegate;
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

    public void initialise() throws FileNotFoundException {
        readConfig();
        initialisePlayerExpiryCheck();
        staticFileLocation("webpage-resources");

        post("/login", (req, res) -> {
            final LoginRequest login = gson.fromJson(req.body(), LoginRequest.class);
            final PlayerLogin.AuthResult authResult = config.authPlayerLogin(login);
            if (authResult == PlayerLogin.AuthResult.OK) {
                final PlayerModel player = handleLogin(login);
                res.cookie(COOKIE_IDENTIFIER, Long.toString(player.getId().getId()));
            }
            return authResult;
        }, gson::toJson);

        post("/gameplay", (req, res) -> {
            final PlayerModel player = getPlayer(Long.parseLong(req.cookie(COOKIE_IDENTIFIER)));
            final GameplayRequest gameplayReq = gson.fromJson(req.body(), GameplayRequest.class);
            logger.info("Gameplay request from: " + player + ", for: " + gameplayReq);
            final Gameplay gameplay = delegate.getGameplay(player, gameplayReq.getType());
            final GameplayModel gameplayModel = new GameplayModel(gameplay);
            gameplays.put(gameplayModel.getId(), gameplayModel);
            return gameplayModel.toResponse();
        }, gson::toJson);

        post("/avatars", (req, res) -> {
            final PlayerModel player = getPlayer(Long.parseLong(req.cookie(COOKIE_IDENTIFIER)));
            final AvatarsRequest avatarsReq = gson.fromJson(req.body(), AvatarsRequest.class);
            final GameplayModel gameplayModel = gameplays.get(avatarsReq.getGameplayId());
            logger.info("Avatars request from: " + player);
            final Set<Avatar> avatars = delegate.getAvatars(player, gameplayModel);
            return new AvatarsResponse(avatars);
        }, gson::toJson);

        post("/modifyAvatars", (req, res) -> {
            final PlayerModel player = getPlayer(Long.parseLong(req.cookie(COOKIE_IDENTIFIER)));
            final ModifyAvatarsRequest avatarsReq = gson.fromJson(req.body(), ModifyAvatarsRequest.class);
            final GameplayModel gameplayModel = gameplays.get(avatarsReq.getGameplayId());
            logger.info("Modify avatars request from: " + player);
            delegate.modifyAvatars(player, gameplayModel, avatarsReq.isAddOrRemove());
            return "";
        });

        get("/longpoll", (req, res) -> {
            final PlayerModel player = getPlayer(Long.parseLong(req.cookie(COOKIE_IDENTIFIER)));
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
    public void registerDelegate(Delegate delegate) {
        this.delegate = delegate;
    }
}

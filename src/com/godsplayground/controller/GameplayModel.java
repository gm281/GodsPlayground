package com.godsplayground.controller;

import com.godsplayground.Gameplay;
import com.godsplayground.server.model.client.AvatarsChanged;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GameplayModel implements Gameplay {

    private static final Random random = new Random(System.currentTimeMillis());
    private static final AtomicInteger gameplayIds = new AtomicInteger(13);

    private int id;
    private PlayerModel player;
    private String mapName;
    private Set<AvatarModel> avatars;

    private Thread tmpThread;

    public GameplayModel(final PlayerModel player) {
        this.id = gameplayIds.incrementAndGet();
        this.player = player;
        this.mapName = "earth";
        this.avatars = new HashSet<>();
        this.avatars.add(new AvatarModel());
        this.tmpForNotifications();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    public Set<AvatarModel> getAvatars() {
        return avatars;
    }

    public void modifyAvatars(final boolean addOrRemove) {
        if (addOrRemove) {
            avatars.add(new AvatarModel());
        } else
        if (avatars.size() > 0) {
            int item = random.nextInt(avatars.size());
            int i = 0;
            AvatarModel toRemove = null;
            for(final AvatarModel avatar : avatars)
            {
                if (i == item) {
                    toRemove = avatar;
                }
                i = i + 1;
            }
            avatars.remove(toRemove);
        }
    }

    public void tmpForNotifications() {
        final Thread thread = new Thread() {
            public void run() {
                for (;;) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    for (final AvatarModel avatar : avatars) {
                        avatar.move(random.nextInt() % 10, random.nextInt() % 10);
                    }
                    final AvatarsChanged avatarsChanged = new AvatarsChanged(GameplayModel.this.getId());
                    GameplayModel.this.player.sendNotification(avatarsChanged);
                }
            }
        };
        thread.start();
        tmpThread = thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameplayModel that = (GameplayModel) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

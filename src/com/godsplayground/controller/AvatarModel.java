package com.godsplayground.controller;

import com.godsplayground.Avatar;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AvatarModel implements Avatar {

    private static final AtomicInteger avatarIds = new AtomicInteger(13);
    private static final Random random = new Random();

    private final int id;
    private int xPosition;
    private int yPosition;

    public AvatarModel() {
        this.id = avatarIds.incrementAndGet();
        this.xPosition = random.nextInt() % 500 + 500;
        this.yPosition = random.nextInt() % 500 + 500;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void move(final int moveX, final int moveY) {
        xPosition += moveX;
        yPosition += moveY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvatarModel that = (AvatarModel) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

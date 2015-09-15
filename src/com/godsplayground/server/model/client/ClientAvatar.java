package com.godsplayground.server.model.client;

import com.godsplayground.Avatar;

public class ClientAvatar {
    private int id;
    private int xPosition;
    private int yPosition;

    public ClientAvatar(final Avatar avatar) {
        this.id = avatar.getId();
        this.xPosition = avatar.getxPosition();
        this.yPosition = avatar.getyPosition();
    }

    public int getId() {
        return id;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}


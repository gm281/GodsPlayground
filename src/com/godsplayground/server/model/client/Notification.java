package com.godsplayground.server.model.client;

public class Notification {

    private final String type;

    private Heartbeat Heartbeat;
    private AvatarsChanged AvatarsChanged;

    public Notification(Object value) {
        this.type = value.getClass().getSimpleName();
        if (value instanceof Heartbeat) {
            this.Heartbeat = (Heartbeat) Heartbeat;
        } else
        if (value instanceof AvatarsChanged) {
            this.AvatarsChanged = (AvatarsChanged)value;

        }
    }
}

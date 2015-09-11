package com.godsplayground.server.model.server;

import com.godsplayground.server.model.client.Notification;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Player implements com.godsplayground.Player {

    private final String username;
    private final String password;
    private final Identifier id;
    private final BlockingQueue<Object> notificationQueue;
    private final AtomicLong lastSeenTimestamp;

    public Player(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.id = new Identifier(username.hashCode());
        this.notificationQueue = new ArrayBlockingQueue<>(10);
        this.lastSeenTimestamp = new AtomicLong();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public void sendNotification(Object notification) {
        try {
            this.notificationQueue.put(notification);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setLastSeenTimestamp(long timestamp) {
        lastSeenTimestamp.set(timestamp);
    }

    public long getLastSeenTimestamp() {
        return lastSeenTimestamp.get();
    }

    public Notification getNotification(long timeout) {
        Object notification = null;
        try {
            notification = this.notificationQueue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return notification != null ? new Notification(notification) : null;
    }

    @Override
    public String toString() {
        return "<" + username + ", " + id.getId() + ">";
    }
}

package com.godsplayground.server.model.client;

public class Notification {

    private final String type;

    private Heartbeat Heartbeat;
    private GodsPlaygroundInterestingDataStructure GodsPlaygroundInterestingDataStructure;

    public Notification(Object value) {
        this.type = value.getClass().getSimpleName();
        if (value instanceof Heartbeat) {
            this.Heartbeat = (Heartbeat) Heartbeat;
        } else
        if (value instanceof  GodsPlaygroundInterestingDataStructure) {
            this.GodsPlaygroundInterestingDataStructure = (GodsPlaygroundInterestingDataStructure)value;

        }
    }
}

package com.godsplayground.server.model.server;

public class PlayerLogin {

    public static enum AuthResult {
        OK,
        UNKNOWN_USERNAME,
        WRONG_PASSWORD
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

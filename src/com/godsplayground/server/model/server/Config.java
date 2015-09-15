package com.godsplayground.server.model.server;

import com.godsplayground.server.model.client.Login;

public class Config {
    private PlayerLogin[] players;

    public PlayerLogin[] getPlayers() {
        return players;
    }

    public PlayerLogin.AuthResult authPlayerLogin(final Login login) {
        for (final PlayerLogin candidate : players) {
            if (candidate.getUsername().equals(login.getUsername())) {
                if (candidate.getPassword().equals(login.getPassword())) {
                    return PlayerLogin.AuthResult.OK;
                } else {
                    return PlayerLogin.AuthResult.WRONG_PASSWORD;
                }
            }
        }
        return PlayerLogin.AuthResult.UNKNOWN_USERNAME;
    }
}

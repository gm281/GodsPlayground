package com.godsplayground.server.model.client;

import com.godsplayground.Avatar;

import java.util.HashSet;
import java.util.Set;

public class AvatarsResponse {
    private Set<ClientAvatar> avatars;

    public AvatarsResponse(final Set<Avatar> avatars) {
        this.avatars = new HashSet<>();
        for (final Avatar avatar : avatars) {
            this.avatars.add(new ClientAvatar(avatar));
        }
    }
}

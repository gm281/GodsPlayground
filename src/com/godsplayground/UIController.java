package com.godsplayground;

import java.util.Set;

public interface UIController {

    public interface Delegate {
        public void newPlayer(final Player player);
        public void playerWentAway(final Player player);
        public Gameplay getGameplay(final Player player, final String type);
        public Set<Avatar> getAvatars(final Player player, final Gameplay gameplay);
        public void modifyAvatars(final Player player, final Gameplay gameplay, final boolean addOrRemove);
    }

    public void registerDelegate(final Delegate delegate);

}

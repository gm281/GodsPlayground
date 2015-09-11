package com.godsplayground;

public interface PlayerCreator {

    public interface Delegate {
        public void newPlayer(final Player player);
        public void playerWentAway(final Player player);
    }

    public void registerPlayerDelegate(final Delegate delegate);

}

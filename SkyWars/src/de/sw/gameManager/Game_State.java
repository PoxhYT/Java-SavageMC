package de.sw.gameManager;

public abstract class Game_State {

    public static int LOBBY_STATE = 0, INGAME_STATE = 1, ENDING_STATE = 2;

    public abstract void start();
    public abstract void stop();
}


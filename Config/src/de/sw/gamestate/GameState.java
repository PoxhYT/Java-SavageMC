package de.sw.gamestate;

public enum GameState {
    LOBBY("§aLobby"),
    INGAME("§6Ingame"),
    RESTART("§cRestart");

    public String name;

    GameState(String name) {
        this.name = name;
    }
}

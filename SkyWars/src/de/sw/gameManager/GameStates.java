package de.sw.gameManager;

public enum GameStates {
    LOBBY("§aLobby"),
    INGAME("§6Ingame"),
    RESTART("§cRestart");

    public String name;

    GameStates(String name) {
        this.name = name;
    }
}

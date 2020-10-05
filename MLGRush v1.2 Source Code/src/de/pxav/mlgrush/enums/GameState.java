package de.pxav.mlgrush.enums;

public enum GameState {

    LOBBY("§aLobby"),
    INGAME("§6Ingame"),
    ENDING("§cRestart");

    private String motd;

    GameState(final String motd) {
        this.motd = motd;
    }

    public String getMotd() {
        return motd;
    }




}

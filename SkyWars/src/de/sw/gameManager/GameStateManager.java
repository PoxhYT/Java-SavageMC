package de.sw.gameManager;

import de.sw.main.Main;

public enum GameStateManager {
    LOBBY, INGAME, Restart;

    private static GameStateManager currentstate;

    public static void setState(GameStateManager state) {
        currentstate = state;
    }

    public static boolean isState(GameStateManager state) {
        return (currentstate == state);
    }

    public static GameStateManager getState() {
        return currentstate;
    }

    public static String getStatus() {
        String s = "";
        if (isState(LOBBY)) {
            s = "§8»§aLobby";
        } else if (isState(INGAME)) {
            s = "§8»§eIngame";
        } else if (isState(Restart)) {
            s = "§8»§cRestart";
        }
        return s;
    }
}

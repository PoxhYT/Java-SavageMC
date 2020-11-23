package de.gamestateapi.main;

public enum GameStateAPIManager {
    LOBBY, INGAME, Restart, Maintenance, Offline;

    private static GameStateAPIManager currentstate;

    public static void setState(GameStateAPIManager state) {
        currentstate = state;
    }

    public static boolean isState(GameStateAPIManager state) {
        return (currentstate == state);
    }

    public static GameStateAPIManager getState() {
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
        } else if(isState(Maintenance)) {
            s = "§8»§4Maintenance";
        } else if(isState(Offline)) {
            s = "§8»§4Offline";
        }
        return s;
    }
}

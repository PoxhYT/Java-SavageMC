package de.sw.manager;

import java.util.ArrayList;

public class GameManager {

    public static ArrayList<String> games = new ArrayList<>();

    public static boolean isGameExist(String name) {
        return games.contains(name);
    }
}

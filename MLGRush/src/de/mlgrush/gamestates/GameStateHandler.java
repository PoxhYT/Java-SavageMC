package de.mlgrush.gamestates;

import de.mlgrush.enums.GameState;

public class GameStateHandler {

    private static GameState gameState;
    private static boolean allowMove;

    public static void setGameState(final GameState newGameState) {
        gameState = newGameState;
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static boolean isAllowMove() {
        return allowMove;
    }

    public static void setAllowMove(boolean allowMove) {
        GameStateHandler.allowMove = allowMove;
    }
}


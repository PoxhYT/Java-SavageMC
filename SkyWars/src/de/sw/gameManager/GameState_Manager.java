package de.sw.gameManager;

import de.sw.main.Main;

public class GameState_Manager {

    private Main instance;
    private Game_State[] game_states;
    private Game_State currentGame_State;

    public GameState_Manager(Main instance) {
        this.instance = instance;
        game_states = new Game_State[3];

        game_states[Game_State.ONLINE] = new Lobby_State(this);
        game_states[Game_State.INGAME_STATE] = new Ingame_State();
        game_states[Game_State.ENDING_STATE] = new Ending_State();
    }

    public void setGameState(int gameStateID) {
        if(currentGame_State != null)
            currentGame_State.stop();
        currentGame_State = game_states[gameStateID];
    }

    public void stopCurrentGameState() {
        if(currentGame_State != null) {
            currentGame_State.stop();
            currentGame_State = null;
        }
    }

    public Game_State getCurrentGame_State() {
        return currentGame_State;
    }

    public Main getInstance() {
        return instance;
    }
}

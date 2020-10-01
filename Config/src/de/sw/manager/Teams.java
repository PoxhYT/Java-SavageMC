package de.sw.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Teams {
    private String name;

    private List<Player> players;

    public Teams(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}


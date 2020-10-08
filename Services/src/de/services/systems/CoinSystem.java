package de.services.systems;

import de.services.mysql.MySQL;
import org.bukkit.entity.Player;

public class CoinSystem {
    private MySQL mySQL;

    public CoinSystem(MySQL sql) {
        this.mySQL = sql;
    }

    public void addCoins(Player player, int amount) {
        // Todo
    }

    public void removeCoins(Player player, int amount) {
        // Todo
    }
}

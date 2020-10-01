package de.sw.utils;

import de.sw.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Data {

    public static final int MIN_PLAYERS = 4;

    public static final int MAX_PLAYERS = 8;

    public static ArrayList<Player> playing = new ArrayList<>();

    public static ArrayList<Player> spectators = new ArrayList();

    public static ArrayList<Player> alive = new ArrayList<>();

    public void setStartItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.CHEST).setDisplayName("§8» §eKits").build());
        player.getInventory().setItem(2, new ItemBuilderAPI(Material.BED).setDisplayName("§8» §cTeams").build());
    }

    public void setSpectatorItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.getInventory().setItem(4, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§8» §bSpectator§7-§bTeleporter").build());
    }
}

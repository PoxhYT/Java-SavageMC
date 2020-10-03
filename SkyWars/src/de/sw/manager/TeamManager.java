package de.sw.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeamManager {

    private String name, prefix;
    private ArrayList<String> players;
    private byte colorData;
    private int maxPlayer;

    public TeamManager(String name, String prefix, byte colorData, int maxPlayer) {
        this.name = name;
        this.prefix = prefix;
        this.colorData = colorData;
        this.maxPlayer = maxPlayer;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public boolean isInTeam

    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(Material.WOOL, 1, colorData);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(prefix + name);
        meta.setLore(players);
    }
}

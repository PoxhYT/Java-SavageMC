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

    public TeamManager(String name, String prefix, byte colorData) {
        this.name = name;
        this.prefix = prefix;
        this.colorData = colorData;
        this.players = new ArrayList<>();
        this.maxPlayer = 4;

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

    public byte getColorData() {
        return colorData;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public boolean isInTeam(Player player) {
        if(players.contains(player.getName())) {
            return true;
        }
        return false;
    }

    public ItemStack getIcon() {
        Material type;
        ItemStack itemStack = new ItemStack(Material.WOOL, 1, colorData);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(prefix + name);
        itemMeta.setLore(players);
        return itemStack;
    }
}

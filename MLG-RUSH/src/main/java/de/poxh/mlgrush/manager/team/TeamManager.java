package de.poxh.mlgrush.manager.team;

import de.poxh.mlgrush.api.ItemBuilderAPI;
import de.poxh.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamManager implements Listener {

    private static ArrayList<PlayerTeamManager> teamManagers = new ArrayList<>();

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§9§lTeamauswahl");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§1 ").build());
            inventory.setItem(12, teamManagers.get(0).getTeamItem());
            inventory.setItem(14, teamManagers.get(1).getTeamItem());
        }
    }

    public void createTeam(String teamName, ItemStack itemStack, Player player, int colorCode) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§7§lTeam §8❘ §" + colorCode + teamName);
        itemStack.setItemMeta(itemMeta);
        player.sendMessage(Main.prefix + "§7§lDu hast das Team §8§l➞ §" + colorCode + teamName + " §7§lerstellt.");
        teamManagers.add(new PlayerTeamManager(teamName, itemStack));
    }
}

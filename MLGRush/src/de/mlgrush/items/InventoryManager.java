package de.mlgrush.items;

import de.mlgrush.api.ItemBuilder;
import de.mlgrush.enums.TeamType;
import de.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class InventoryManager {

    public void giveLobbyItems(Player player) {
        player.getInventory().setItem(0, new ItemBuilder(Material.BED).setDisplayName("§eTeamauswahl").build());
        player.getInventory().setItem(4, new ItemBuilder(Material.CHEST).setDisplayName("§6Cosmetics").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§cVerlassen").build());
    }

    public void openTeamSelector(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        ArrayList<String> lore1 = new ArrayList<>();
        if (!Main.getInstance().getTeamHandler().isTeamFull(TeamType.TEAM_1)) {
            lore1.add("§8» §cKEIN SPIELER");
        } else {
            for (Player current : Main.getInstance().getTeamHandler().getPlayersInTeam(TeamType.TEAM_1))
                lore1.add("§8» §9" + current.getName());
        }
        ArrayList<String> lore2 = new ArrayList<>();
        if (!Main.getInstance().getTeamHandler().isTeamFull(TeamType.TEAM_2)) {
            lore2.add("§8» §cKEIN SPIELER");
        } else {
            for (Player current : Main.getInstance().getTeamHandler().getPlayersInTeam(TeamType.TEAM_2))
                lore2.add("§8» §c" + current.getName());
        }
        for (int i = 0; i < 9; i++)
            inventory.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (short)7)).setDisplayName("§5 ").build());
            inventory.setItem(2, (new ItemBuilder(Material.WOOL, (short)11)).setDisplayName("§7Team: §9BLAU").setLore(lore1).build());
            inventory.setItem(6, (new ItemBuilder(Material.WOOL, (short)14)).setDisplayName("§7Team: §cROT").setLore(lore2).build());
            player.openInventory(inventory);
    }


    public void giveIngameItems(Player player) {
        player.getInventory().setItem(0, new ItemBuilder(Material.STICK).addEnchantment(Enchantment.KNOCKBACK, 1).build());
        player.getInventory().setItem(1, new ItemBuilder(Material.STONE_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SANDSTONE).setAmount(64).build());
    }
}

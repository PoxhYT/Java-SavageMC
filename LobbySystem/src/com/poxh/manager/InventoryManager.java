package com.poxh.manager;

import com.poxh.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public void SetupInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, "§eSetupInventory");
        for (int i = 0; i < 45; i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§e ").build());
        }
        inventory.setItem(12, new ItemBuilderAPI(Material.GRASS).setDisplayName("§eSkyWars").setLore("§7» §eKlicke, um dich zu SkyWars zu teleportieren").build());
        inventory.setItem(14, ItemBuilderAPI.getHead(player, "§eCommunity", "§7» §eKlicke, um dich zur Community zu teleportieren"));
        inventory.setItem(20, new ItemBuilderAPI(Material.IRON_SWORD).setDisplayName("§eSurvivalGames").setLore("§7» §eKlicke, um dich zu SurvivalGames zu teleportieren").build());
        inventory.setItem(22, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§eLobby").setLore("§7» §eKlicke, um dich zur Lobby zu teleportieren").build());
        inventory.setItem(24, new ItemBuilderAPI(Material.SANDSTONE).setDisplayName("§eClutches").setLore("§7» §eKlicke, um dich zu Clutches zu teleportieren").build());
        inventory.setItem(30, new ItemBuilderAPI(Material.BED).setDisplayName("§eBedWars").setLore("§7» §eKlicke, um dich zu BedWars zu teleportieren").build());
        inventory.setItem(32, new ItemBuilderAPI(Material.STICK).setDisplayName("§eMLG-RUSH").setLore("§7» §eKlicke, um dich zu MLG-RUSH zu teleportieren").build());
        player.openInventory(inventory);
    }

    public void openNavigator(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, "§eNavigator");
        for (int i = 0; i < 45; i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§e ").build());
        }
        inventory.setItem(12, new ItemBuilderAPI(Material.GRASS).setDisplayName("§eSkyWars").setLore("§7» §eKlicke, um dich zu SkyWars zu teleportieren").build());
        inventory.setItem(14, ItemBuilderAPI.getHead(player, "§eCommunity", "§7» §eKlicke, um dich zur Community zu teleportieren"));
        inventory.setItem(20, new ItemBuilderAPI(Material.IRON_SWORD).setDisplayName("§eSurvivalGames").setLore("§7» §eKlicke, um dich zu SurvivalGames zu teleportieren").build());
        inventory.setItem(22, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§eLobby").setLore("§7» §eKlicke, um dich zur Lobby zu teleportieren").build());
        inventory.setItem(24, new ItemBuilderAPI(Material.SANDSTONE).setDisplayName("§eClutches").setLore("§7» §eKlicke, um dich zu Clutches zu teleportieren").build());
        inventory.setItem(30, new ItemBuilderAPI(Material.BED).setDisplayName("§eBedWars").setLore("§7» §eKlicke, um dich zu BedWars zu teleportieren").build());
        inventory.setItem(32, new ItemBuilderAPI(Material.STICK).setDisplayName("§eMLG-RUSH").setLore("§7» §eKlicke, um dich zu MLG-RUSH zu teleportieren").build());
        player.openInventory(inventory);
    }

    public void LobbyInventory(Player player) {
        if(player.hasPermission("server.nick")) {
            player.getInventory().setItem(0, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§eNavigator").setLore("§7» Rechtsklick zum öffnen").build());
            player.getInventory().setItem(2, new ItemBuilderAPI(Material.CHEST).setDisplayName("§eGadgets").setLore("§7» Rechtsklick um die Gadgets zu öffnen").build());
            player.getInventory().setItem(4, new ItemBuilderAPI(Material.NAME_TAG).setDisplayName("§eNick").setLore("§7» Rechtsklick zum nicken").build());
            player.getInventory().setItem(6, new ItemBuilderAPI(Material.BLAZE_ROD).setDisplayName("§eSpieler anzeigen/verstecken").setLore("§7» Rechtsklick um Einstellungen zu wählen").build());
            player.getInventory().setItem(8, ItemBuilderAPI.getHead(player, "§eFreunde", "§7» Rechtsklick um deine Freundesliste zu öffnen"));
        } else {
            player.getInventory().setItem(0, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§eNavigator").setLore("§7» Rechtsklick zum öffnen").build());
        }
    }
}

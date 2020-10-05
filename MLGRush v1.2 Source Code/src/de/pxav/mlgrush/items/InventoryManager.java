package de.pxav.mlgrush.items;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class InventoryManager {

    private final String LOBBY_TEAM_SELECTOR = "§7§lTEAMAUSWAHL";
    private final String LOBBY_LEAVE = "§c§lSPIEL VERLASSEN";
    private final String TEAM_SEL_1 = "§7§lTEAM #1";
    private final String TEAM_SEL_2 = "§7§lTEAM #2";
    private final String TEAM_SEL_TITLE = "§7§lTEAMAUSWAHL";
    private final String GAME_STICK = "§7§lKNÜPPEL";
    private final String GAME_BLOCKS = "§7§lBLÖCKE";
    private final String GAME_PICKAXE = "§7§lSPTZHAKE";

    public void givePlayerLobbyItems(final Player player) {
        player.getInventory().setItem(0, new ItemBuilder(Material.BED).setDisplayName(LOBBY_TEAM_SELECTOR).build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SLIME_BALL).setDisplayName(LOBBY_LEAVE).build());
    }

    /**
     * Opens a menu to select the teams.
     * @param player The player who should see the inventory.
     */
    public void openTeamSelection(final Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 9, TEAM_SEL_TITLE);

        final ArrayList<String> lore1 = new ArrayList<>();
        lore1.add("§8§m---------------");
        if(!MLGRush.getInstance().getTeamHandler().isTeamFull(TeamType.TEAM_1))
            lore1.add("§cNoch kein Spieler");
        else {
            for (Player current : MLGRush.getInstance().getTeamHandler().getPlayersInTeam(TeamType.TEAM_1))
                lore1.add("§7" + current.getName());
        }
        lore1.add("§8§m---------------");

        final ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("§8§m---------------");
        if(!MLGRush.getInstance().getTeamHandler().isTeamFull(TeamType.TEAM_2))
            lore2.add("§cNoch kein Spieler");
        else {
            for (Player current : MLGRush.getInstance().getTeamHandler().getPlayersInTeam(TeamType.TEAM_2))
                lore2.add("§7" + current.getName());
        }
        lore2.add("§8§m---------------");

        for (int i = 0; i < 9; i++)
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).setDisplayName("§8PLATZHALTER").build());

        inventory.setItem(2, new ItemBuilder(Material.MELON).setDisplayName(TEAM_SEL_1).setLore(lore1).build());
        inventory.setItem(6, new ItemBuilder(Material.APPLE).setDisplayName(TEAM_SEL_2).setLore(lore2).build());

        player.openInventory(inventory);
    }

    public void givePlayItems(final Player player) {
        player.getInventory().setItem(0, new ItemBuilder(Material.STICK)
                .addEnchantment(Enchantment.KNOCKBACK)
                .setDisplayName(GAME_STICK)
                .build());
        player.getInventory().setItem(1, new ItemBuilder(Material.WOOD_PICKAXE)
                .addEnchantment(Enchantment.DURABILITY)
                .addEnchantment(Enchantment.DIG_SPEED)
                .setDisplayName(GAME_PICKAXE)
                .build());
        player.getInventory().setItem(2, new ItemBuilder(Material.SANDSTONE)
                .setDisplayName(GAME_BLOCKS)
                .setAmount(10)
                .build());
    }

    public String getLOBBY_TEAM_SELECTOR() {
        return LOBBY_TEAM_SELECTOR;
    }

    public String getLOBBY_LEAVE() {
        return LOBBY_LEAVE;
    }

    public String getTEAM_SEL_1() {
        return TEAM_SEL_1;
    }

    public String getTEAM_SEL_2() {
        return TEAM_SEL_2;
    }

    public String getTEAM_SEL_TITLE() {
        return TEAM_SEL_TITLE;
    }

}

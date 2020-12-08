package com.poxh.listener;

import com.poxh.api.ItemBuilderAPI;
import com.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ChestLotteryListener implements Listener {


    private HashMap<Player, Integer> openTickets = new HashMap<>();
    private HashMap<Player, Integer> endWin1 = new HashMap<>();
    private HashMap<Player, Integer> endWin2 = new HashMap<>();
    private HashMap<Player, Integer> endWin3 = new HashMap<>();
    private HashMap<Player, Integer> endWin4 = new HashMap<>();
    private HashMap<Player, Integer> endWin5 = new HashMap<>();
    private HashMap<Player, Integer> finalEndWin = new HashMap<>();


    public static void openInventory(Player player) {
        //Creating the Lottery for the Lobby
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "§eLottery");
        inventory.setItem(0, new ItemBuilderAPI(Material.CHEST).setDisplayName("§eÖffne mich❤️").build());
        player.openInventory(inventory);
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (event.getItem().getType() == Material.ENDER_CHEST) {
                openInventory(player);
                playSound(player, Sound.CHICKEN_EGG_POP);
            }
        }catch (NullPointerException e){}
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            if(event.getInventory().getTitle().equalsIgnoreCase("§eLottery")) {
                if(openTickets.get(player) == null || openTickets.get(player) == 5) {
                    //Creates the ticket inventory
                    Inventory inventory = Bukkit.createInventory(null, 54, "§eWähle 5 Tickets");
                    for (int i = 0; i < inventory.getSize(); i++) {
                        inventory.setItem(i, new ItemBuilderAPI(Material.ENDER_CHEST).setDisplayName("§eTicket").build());
                        player.openInventory(inventory);
                    }

                    openTickets.put(player, 0);
                } else {
                    player.sendMessage(Main.prefix + "§eDu öffnest aktuell ein Ticket!");
                }
            }
        }catch (NullPointerException e){}
    }
}

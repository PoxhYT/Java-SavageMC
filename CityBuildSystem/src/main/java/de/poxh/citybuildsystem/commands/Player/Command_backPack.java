package de.cb.poxh.commands.Player;

import de.cb.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Command_backPack implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                Inventory inventory = Bukkit.createInventory(player, 54, "§2§l " + player.getName() + "§7's " + "§2§lBackPack");
                if(Main.menu.containsKey(player.getUniqueId().toString())) {
                    inventory.setContents(Main.menu.get(player.getUniqueId().toString()));
                    player.openInventory(inventory);
                    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                }

                player.openInventory(inventory);
            }
        }

        return false;
    }

    @EventHandler
    public void onGUIClose(InventoryCloseEvent event) {
        if(event.getView().getTitle().contains(event.getPlayer().getName())) {
            Main.menu.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
            Main.instance.saveInv();
        }
    }
}

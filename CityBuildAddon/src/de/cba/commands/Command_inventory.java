package de.cba.commands;

import de.cba.main.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Command_inventory implements CommandExecutor {

    private HashMap<UUID, ItemStack[]> inventory = new HashMap<>();
    private boolean isSaved;
    private boolean alreadyGot;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("save")) {
                    if(!isSaved) {
                        inventory.put(player.getUniqueId(), player.getInventory().getContents());
                        player.getInventory().clear();
                        player.sendMessage(Main.prefix + "Dein §eInventar §7wurde §aerfolgreich §7gespeichert!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        isSaved = true;
                    } else if(isSaved) {
                        player.sendMessage(Main.prefix + "Du hast §cbereits §7ein §eInventar §7gespeichert!");
                        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
                    }
                } else if(args[0].equalsIgnoreCase("get")) {
                    if(!alreadyGot) {
                        player.sendMessage(Main.prefix + "§cACHTUNG§7! §7Bevor du dein §eInventar §7abrufen willst, solltest du dir sicher sein, dass dein §eInventar §cLEER §7ist.");
                        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
                        alreadyGot = true;
                    } else if(alreadyGot) {
                        if(inventory.containsKey(player.getUniqueId())) {
                            player.getInventory().setContents(inventory.get(player.getUniqueId()));
                            inventory.remove(player.getUniqueId());
                            player.sendMessage(Main.prefix + "Dein §eInventar §7wurde §aerfolgreich §7geladen!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                            isSaved = false;
                        } else
                            player.sendMessage(Main.prefix + "Du hast noch §cKEIN §eInventar §7gespeichert!");
                        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
                        alreadyGot = false;
                    }
                } else
                    player.sendMessage("§cBitte benutze §7/inv save/get§c!");
            } else
                player.sendMessage("§cBitte benutze §7/inv save/get§c!");
        }
        return false;
    }
}

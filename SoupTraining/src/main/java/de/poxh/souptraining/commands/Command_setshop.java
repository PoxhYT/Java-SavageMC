package de.soup.commands;

import de.soup.main.Main;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Command_setshop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Main.prefix + "§cDu musst ein Spieler sein!");
            return false;
        }

        Player player = (Player) sender;
        if(player.hasPermission("server.owner")) {
            if(args.length == 0) {
                Location loc = player.getLocation();
                Villager v = (Villager)loc.getWorld().spawnCreature(loc, CreatureType.VILLAGER);
                v.setCustomName("§8✘ §e§lShop §8✘");
                v.setCustomNameVisible(true);
                v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 356000, 356000));
                player.sendMessage(Main.prefix + "Der §eShop §7wurde platziert!");
                player.playSound(player.getLocation(loc), Sound.VILLAGER_YES, 1, 1);
            } else {
                player.sendMessage(Main.prefix + "§cBitte benutze den Befehl§7: §e/setshop");
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            }
        }else{
            player.sendMessage(Main.noPerms);
        }
        return false;
    }
}

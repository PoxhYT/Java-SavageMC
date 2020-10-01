package de.lobby.commands;

import de.lobby.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Command_setreward implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (p.hasPermission("server.owner") &&
                cmd.getName().equalsIgnoreCase("setreward")) {
            Location loc = p.getLocation();
            Villager v = (Villager)loc.getWorld().spawnCreature(loc, CreatureType.VILLAGER);
            v.setCustomName("§8• §r§eTägliche Belohnung §8•");
                    v.setCustomNameVisible(true);
            v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 356000, 356000));
            p.sendMessage(Main.prefix + "Der §r§eReward§7-§r§eVillager §r§7wurde gespawnt!");
        }
        return true;
    }
}


package de.bungee.commands;

import de.bungee.main.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Command_ping implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            if(args.length == 0) {
                Player player = (Player) sender;
                player.sendMessage(Main.prefix + "Dein aktueller Ping lautet: §e" + getPing(player) + "ms");
            }else if(args.length == 1) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    sender.sendMessage(Main.prefix + "Der Ping von §e" + args[0] + " §7lautet: §e" + getPing(Bukkit.getPlayer(args[0])) + "ms");
                }else{
                    sender.sendMessage(Main.prefix + "§cDieser Spieler ist nicht online!");
                }
            }else{
                sender.sendMessage(Main.prefix + "§cBenutze§7: /ping ");
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.VILLAGER_DEATH, 1, 1);
            }

        }else{
            sender.sendMessage(Main.prefix + "§cDieser Befehl kann nur durch ein Spieler ausgeführt werden!");
        }
        return false;
    }

    public static Integer getPing(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer ping = craftPlayer.getHandle();
        return ping.ping;
    }
}

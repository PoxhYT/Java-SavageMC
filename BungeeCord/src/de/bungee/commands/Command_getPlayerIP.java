package de.bungee.commands;

import de.bungee.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

import static de.bungee.commands.Command_ping.getPing;

public class Command_getPlayerIP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {

                Player player = (Player) sender;
                Player target = Bukkit.getPlayer(args[0]);
                InetSocketAddress getIP = target.getAddress();

                player.sendMessage(Main.prefix + "Deine aktuelle IP lautet: §e" + target.getAddress());
            }else if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(Bukkit.getPlayer(args[0]) != null) {
                    sender.sendMessage(Main.prefix + "Die aktuelle IP von " + target.getDisplayName() + " §7lautet: §e" + target.getAddress());
                }else{
                    sender.sendMessage(Main.prefix + "§cDieser Spieler ist nicht online!");
                }
            }else{
                sender.sendMessage(Main.prefix + "§cBenutze§7: /ip <name> ");
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.VILLAGER_DEATH, 1, 1);
            }

        }else{
            sender.sendMessage(Main.prefix + "§cDieser Befehl kann nur durch ein Spieler ausgeführt werden!");
        }
        return false;
    }
}

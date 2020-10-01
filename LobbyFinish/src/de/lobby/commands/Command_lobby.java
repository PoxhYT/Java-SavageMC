package de.lobby.commands;

import de.lobby.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_lobby implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        Player player = (Player)sender;
        if (sender instanceof Player)
            if (player.hasPermission("lobby.admin")) {
                player.sendMessage("");
                player.sendMessage(Main.prefix + "§8• §r§eSavageMC§7-§r§eLobbySystem §8•");
                player.sendMessage(Main.prefix + "§8• §r§7Befehle§7:");
                player.sendMessage(Main.prefix + "§8• §7/setspawn [setze den Spawn].");
                player.sendMessage(Main.prefix + "§8• §7/vanish [Aktiviere den Vanishmodus].");
                player.sendMessage(Main.prefix + "§8• §7/build [Aktiviere den Buildmodus].");
                player.sendMessage(Main.prefix + "§8• §7/fly [Aktiviere den Flugmodus].");
                player.sendMessage(Main.prefix + "§8• §7/coins [set], [add], [remove] <Spieler>");
                player.sendMessage("");
            } else if (player.hasPermission("server.youtuber")) {
                player.sendMessage("");
                player.sendMessage(Main.prefix + "§8• §r§eSavageMC§7-§r§eLobbySystem §8•");
                player.sendMessage(Main.prefix + "§8• §r§7Befehle§7:");
                player.sendMessage(Main.prefix + "§8• §7/vanish [Aktiviere den Vanishmodus].");
                player.sendMessage(Main.prefix + "§8• §7/fly [Aktiviere den Flugmodus].");
                player.sendMessage(Main.prefix + "§8• §7/coins <Spieler> zeige die Coins von dir, oder von andere Spieler an.");
                player.sendMessage("");
            } else if (player.hasPermission("lobby.team")) {
                player.sendMessage("");
                player.sendMessage(Main.prefix + "§8• §r§eSavageMC§7-§r§eLobbySystem §8•");
                player.sendMessage(Main.prefix + "§8• §r§7Befehle§7:");
                player.sendMessage(Main.prefix + "§8• §7/vanish [Aktiviere den Vanishmodus].");
                player.sendMessage(Main.prefix + "§8• §7/fly [Aktiviere den Flugmodus].");
                player.sendMessage(Main.prefix + "§8• §7/coins <Spieler> zeige die Coins von dir, oder von andere Spieler an.");
                player.sendMessage("");
            } else {
                player.sendMessage("");
                player.sendMessage(Main.prefix + "§8• §r§eSavageMC§7-§r§eLobbySystem §8•");
                player.sendMessage(Main.prefix + "§8• §r§7Befehle§7:");
                player.sendMessage(Main.prefix + "§8• §7/coins <Spieler> zeige die Coins von dir, oder von andere Spieler an.");
                player.sendMessage("");
            }
        return true;
    }
}


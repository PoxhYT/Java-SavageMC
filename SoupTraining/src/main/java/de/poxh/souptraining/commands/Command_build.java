package de.soup.commands;

import de.soup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_build implements CommandExecutor {
    @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.builder")) {
                if(args.length == 0) {
                    if(Main.build.contains(player)) {
                        Main.build.remove(player);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Main.prefix + "§7Der §aBaumodus §7wurde für dich §cdeaktiviert§7.");
                    } else {
                        Main.build.add(player);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(Main.prefix + "§r§7Der §aBaumodus §7wurde für dich §r§aaktiviert§7.");
                    }
                } else if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        if(Main.build.contains(target)) {
                            Main.build.remove(target);
                            target.getInventory().clear();
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(Main.prefix + "§r§7Der " + player.getDisplayName() + " §r§7hat dir den §r§aBaumodus §r§centzogen§7.");
                            player.sendMessage(Main.prefix + "§r§7Du hast " + target.getDisplayName() + " §r§cverboten zu bauen§7." );
                        } else {
                            Main.build.add(target);
                            target.getInventory().clear();
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(Main.prefix + "§r§7Der " + player.getDisplayName() + " §r§7hat dir den §r§aBaumodus §r§ahinzugefügt§7.");
                            player.sendMessage(Main.prefix + "§r§7Du hast " + target.getDisplayName() + " §r§7erlaubt zu bauen§7." );
                        }
                    } else
                        player.sendMessage(Main.notFound);
                } else
                    player.sendMessage(Main.prefix + "§r§7Benutze §8➜ §7/§r§7build <Spieler>.");
            } else
                player.sendMessage(Main.noPerms);
        } else
            sender.sendMessage(Main.prefix + "§cDu musst ein Spieler sein.");
        return false;
    }
}

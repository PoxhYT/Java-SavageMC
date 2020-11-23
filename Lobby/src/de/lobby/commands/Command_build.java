package de.lobby.commands;

import de.lobby.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_build implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(player.hasPermission("server.builder")) {
                if(args.length == 0) {
                    if(!Main.build.contains(player)) {
                        Main.build.add(player);
                        player.sendMessage(Main.prefix + "Du hast den §aBuildingmodus §7aktiviert!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP,1 ,1);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.CREATIVE);
                    } else if(Main.build.contains(player)) {
                        Main.build.remove(player);
                        player.sendMessage(Main.prefix + "Du hast den §aBuildingmodus §cdeaktiviert§7!");
                        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH,1 ,1);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.SURVIVAL);
                        Main.getInstance().inventoryManager.setLobbyInventory(player);
                    }
                }
            } else
                player.sendMessage(Main.noPerms);
            player.playSound(player.getLocation(), Sound.VILLAGER_DEATH,1 ,1);

        } else
            player.sendMessage(Main.prefix + "§cDu kannst au der Konsole keine Befehle ausführen!");
        return false;
    }
}

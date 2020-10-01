package de.mlgrush.commands;

import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_Build implements CommandExecutor {

    public Command_Build(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player){
            final Player player = (Player)sender;
            if(player.hasPermission("server.owner")) {
                final String prefix = Main.getInstance().getPrefix();

                if (args.length == 0){
                    final PlayerManager playerManager = new PlayerManager(player);
                    if (playerManager.isInBuildMode()){
                        playerManager.setBuildMode(false);
                        player.sendMessage(prefix + "§7Du kannst §7nun §anicht §7mehr §7bauen§8.");
                        playerManager.restoreInventory();
                    } else {
                        playerManager.setBuildMode(true);
                        player.sendMessage(prefix + "§7Du kannst §anun §7bauen§8.");
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(null);
                        player.setGameMode(GameMode.CREATIVE);
                        playerManager.saveInventory();
                    }
                } else if(args.length == 1) {
                    if(player.hasPermission("mlgrush.build.*") || player.hasPermission("mlgrush.*") || player.hasPermission("mlgrush.build.others")){
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null){
                            final PlayerManager targetManager = new PlayerManager(target);
                            if(targetManager.isInBuildMode()){
                                targetManager.setBuildMode(false);
                                target.sendMessage(prefix + "§7Du kannst §7nun §anicht §7mehr §7bauen!");
                                player.sendMessage(prefix + "§7Spieler §a" + target.getName() + " §7aus Buildmode entfernt§8.");
                                targetManager.restoreInventory();
                            } else {
                                targetManager.setBuildMode(true);
                                target.sendMessage(prefix + "§7Du kannst §anun §7bauen!");
                                player.sendMessage(prefix + "§7Spieler §a" + target.getName() + " §7in Buildmode gesetzt§8.");
                                target.setGameMode(GameMode.CREATIVE);
                                target.getInventory().clear();
                                target.getInventory().setArmorContents(null);
                                targetManager.saveInventory();
                            }
                        } else
                            player.sendMessage(prefix + "§cDieser Spieler ist nicht online!");
                    } else
                        player.sendMessage(Main.getInstance().getPrefix() + "§cDazu hast du keine Rechte.");
                }
            } else
                player.sendMessage(Main.getInstance().getPrefix() + "§cDazu hast du keine Rechte.");
        } else {
            if (args.length == 0) {
                sender.sendMessage("[MLGRUSH] You must be a player.");
            } else if(args.length == 1) {
                final Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    final PlayerManager targetManager = new PlayerManager(target);
                    if (targetManager.isInBuildMode()) {
                        targetManager.setBuildMode(false);
                        target.sendMessage(Main.getInstance().getPrefix() + "§cDu kannst nun §anicht §7mehr bauen.");
                        sender.sendMessage(Main.getInstance().getPrefix() + " is no longer able to build!");
                        targetManager.restoreInventory();
                    } else {
                        sender.sendMessage(Main.getInstance().getPrefix() + target.getName() + " is now able to build!");
                        target.sendMessage(Main.getInstance().getPrefix() + "§7Du kannst §anun §7bauen.");
                        targetManager.setBuildMode(true);
                        target.setGameMode(GameMode.CREATIVE);
                        target.getInventory().clear();
                        target.getInventory().setArmorContents(null);
                        targetManager.saveInventory();
                    }
                } else
                    sender.sendMessage("[MLGRUSH] This player is not online.");
            } else {
                sender.sendMessage("[MLGRUSH] Syntax: /build <Player>");
            }
        }

        return true;
    }

}


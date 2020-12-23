package de.lobby.commands;

import com.rosemite.services.main.MainService;
import com.rosemite.services.models.player.PlayerInfo;
import de.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_removeBan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof  Player) {
            if(player.hasPermission("server.supporter")) {
                if (args.length == 2) {
                    PlayerInfo target = MainService.getService(null).getPlayerService().getPlayerInfoByName(args[0]);

                    int removeBans = Integer.valueOf(args[1]);

                    if (target != null) {
                        if (MainService.getService(null).getPlayerService().getBanAmount(target.getUuid()) != 0) {
                            MainService.getService(null).getPlayerService().removeBans(target.getUuid(), removeBans);
                            player.sendMessage(Main.prefix + "Du hast " + target.getPlayername() + " §e" + removeBans + " §7Bans entfernt!");
                            player.sendMessage(Main.prefix + "§eSeine aktuelle Baneinträge lauten: " + MainService.getService(null).getPlayerService().getBanAmount(target.getUuid()));
                        } else {
                            player.sendMessage(Main.prefix + target.getPlayername() + " §chat bisher keine Baneinträge!");
                            return false;
                        }
                    } else {
                        player.sendMessage(Main.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert!");
                        return false;
                    }
                }
            } else {
                player.sendMessage(Main.noPerms);
                return false;
            }
        }
        return false;
    }
}

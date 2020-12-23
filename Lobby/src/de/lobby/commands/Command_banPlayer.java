package de.lobby.commands;

import com.rosemite.services.main.MainService;
import de.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

public class Command_banPlayer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof  Player) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {

                    Date date = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DAY_OF_MONTH, 5);

                    MainService.getService(null).getPlayerService().setPlayerBan(target.getUniqueId().toString(), c.getTime().toString());

                    player.sendMessage(Main.prefix + "§eEs hat funktioniert!");
                }
            }
        }
        return false;
    }
}

package de.soup.commands;

import com.google.common.collect.Maps;
import de.soup.main.Main;
import de.soup.timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Command_timer implements CommandExecutor {

    private static Map<UUID, Timer> timers = Maps.newHashMap();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Main.prefix + "§cDu bist kein Spieler!");
            return false;
        }

        Player player = (Player)sender;

        if(args.length == 1 && args[0].equalsIgnoreCase("start")) {
            Timer timer = new Timer();
            timer.start(player);
            player.sendMessage(Main.prefix + "Dein §eTimer §7wurde gestartet!");
            timers.put(player.getUniqueId(), timer);
            return true;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            Timer timer = timers.get(player.getUniqueId());
            timer.stop();

            player.sendMessage(Main.prefix + "Deine §eZeit§8: §6" + timer.getElapsedTime());
            return true;
        }
        return false;
    }
}

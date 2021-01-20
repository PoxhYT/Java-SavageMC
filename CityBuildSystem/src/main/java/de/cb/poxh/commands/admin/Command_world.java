package de.cb.poxh.commands.admin;

import com.rosemite.helper.Log;
import de.cb.poxh.api.WorldAPI;
import de.cb.poxh.main.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Command_world implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 2) {
                String worldName = args[1];

                if(args[0].equals("create")) {
                    WorldAPI.createWorld(worldName, player);
                }

                if(args[0].equals("remove")) {
                    WorldAPI.removeWorld(worldName, player);
                }

                if(args[0].equals("teleport")) {
                    WorldAPI.randomTeleport(worldName, player);
                }

            }
        }

        return false;
    }
}

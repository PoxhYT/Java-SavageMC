package de.sw.commands;

import de.gamestateapi.main.GameStateAPIManager;
import de.sw.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_setup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if(sender instanceof Player) {
            if (args.length == 0) {
                if(Main.countdown.isRunning()) {
                    Main.countdown.stop();
                }
                player.setLevel(0);
                player.sendMessage(Main.prefix + "Willkommen im §eSetup§7!" + Command_location.name);
                player.performCommand("build");
                GameStateAPIManager.setState(GameStateAPIManager.SETUP);
            }
        }
        return false;
    }
}

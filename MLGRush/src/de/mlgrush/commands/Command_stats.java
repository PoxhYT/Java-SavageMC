package de.mlgrush.commands;

import de.mlgrush.api.SQLStats;
import de.mlgrush.main.Main;
import javafx.beans.binding.Bindings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Command_stats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("stats")) {

                int kills = SQLStats.getKills(player.getUniqueId().toString());
                int deaths = SQLStats.getDeaths(player.getUniqueId().toString());

                double KD = ((double) kills) / ((double) deaths);

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formatted = decimalFormat.format(KD);


                if (args.length == 0) {
                    player.sendMessage("§7-= §8•§eStatistiken §7von §e" + player.getName() + "§8• §7=-");
                    player.sendMessage("§r ");
                    player.sendMessage("§7Kills: §e" + kills);
                    player.sendMessage("§7Deaths: §e" + deaths);
                    player.sendMessage("§7Wins: §e" + SQLStats.getWins(player.getUniqueId().toString()));
                    player.sendMessage("§7KD: §e" + formatted.replace("Infinity", "0").replace("NaN", "0"));
                    player.sendMessage("§5 ");
                    player.sendMessage("§7-=-------------------=-");


                } else {
                    sender.sendMessage(Main.prefix + "Benutzung: /§estats §7<§eSpieler§7>");
                }
            }
        }
        return false;
    }
}


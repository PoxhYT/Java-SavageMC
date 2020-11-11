package de.cba.commands;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import de.cba.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Command_Pay implements CommandExecutor {

    private MainService services;
    private MongoDatabase mongoDatabase;

    public Command_Pay(MainService services, MongoDatabase mongoDatabase) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
        services = MainService.getService(services);

        if(sender instanceof Player) {
            if(args.length == 0) {
                player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins pay §7<§eNAME§7>");

                CoinService coinService = services.getCoinService();
                if(coinService == null) {
                    coinService = new CoinService(mongoDatabase, services);
                    Log.d("NICE");
                }

                coinServiceMap.put(coinService, player.getUniqueId());
                Log.d("WELL");

            } else {
                if(args[1].equalsIgnoreCase("pay")) {
                    if(args.length == 1) {
                        player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins pay §7<§eNAME§7>");
                    } else
                        player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins pay §7<§eNAME§7>");
                }

                if(args.length == 2) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    
                }
            }
        } else
            sender.sendMessage(Main.onlyPlayers);

        return false;
    }
}

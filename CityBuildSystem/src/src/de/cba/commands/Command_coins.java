package src.de.cba.commands;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.de.cba.main.Main;

import java.util.HashMap;
import java.util.UUID;

public class Command_coins implements CommandExecutor {

    private MainService services;
    private MongoDatabase mongoDatabase;

    public Command_coins(MainService services, MongoDatabase mongoDatabase) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
            services = MainService.getService(services);
            if(args.length == 0) {
                CoinService coinService = services.getCoinService();
                if(coinService == null) {
                    coinService = new CoinService(mongoDatabase, services);
                }

                coinServiceMap.put(coinService, player.getUniqueId());
                player.sendMessage(Main.prefix + "Du besitzt aktuell §e" + coinService.getCoinAmount(player.getUniqueId().toString()) + " Coins");

            } else
                player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins");
        } else
            sender.sendMessage(Main.onlyPlayers);
        return false;
    }
}

package de.lobby.commands;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.services.coin.CoinService;
import de.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Command_coins implements CommandExecutor {

    private MainService services;
    private MongoDatabase mongoDatabase;
    private CoinService coinService;

    public Command_coins(MongoDatabase mongoDatabase, MainService services) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
        this.coinService = services.getCoinService();
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
            }

            if(args.length == 1) {
                player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins add §7<§ename§7> <§eamount§7>");
            }

            if(args.length == 2) {
                player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins add §7<§ename§7> <§eamount§7>");
            }

            if(args.length == 3) {
                String name = args[1];
                if (args[0].equalsIgnoreCase("add")) {

                    PlayerInfo targetInfo = services.getPlayerService().getPlayerInfoByName(name);

                    if (targetInfo == null) {
                        player.sendMessage(Main.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert!");
                        return false;
                    }

                    Integer amount = Integer.valueOf(args[2]);
                    coinService.addCoins(targetInfo.getUuid(), amount);

                    Player target = Bukkit.getPlayer(UUID.fromString(targetInfo.getUuid()));

                    target.sendMessage(Main.prefix + "Es wurden §e" + amount + " Coins §7auf dein §eKonto §7gutgeschrieben!");
                    target.sendMessage(Main.prefix + "Dein aktueller §eKontostand §7lautet " + coinService.getCoinAmount(target.getUniqueId().toString()));
                }
            }
        } else
            sender.sendMessage("§cNÖ");
        return false;
    }
}

package src.de.cba.commands;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.services.coin.CoinService;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.de.cba.main.Main;

import java.util.HashMap;
import java.util.UUID;

public class Command_Pay implements CommandExecutor {

    private MainService services;
    private CoinService coinService;

    public Command_Pay() {
        this.services = MainService.getService(null);
        this.coinService = services.getCoinService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(sender instanceof Player) {
            if(args.length == 0) {
                player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§epay §7(§ename§7) (§eamount§7)");
            } else {
                if(args.length == 1) {
                    player.sendMessage(Main.prefix);
                    player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§epay §7(§ename§7) (§eamount§7)");
                }

                if(args.length == 2) {

                    Player target =  Bukkit.getPlayerExact(args[0]);
                    int amount = Integer.parseInt(args[1]);
                    Pair<Integer, Boolean> res = coinService.removeCoins(player.getUniqueId().toString(), amount);
                    PlayerInfo targetInfo = services.getPlayerService().getPlayerInfo(target.getUniqueId().toString());

                    if(targetInfo != null) {
                        if (!res.getValue()) {
                            player.sendMessage(Main.prefix + "§cDu hast nicht genügende Coins!");
                        } else {
                            coinService.addCoins(target.getUniqueId().toString(), amount);
                            player.sendMessage(Main.prefix + "Du hast " + target.getDisplayName() + " §e" +
                                    coinService.getCoinAmount(target.getUniqueId().toString()) + " Coins §7geschenkt!");
                            target.sendMessage(Main.prefix + player.getDisplayName() + " §7hat dir §e" + amount + " Coins §7geschenkt!");
                        }
                        Log.d("WELL");
                    } else
                        player.sendMessage(Main.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert!");
                }
            }
        } else
            sender.sendMessage(Main.onlyPlayers);

        return false;
    }
}

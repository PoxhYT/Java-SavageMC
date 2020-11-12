package src.de.cba.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import src.de.cba.main.Main;

import java.util.Random;

public class BlockBreakListener implements Listener {

    private MainService services;
    private CoinService coinService;

    public BlockBreakListener() {
        this.services = MainService.getService(null);
        this.coinService = services.getCoinService();
    }

    public void onBlockBrake(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if(event.getBlock().getType() == Material.SAND) {
            Log.d("OKAY");
            //Creates a random chance between 1 - 100

            Random chance = new Random();
            int chanceNumber = 50;
            int randomChance = chance.nextInt(chanceNumber);

            //Creates a random int between 1 - 100

            Random coins = new Random();
            int coinsNumber = 100;
            int randomCoins = chance.nextInt(50 - coinsNumber);

            if(randomChance < 50) {
                player.sendMessage(Main.prefix + "Du hast §e" + randomCoins + " Coins §7gefunden!");
                coinService.addCoins(player.getUniqueId().toString(), randomCoins);
                player.sendMessage(Main.prefix + "Du besitzt aktuell §e" + coinService.getCoinAmount(uuid) + " Coins§7.");
            }
        }

        if(event.getBlock().getType() == Material.IRON_ORE) {
            //Creates a random chance between 1 - 100

            Random chance = new Random();
            int chanceNumber = 50;
            int randomChance = chance.nextInt(chanceNumber);

            //Creates a random int between 1 - 100

            Random coins = new Random();
            int coinsNumber = 70;
            int randomCoins = chance.nextInt(35 - coinsNumber);

            if(randomChance < 50) {
                player.sendMessage(Main.prefix + "Du hast §e" + randomCoins + " Coins §7gefunden!");
                coinService.addCoins(player.getUniqueId().toString(), randomCoins);
                player.sendMessage(Main.prefix + "Du besitzt aktuell §e" + coinService.getCoinAmount(uuid) + " Coins§7.");
            }
        }
    }
}
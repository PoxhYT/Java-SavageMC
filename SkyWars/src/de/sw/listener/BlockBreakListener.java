package de.sw.listener;

import de.sw.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class BlockBreakListener implements Listener {

    public void onBlockBrake(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(event.getBlock().getType() == Material.DIAMOND_ORE) {
            //Creates a random chance between 1 - 100

            Random chance = new Random();
            int chanceNumber = 100;
            int randomChance = chance.nextInt(chanceNumber);

            //Creates a random int between 1 - 100

            Random coins = new Random();
            int coinsNumber = 100;
            int randomCoins = chance.nextInt(50 - coinsNumber);

            if(randomChance < 50) {
                coinsNumber = 50;
                player.sendMessage(Main.prefix + "Du hast §e" + randomCoins + " Coins §7gefunden!");
            }
        }
    }
}

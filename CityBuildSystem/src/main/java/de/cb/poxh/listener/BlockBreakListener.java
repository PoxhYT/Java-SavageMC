package de.cb.poxh.listener;

import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (block.getBlockData().getMaterial().name().toLowerCase().contains("ore")) {
            Random rm = ThreadLocalRandom.current();
            float chance = rm.nextFloat();
            float normal = 0.85F;
            float epic = 0.10F;
            float legendary = 0.05F;

            if ((chance <= epic) || chance >= legendary) {
                Integer coins = rm.nextInt(200 - 30 + 1);
                player.sendMessage(String.valueOf(coins));

            }
        }
    }
}

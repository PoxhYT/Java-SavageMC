package de.cb.poxh.commands.countdowns;

import de.cb.poxh.main.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Countdown_delay extends BukkitRunnable {

    public final Map<Player, Integer> delayForCountdown = new HashMap<>();
    public int taskID;

    @Override
    public void run() {

        Set<Player> remove = new HashSet<>();
        for (Player player : delayForCountdown.keySet()) {
            int timer = delayForCountdown.compute(player, (k, v) -> v - 1);

            if (timer == 0) {
                remove.add(player);
            }

            switch (timer) {
                case 60: case 50: case 40: case 30: case 20: case 15: case 10:
                    player.sendMessage(Main.instance.prefix + "Dein §b§lTeleportDelay §7endet in §b§l" + timer + " Sekunden! ");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    break;
                case 5:
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    player.sendTitle("§b§lTeleportDelay", "§c§lläuft ab §7...");
                case 4: case 3: case 2:
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    player.sendTitle("§b§lTeleportDelay", "§c§lläuft ab §7...");
                    break;
                case 1:
                    player.sendMessage(Main.instance.prefix + "§4§lDein TeleportDelay ist abgelaufen");
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
                    break;
                case 0:
                    Main.instance.countdown_delay.delayForCountdown.remove(player);
                    break;
            }
        }


        remove.forEach(delayForCountdown::remove);
    }

    public void registerCountdown(Player player, int value) {
        player.sendMessage(Main.instance.prefix + "Dein §b§lTeleportDelay §7endet in §b§l60 Sekunden! ");
        delayForCountdown.put(player, value);
    }
}

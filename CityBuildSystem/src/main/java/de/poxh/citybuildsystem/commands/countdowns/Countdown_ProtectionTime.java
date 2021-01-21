package de.cb.poxh.commands.countdowns;

import de.cb.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Countdown_ProtectionTime extends BukkitRunnable implements Listener{

    public final Map<Player, Integer> countdowns = new HashMap<>();
    private boolean isRunning;

    @Override
    public void run() {
        isRunning = true;
        Set<Player> remove = new HashSet<>();
        for (Player player : countdowns.keySet()) {
            int timer = countdowns.compute(player, (k, v) -> v - 1);

            if (timer == 0) {
                remove.add(player);
            }

            switch (timer) {
                case 25:
                    player.sendMessage(Main.instance.prefix + "Deine §b§lSchutzzeit §7endet in §b§l" + timer + " Sekunden!");
                    player.sendMessage(String.valueOf(isRunning));
                    break;
                case 20: case 15: case 10:
                    player.sendMessage(String.valueOf(isRunning));
                    player.sendMessage(Main.instance.prefix + "Deine §b§lSchutzzeit §7endet in §b§l" + timer + " Sekunden!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    break;
                case 5:
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    player.sendTitle("§b§lSchutzzeit", "§c§lläuft ab §7...");
                case 4: case 3: case 2:
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    player.sendTitle("§b§lSchutzzeit", "§c§lläuft ab §7...");
                    break;
                case 1:
                    player.sendMessage(Main.instance.prefix + "§4§lACHTUNG deine Schutzzeit ist abgelaufen");
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
                    break;
                case 0:
                    if(!Main.instance.countdown_delay.delayForCountdown.containsKey(player) || Bukkit.getScheduler().isCurrentlyRunning(Main.instance.countdown_delay.getTaskId())) {
                        Main.instance.countdown_delay.registerCountdown(player, 20);
                        Countdown_delay countdown_delay = new Countdown_delay();
                        countdown_delay.registerCountdown(player, 60);
                        countdown_delay.runTaskTimer(Main.getInstance(), 0, 20);
                        countdowns.remove(player);
                        Main.instance.countdown_protectionTime.countdowns.remove(player);
                    }
                    break;
            }
        }
    }

    public void registerCountdown(Player player, int value) {
        countdowns.put(player, value);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if(countdowns.containsKey(player)) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

}

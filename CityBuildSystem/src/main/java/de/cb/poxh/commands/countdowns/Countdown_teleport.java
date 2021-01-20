package de.cb.poxh.commands.countdowns;

import de.cb.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class Countdown_teleport{

    public int timer = 10;
    private int taskID;
    private Main instance;
    private boolean isRunning;

    public Countdown_teleport(Main instance){
        this.instance = instance;
    }

    public void start(Player player, Player target) {
        isRunning = true;
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                switch (timer) {
                    case 10:
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        break;
                    case 5:
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        player.sendTitle("§b§lTeleportiere§7...", "§c");
                    case 4: case 3: case 2:
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        player.sendTitle("§b§lTeleportiere§7...", "§3");
                        break;
                    case 1:
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        player.sendTitle("§b§lTeleportiere§7...", "§1");
                        break;
                    case 0:
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        teleportPlayerToTarget(player, target);
                        stop();
                        break;
                }
                timer--;
            }
        }, 0, 20);
    }

    public void stop() {
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            timer = 10;
        }
    }

    private void teleportPlayerToTarget(Player player, Player target) {
        player.teleport(target.getLocation());
    }
}

package de.signsystem.events;

import de.signsystem.api.ServerSign;
import de.signsystem.main.Main;
import de.signsystem.main.Var;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class SignInteractListener implements Listener {

    private ArrayList<Player> used = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType() != Material.SIGN && b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN)
                return;
            final Player p = e.getPlayer();
            if (!this.used.contains(p))
                for (ServerSign all : Main.serversigns) {
                    if (all.getLocation().equals(b.getLocation())) {
                        if (this.used.contains(p))
                            return;
                        this.used.add(p);
                        Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), new Runnable() {
                            public void run() {
                                SignInteractListener.this.used.remove(p);
                            }
                        },  10L);
                        if (all.getSign().getLine(1).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Var.LOADING))) {
                            if (Var.PLAY_EFFECTS) {
                                p.playEffect(all.getLocation(), Effect.CLOUD, 3);
                                p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 2.0F, 3.0F);
                                p.sendMessage(String.valueOf(Main.prefix) + ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("ServerLoading")));
                            }
                            return;
                        }
                        if (all.getSign().getLine(1).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Var.MAINTENANCE))) {
                            if (Var.PLAY_EFFECTS) {
                                p.playEffect(all.getLocation(), Effect.CLOUD, 3);
                                p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 2.0F, 3.0F);
                                String message = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("ServerMaintenance"));
                                message = message.replace("%group%", all.getGroup());
                                p.sendMessage(String.valueOf(Main.prefix) + message);
                            }
                            return;
                        }
                        System.out.println("STATUS: " + all.getSign().getLine(1) + " und abgefragt ist " + ChatColor.translateAlternateColorCodes('&', Var.MAINTENANCE) + " & " + ChatColor.translateAlternateColorCodes('&', Var.LOADING));
                        if (all.getSign().getLine(3).equalsIgnoreCase("")) {
                            if (p.hasPermission("signsystem.joinpremium")) {
                            Main.connect(p, all.getName());
                        } else {
                            p.sendMessage((new StringBuilder(String.valueOf(Main.prefix))).toString());
                        }
                        Main.connect(p, all.getSign().getLine(0).split(" ")[1]);
                    }
                }
            }
        }
    }
}

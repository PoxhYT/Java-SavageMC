package de.soup.events;

import de.soup.commands.SoupCommand;
import de.soup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class SoupListener implements Listener {

    public static ConcurrentHashMap<String, Integer[]> droppedItems = (ConcurrentHashMap)new ConcurrentHashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getPlayer() == null)
            return;
        if(event.getItem() != null)
            if (event.getItem().getType() == Material.MUSHROOM_SOUP)
                if(SoupCommand.isInTraining(player))
                    heal(event.getPlayer());

    }

    private void heal(Player player) {
        int health = (int) player.getHealth();
        if (health == 20)
            return;
        if(health > 12) {
            player.setHealth(20);
            player.getInventory().getItemInHand().setType(Material.BOWL);
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            return;
        }
        player.setHealth((health + 8));
        player.getInventory().getItemInHand().setType(Material.BOWL);
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent e) {
        ItemStack i = e.getItemDrop().getItemStack();
        if (i.getType() == Material.BOWL || (i.getType() == Material.MUSHROOM_SOUP && SoupCommand.isInTraining(e.getPlayer()))) {
            if (!droppedItems.containsKey(e.getPlayer().getName()))
                droppedItems.put(e.getPlayer().getName(), new Integer[] { Integer.valueOf(0), Integer.valueOf(0) });
            if (i.getType() == Material.BOWL) {
                Integer[] drops = droppedItems.get(e.getPlayer().getName());
                drops[0] = Integer.valueOf(drops[0].intValue() + 1);
                droppedItems.put(e.getPlayer().getName(), drops);
            }
            if (i.getType() == Material.MUSHROOM_SOUP) {
                Integer[] drops = droppedItems.get(e.getPlayer().getName());
                drops[1] = Integer.valueOf(drops[1].intValue() + 1);
                droppedItems.put(e.getPlayer().getName(), drops);
            }
            Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin) Main.getInstance(), new Runnable() {
                public void run() {
                    Item dr = e.getItemDrop();
                    dr.remove();
                }
            },  3L);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (SoupCommand.isInTraining(e.getPlayer())) {
            SoupCommand.killTask(e.getPlayer());
        }
    }
}



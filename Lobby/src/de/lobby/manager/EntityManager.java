package de.lobby.manager;

import com.rosemite.services.main.MainService;
import de.lobby.main.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EntityManager implements Listener {


    public void createVillager(Player player) {
        Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setCustomName("§eBelohnungen!");
        player.sendMessage(Main.prefix + "Du hast den §eReward-Villager §7erfolgreich gesetzt!");
        MainService.getService(null).getLobbyService().setVillager(true);
        freezeEntity(villager);
    }

    public void freezeEntity(Entity entity){
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) entity).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Villager reward = (Villager) event.getRightClicked();
        if(reward.getCustomName().equalsIgnoreCase("§eBelohnungen!")) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            Main.getInstance().inventoryManager.openRewardInventory(player);
        }
    }
}

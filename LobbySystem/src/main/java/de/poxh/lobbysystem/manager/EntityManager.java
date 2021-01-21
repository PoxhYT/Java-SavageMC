package de.poxh.lobby.manager;

import de.poxh.lobby.main.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityManager implements Listener {

    public void createVillager(Player player, String name) {
        Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setCustomName("§e" + name);
        player.sendMessage(Main.prefix + "Du hast den §e" + name + "-Villager §7erfolgreich gesetzt!");
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
    public void onDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }
}

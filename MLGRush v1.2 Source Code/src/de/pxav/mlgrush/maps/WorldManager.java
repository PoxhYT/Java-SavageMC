package de.pxav.mlgrush.maps;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class WorldManager {

    /**
     * Iterates all maps which are loaded by bukkit.
     * The current world will be prepared by the plugin
     * to grant that for example no mobs will spawn.
     */
    public void prepareMaps() {
        Bukkit.getWorlds().forEach(current -> {
            current.setTime(1000L);
            current.setPVP(true);
            current.setDifficulty(Difficulty.PEACEFUL);
            current.setGameRuleValue("doMobSpawning", "false");
            current.setGameRuleValue("doDaylightCycle", "false");

            if(current.isThundering())
                current.setThundering(false);

            this.clearEntities(current);

        });
    }

    /**
     * Clears all entities on a certain world.
     * @param world The world whose entities should be removed.
     */
    private void clearEntities(final World world) {
        world.getEntities().forEach(current -> {
            if(this.shouldClear(current))
                current.remove();
        });
    }

    private boolean shouldClear(final Entity entity) {
        return entity.getType() != EntityType.ARMOR_STAND
                || entity.getType() != EntityType.FISHING_HOOK
                || entity.getType() != EntityType.PAINTING
                || entity.getType() != EntityType.MINECART
                || entity.getType() != EntityType.MINECART_CHEST
                || entity.getType() != EntityType.MINECART_COMMAND
                || entity.getType() != EntityType.MINECART_FURNACE
                || entity.getType() != EntityType.MINECART_MOB_SPAWNER
                || entity.getType() != EntityType.MINECART_TNT
                || entity.getType() != EntityType.MINECART_HOPPER
                || entity.getType() != EntityType.ITEM_FRAME
                || entity.getType() != EntityType.BOAT;
    }

}

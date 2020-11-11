package de.cba.manager;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EffectLib {

    public static void playParticleEffect(Player player, double count){
        Location location1 = player.getLocation();
        location1.setY((player.getLocation().getY() + 0.2) + (count/40));
        int particles = 50;
        float radius = 0.7f;
        for (int i = 0; i < particles; i++) {
            double angle, x, z;
            angle = 2 * Math.PI * i / particles;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            location1.add(x, 0 , z);
            location1.getWorld().spigot().playEffect(location1, Effect.COLOURED_DUST, 0, 1, 0, 255, 0, 1, 0, 30);
            location1.subtract(x, 0, z);
        }
    }

}


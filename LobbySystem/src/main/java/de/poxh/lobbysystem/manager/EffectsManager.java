package de.poxh.lobby.manager;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class EffectsManager {

    public static void createFireWork(Player p){
        Firework firework1 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect1 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.STAR)
                .build();
        FireworkMeta meta1 = firework1.getFireworkMeta();
        meta1.addEffect(effect1);
        meta1.setPower(1);
        firework1.setFireworkMeta(meta1);

        Firework firework2 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect2 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.BALL)
                .build();
        FireworkMeta meta2 = firework2.getFireworkMeta();
        meta2.addEffect(effect2);
        meta2.setPower(1);
        firework2.setFireworkMeta(meta2);

        Firework firework3 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect3 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build();
        FireworkMeta meta3 = firework3.getFireworkMeta();
        meta3.addEffect(effect2);
        meta3.setPower(1);
        firework3.setFireworkMeta(meta3);

        Firework firework4 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect4 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.BURST)
                .build();
        FireworkMeta meta4 = firework4.getFireworkMeta();
        meta4.addEffect(effect4);
        meta4.setPower(1);
        firework4.setFireworkMeta(meta4);
    }
}

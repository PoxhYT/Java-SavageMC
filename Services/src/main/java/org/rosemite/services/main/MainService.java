package org.rosemite.services.main;

import org.bukkit.plugin.java.JavaPlugin;

public class MainService extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("Test");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

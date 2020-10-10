package com.rosemtie.services.config;

import org.bukkit.plugin.Plugin;

public class Config {
    private Plugin plugin;

    public Config(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public Object getConfiguration(String path)
    {
        return plugin.getConfig().get(path);
    }

    public void saveConfiguration(String path, Object value)
    {
        plugin.getConfig().addDefault(path, value);
        plugin.saveConfig();

        plugin.reloadConfig();
    }
}

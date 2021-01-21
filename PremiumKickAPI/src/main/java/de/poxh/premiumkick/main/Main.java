package de.poxh.premiumkick.main;

import de.poxh.premiumkick.listener.PremiumKick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public Main instance;
    public boolean premiumKick = true;

    public static String prefix = "§5§lSavageMC§7❘ §7» ";

    @Override
    public void onEnable() {
        init();

    }

    private void init() {
        instance = this;
        allowPremiumKick();
        Bukkit.getPluginManager().registerEvents(new PremiumKick(this), this);
    }

    public void allowPremiumKick(){
        premiumKick = true;
    }

    public void disallowPremiumKick(){
        premiumKick = false;
    }

    public Main getInstance() {
        return instance;
    }
}

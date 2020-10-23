package de.sw.gameManager;


import com.rosemite.services.helper.Log;
import de.sw.listener.KitListener;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Ingame_State extends Game_State {
    private KitListener kitListener;

    @Override
    public void start() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyWars");
        kitListener = ((Main)plugin).getKitListener();

        kitListener.kitMap.forEach((k, v) -> {
            Log.d(k);
            Log.d(v);
        });
    }

    @Override
    public void stop() { }
}

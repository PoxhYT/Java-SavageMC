package de.signsystem.events;

import de.gamestateapi.main.GameStateAPIManager;
import de.signsystem.api.ServerSign;
import de.signsystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SetupListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().hasPermission("signsystem.setup")) {
            if (Main.setup.equals(""))
                return;
            String[] setup = Main.setup.split(";");
            if (setup[0].equals(e.getPlayer().getName())) {
                Player p = e.getPlayer();
                e.setCancelled(true);
                Block b = e.getBlock();
                if (b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
                    p.sendMessage(String.valueOf(Main.prefix) + "hast erfolgreich ein ServerSign gesetzt!");
                    ServerSign ss = new ServerSign(b.getLocation(), Integer.parseInt(setup[3]), setup[2], setup[1], setup[4], false);
                    int id = ss.save().intValue();
                    ss.setID(id);
                    ss.setMaintenanceStatusAfter();
                    ss.setState(GameStateAPIManager.Offline);
                    ss.update();
                    Main.serversigns.add(ss);
                    p.sendMessage(String.valueOf(Main.prefix) + "hast ein neues Sign registriert. #" + Main.serversigns.size());
                    Bukkit.getConsoleSender().sendMessage(Main.prefix + + Main.serversigns.size() + "-" + setup[2] + ":" + Integer.parseInt(setup[3]) + "] wurde ins SignSystem registriert!");
                    Main.setup = "";
                } else {
                    p.sendMessage(String.valueOf(Main.prefix) + "musst ein Schild schlagen!");
                }
            }
        }
    }
}


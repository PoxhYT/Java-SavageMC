package de.sw.manager;

import com.nametagedit.plugin.NametagEdit;
import com.nametagedit.plugin.NametagManager;
import de.sw.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NameTagManager extends NametagEdit {

    public NametagManager manager = new NametagManager(this);

    public static NametagManager instance;

    public void setName(Player player) {
        for (int i = 0; i < Main.instance.teams.length; i++) {
            for (int j = 0; j < Main.instance.teams[i].getPlayers().size(); j++) {
                if(player.getUniqueId() == Main.instance.teams[i].getPlayers().get(j).getUniqueId()) {
                    manager.setNametag(player.getName(), Main.instance.teams[i].getTeamName(), "");
                }
            }
        }
    }
}



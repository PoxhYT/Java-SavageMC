package de.sw.commands;

import de.sw.main.Main;
import de.sw.manager.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCmd {
    public CommandSender sender;

    public String[] args;

    public String[] alias;

    public String cmdName;

    public int argLength = 0;

    public boolean forcePlayer = true;

    public Player player;

    public String type;

    public int maxArgs = -1;

    void processCmd(CommandSender s, String[] arg) {
        this.sender = s;
        this.args = arg;
        if (this.forcePlayer) {
            if (!(s instanceof Player)) {
                s.sendMessage(Main.prefix + "§cDu musst ein Spieler sein!");
                return;
            }
            this.player = (Player)s;
        }
        if ((this.maxArgs == -1 && this.argLength > arg.length) || (this.maxArgs != -1 && arg.length > this.maxArgs)) {
            s.sendMessage(Main.prefix + "§7/sw chestedit [chesttype] [chance]");
        } else {
            boolean returnVal = run();
            if (!returnVal)
                s.sendMessage(Main.prefix + "§7/sw chestedit [chesttype] [chance]");
        }
    }

    public String getType() {
        return this.type;
    }

    public abstract boolean run();
}

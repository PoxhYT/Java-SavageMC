package de.sw.commands;

import de.sw.main.Main;
import de.sw.manager.Util;
import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdManager implements CommandExecutor {
    private List<BaseCmd> admincmds = new ArrayList<>();

    private List<BaseCmd> pcmds = new ArrayList<>();

    private static CmdManager cm;

    public CmdManager() {
        cm = this;
        this.admincmds.add(new ChestEditCmd("skywars"));
    }

    public static List<BaseCmd> getCommands() {
        List<BaseCmd> a = cm.admincmds;
        a.addAll(cm.pcmds);
        return a;
    }

    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (args.length == 0 || getCommands(args[0]) == null) {
            sendHelp(this.admincmds, s, "1");
            sendHelp(this.pcmds, s, "2");
        } else {
            getCommands(args[0]).processCmd(s, args);
        }
        return true;
    }

    private void sendHelp(List<BaseCmd> cmds, CommandSender s, String num) {
        int count = 0;
        for (BaseCmd cmd : cmds) {
            if (Util.get().hp(cmd.getType(), s, cmd.cmdName)) {
                count++;
                if (count == 1) {
                    s.sendMessage(" ");
                }
            }
        }
    }

    private BaseCmd getCommands(String s) {
        BaseCmd cmd = getCmd(this.admincmds, s);
        if (cmd == null)
            cmd = getCmd(this.pcmds, s);
        return cmd;
    }

    private BaseCmd getCmd(List<BaseCmd> cmds, String s) {
        for (BaseCmd cmd : cmds) {
            if (cmd.cmdName.equalsIgnoreCase(s))
                return cmd;
            for (String alias : cmd.alias) {
                if (alias.equalsIgnoreCase(s))
                    return cmd;
            }
        }
        return null;
    }
}



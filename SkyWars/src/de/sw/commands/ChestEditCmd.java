package de.sw.commands;

import de.sw.enums.ChestType;
import de.sw.main.Main;
import de.sw.manager.ChestManager;
import de.sw.manager.Util;

public class ChestEditCmd extends BaseCmd {
    public ChestEditCmd(String t) {
        this.type = t;
        this.forcePlayer = true;
        this.cmdName = "chestedit";
        this.alias = new String[] { "ce" };
        this.argLength = 3;
    }

    public boolean run() {
        ChestType ct;
        int percent;
        String type = this.args[1];
        if (type.equalsIgnoreCase("basic")) {
            ct = ChestType.BASIC;
        } else if (type.equalsIgnoreCase("basiccenter")) {
            ct = ChestType.CENTER;
        } else {
            return false;
        }
        if (Util.get().isInteger(this.args[2])) {
            percent = Integer.valueOf(this.args[2]).intValue();
        } else {
            return false;
        }
        if (percent <= 0 || percent > 100) {
            return false;
        }
        Main.getChestManager().editChest(ct, percent, this.player);
        return true;
    }
}

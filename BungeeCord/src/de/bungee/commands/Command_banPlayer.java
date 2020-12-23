package de.bungee.commands;

import com.rosemite.services.main.MainService;
import com.rosemite.services.models.player.PlayerInfo;
import de.bungee.enums.BanReasons;
import de.bungee.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class Command_banPlayer extends Command {
    public Command_banPlayer(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(args.length == 2) {
                PlayerInfo target = MainService.getService(null).getPlayerService().getPlayerInfoByName(args[0]);
                BanReasons banReasons = BanReasons.valueOf(args[1]);

                if(target != null) {
                    if(args[1].equalsIgnoreCase(String.valueOf(banReasons))) {
                        switch (banReasons) {
                            case HACKING:
                                Main.banPlayer(target.getUuid(), 7, 1);
                                Main.sendMessage(player, "§7Du hast " + target.getPlayername() + " §7wegen §cHACKING §7für §e7Tage §7gebannt!");
                                BungeeCord.getInstance().getPlayer(UUID.fromString(target.getUuid())).disconnect();
                            break;
                        }
                    }
                } else {
                    Main.sendMessage(player, "§cDieser Spieler ist nicht in der Datenbank registriert!");
                }
            } else {
                Main.sendMessage(player, "§7Bitte benutze den §eBefehl§7: /§cbanPlayer §7<§eSPIELER§7> <§eGRUND§7>");
            }
        } else {
            sender.sendMessage(new TextComponent("§cNUR SPIELER"));
        }
    }
}

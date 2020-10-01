package de.mlgrush.commands;

import de.mlgrush.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_shop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent textComponent = new TextComponent("§7Besuche unseren §eShop§7: ");
            TextComponent clickme = new TextComponent("§8[§eKlicke hier§8]");
            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://savagemc.net"));
            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Klicke, um zum §eShop §7zu gelangen.")));
            textComponent.addExtra(clickme);

            player.spigot().sendMessage(textComponent);

        }

        return false;
    }
}

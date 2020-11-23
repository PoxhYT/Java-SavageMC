package src.de.cba.commands;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import src.de.cba.main.Main;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Command_WorldCreate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0) {
            player.sendMessage(Main.prefix + "Bitte benutze den Befehl /createwolrd <name>");
        } else if(args.length == 1) {

            //Creates a empty world.

            WorldCreator wc = new WorldCreator(args[0]);
            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.FLAT);
            wc.generatorSettings("0;0;0;");
            wc.createWorld();

            World world = player.getWorld();




            player.sendMessage(Main.prefix + "Du hast die Welt §e" + args[0] + " §7erfolgreich erstellt!");

            //Teleports the player to the current world.



        }
        return false;
    }

    private void createWorlds() {
        WorldCreator wc = new WorldCreator("TEST");

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.FLAT);
        wc.generatorSettings("0;0;0;");
        wc.createWorld();
    }
}

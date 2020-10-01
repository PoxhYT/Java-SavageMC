package de.lobby.inventories;

import de.lobby.utils.ItemAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LobbyInventory {

    public static void setTeamInventory(Player player) {
        player.getInventory().setItem(0, new ItemAPI(Material.COMPASS).setName("§8✘ §r§5Navigator §8✘").build());
        player.getInventory().setItem(1, new ItemAPI(Material.NETHER_STAR).setName("§8✘ §r§bLobbySwitcher §8✘").build());
        player.getInventory().setItem(3, new ItemAPI(Material.BARRIER).setName("§8✘ §r§cKein Gadget ausgewählt §8✘").build());
        player.getInventory().setItem(4, new ItemAPI(Material.CHEST).setName("§8✘ §r§eGadgets §8✘").build());
        player.getInventory().setItem(6, new ItemAPI(Material.TNT).setName("§8✘ §r§4SilentLobby §8✘").build());
        player.getInventory().setItem(7, new ItemAPI(Material.REDSTONE_COMPARATOR).setName("§8✘ §r§eEinstellungen §8✘").build());

    }
}

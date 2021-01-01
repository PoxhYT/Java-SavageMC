package de.poxh.lobby.listener;

import com.google.gson.Gson;
import com.rosemite.helper.Log;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.player.PlayerInfo;
import de.poxh.lobby.api.ItemBuilderAPI;
import de.poxh.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FriendListener implements Listener {

    private IService service;

    public FriendListener(IService service) {
        this.service = service;
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws ParseException {
        try {
            Player player = event.getPlayer();
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Freunde §8❘ §7Rechtsklick")) {
                openFriendsInv(player);
            }
        }catch (NullPointerException e){}
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            if(event.getInventory().getTitle().equalsIgnoreCase("§bFreunde")) {
                List<String> uuids = Main.service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString()).getValue().friends;
                for (int i = 0; i < Main.service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString()).getValue().friends.size(); i++) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§7" + getPlayerName(uuids.get(i)))) {
                        openFriendInfo(player, getPlayerName(uuids.get(i)));
                    }
                }
            }

        List<String> uuids = Main.service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString()).getValue().friends;
        for (int i = 0; i < Main.service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString()).getValue().friends.size(); i++) {
            if (event.getInventory().getTitle().equalsIgnoreCase("§7" + getPlayerName(uuids.get(i)))) {
                if(event.getCurrentItem().getType() == Material.BARRIER) {
                    Main.service.getFriendsService().removeFriend(player.getUniqueId().toString(), uuids.get(i));
                    player.sendMessage(Main.prefix + "§cDu hast die Freundschaft von dir und " + getPlayerName(uuids.get(i)) + " §caufgelöst!");
                    player.closeInventory();
                }
                if(event.getCurrentItem().getType() == Material.ENDER_PEARL) {
                    if(Main.service.getPlayerService().getPlayerInfo(uuids.get(i)).getIsOnline()) {
                        String serverName = Main.service.getPlayerService().getPlayerInfo(uuids.get(i)).getServerName();
                        if(serverName.equalsIgnoreCase(Main.service.getPlayerService().getPlayerInfo(player.getUniqueId().toString()).getServerName())) {
                            player.sendMessage(Main.prefix + "§7" + Main.service.getPlayerService().getPlayerInfo(uuids.get(i)).getPlayername() + " §7befindet sich in deine Lobby");
                        } else {
                            player.performCommand("connect " + serverName);
                            player.sendMessage(Main.prefix + "Du wurdest zu " + Main.service.getPlayerService().getPlayerInfo(uuids.get(i)).getPlayername() + " §7teleportiert!");
                        }
                    } else {
                        player.sendMessage(Main.prefix + "§7" + Main.service.getPlayerService().getPlayerInfo(uuids.get(i)).getPlayername() + " §cist aktuell nicht online");
                    }
                }
            }
        }
    }

    private static void openFriendInfo(Player player, String uuid) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§7" + uuid);
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§3 ").build());
        }
        inventory.setItem(13, new ItemBuilderAPI(Material.ENDER_PEARL).setDisplayName("§bFreund nachspringen")
                .setLore("§7➜ Ist aktuell auf " + "§eLobby-1 §aOnline").build());
        inventory.setItem(16, new ItemBuilderAPI(Material.BARRIER).setDisplayName("§cFreund entfernen").setLore("§7➥ §cLinksklick um " + uuid + " von deiner Liste zu entfernen").build());
        player.openInventory(inventory);
    }

    public void openFriendsInv(Player player) throws ParseException {
        Inventory inventory = Bukkit.createInventory(null, 54, "§bFreunde");
        if(Main.service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString()).getValue().friends.size() == 0) {
            inventory.setItem(0, new ItemBuilderAPI(Material.PAPER).setDisplayName("§7Füge §bFreunde §7zu deiner Liste hinzu!")
                    .setLore("§7➥ Füge §bFreunde §7hinzu, indem du den Befehl", "§7/§bfriend add <Spieler> §7ausführst.").build());
            inventory.setItem(49, new ItemBuilderAPI(Material.REDSTONE_COMPARATOR).setDisplayName("§eEinstellungen").build());
            inventory.setItem(53, new ItemBuilderAPI(Material.PAPER).setDisplayName("§7Nächste Seite").setLore("§7➥ §eLinksklick um zur nächsten Seite zu gelangen").build());
        } else{

            List<String> uuids = Main.service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString()).getValue().friends;
            List<PlayerInfo> playerInfos = Main.service.getPlayerService().mapUUIDsToPlayerInfo(uuids);
            playerInfos.sort(new Comparator<PlayerInfo>() {
                @Override
                public int compare(PlayerInfo playerInfo, PlayerInfo playerInfo2) {
//                    if (!playerInfo.getIsOnline() && playerInfo2.getIsOnline()) {
//                        return -1;
//                    } else if (playerInfo.getIsOnline() == playerInfo2.getIsOnline()) {
                    return (playerInfo.getIsOnline() == playerInfo2.getIsOnline())? 0 : playerInfo.getIsOnline()? -1 : 1;


                }
            });

            Log.d(new Gson().toJson(playerInfos));

            for (int i = 0; i < playerInfos.size(); i++) {
                if(playerInfos.get(i).getIsOnline()) {
                    inventory.setItem(i, getSkull(playerInfos.get(i).getPlayername(), "§aIst aktuell online", "§7➥ Ist aktuell §aonline §7auf §e" +
                            playerInfos.get(i).getServerName()));
                } else {
                    Date date = new Date();
                    Date lastSeen = new SimpleDateFormat("dd/MM/yyyy H:m").parse(playerInfos.get(i).getLastSeen());

                    inventory.addItem(new ItemBuilderAPI(Material.SKULL_ITEM).setDisplayName("§7" + (playerInfos.get(i).getPlayername()) +
                            " §7war vor " + getDifferenceInHours(lastSeen, date, player) + " §7zuletzt Online").setLore("§7• " + (playerInfos.get(i).getPlayername()) + "§7's §eStatus", "§7➥ §eAbonniert Poxh auf YouTube").build());

                }
            }
            inventory.setItem(49, new ItemBuilderAPI(Material.REDSTONE_COMPARATOR).setDisplayName("§eEinstellungen").build());
            player.openInventory(inventory);
        }
        player.openInventory(inventory);

    }

    private String getPlayerName(String uuid) {
        return service.getPlayerService().getPlayerInfo(uuid).getPlayername();
    }


    private static ItemStack getSkull(String name, String info, String... lore) {
        ItemStack skull = new ItemStack(397, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName("§7" + name + " §7" + info);
        skullMeta.setLore(Arrays.asList(lore));
        skullMeta.setOwner(name);
        skull.setItemMeta(skullMeta);
        return  skull;
    }

    public static String getDifferenceInHours(Date startDate, Date endDate, Player player){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        //long elapsedDays = different / daysInMilli;
        //different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if(elapsedHours == 0) {
            if(elapsedMinutes < 1) {
                return "weniger als 1 Minute";
            }
            return "§e" + elapsedMinutes + " Minuten";
        } else {
            return "§e" + elapsedHours + " §eStunden und " + elapsedMinutes + " Minuten";
        }
    }
}

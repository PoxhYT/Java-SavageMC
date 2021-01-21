package de.cb.poxh.manager.auction;

import de.cb.poxh.api.ItemBuilderAPI;
import de.cb.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuctionManager implements Listener{
    private Main instance;

    public AuctionManager(Main instance) {
    }

    private static ArrayList<PlayerAuctionManager> auctionManagerArrayList = new ArrayList<>();
    private static ArrayList<String> ids = new ArrayList<>();

    public ItemStack addItemToAuction(String seller, int days, Player player, int price) throws ParseException {
        setDate(days);
        int amount = player.getItemInHand().getAmount();
        player.sendMessage(Main.instance.prefix + "Du hast dein Item §b" + player.getInventory().getItemInMainHand().getType().name() +
                " §7zum §eAuktionshaus §7hinzugefügt.");
        seller = player.getDisplayName();

        Date date = new Date();
        Date endItemDate =new SimpleDateFormat("dd/MM/yyyy H:m").parse(setDate(days));
        List<String> itemLore = new ArrayList<>();

        itemLore.add("§cVerkaufer§7: " + seller);
        itemLore.add( getDifferenceInHours(date, endItemDate));
        itemLore.add("§ePreis§7: " + price + " §eCoins");
        itemLore.add(convertToInvisibleString(generateID()));

        ItemStack itemStack = new ItemStack(player.getInventory().getItemInMainHand());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(itemLore);
        itemStack.setItemMeta(meta);

        player.sendMessage(String.valueOf(itemStack.getItemMeta().getLore()));


        player.setItemInHand(null);

        auctionManagerArrayList.add(new PlayerAuctionManager(player.getName(), itemStack, price, player.getUniqueId().toString()));
        return itemStack;
    }

    public List<PlayerAuctionManager> getAuctionList() {
        return auctionManagerArrayList;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {

            Player player = (Player) event.getWhoClicked();
            for (int i = 0; i < auctionManagerArrayList.size(); i++) {

                String finalSeller = auctionManagerArrayList.get(i).getSeller();

                if(event.getView().getTitle().equalsIgnoreCase("§cAuctionHaus")) {
                    String[] lore = event.getCurrentItem().getItemMeta().getLore().toArray(new String[0]);
                    String ID = lore[lore.length - 1];

                    Material blackGlass = Material.BLACK_STAINED_GLASS_PANE;

                    List<String> itemIDList = auctionManagerArrayList.get(i).getItemSell().getItemMeta().getLore();
                    String itemID = itemIDList.get(itemIDList.size() - 1);
                    if (ID.equals(itemID)) {
                        if (event.getCurrentItem().getItemMeta().getLore().contains("§cVerkaufer§7: " + player.getName())) {
                            Inventory inventory = Bukkit.createInventory(null, 27, "§eItemVerwaltung");
                            for (int j = 0; j < inventory.getSize(); j++) {
                                inventory.setItem(j, new ItemBuilderAPI(blackGlass).setDisplayName("§1 ").build());
                            }
                            inventory.setItem(11, new ItemBuilderAPI(Material.REDSTONE).setDisplayName("§cAbbrechen").setLore("§7➥§eLinksklick um die §eItemVerwaltung §7zu beenden").build());
                            inventory.setItem(13, auctionManagerArrayList.get(i).getItemSell());
                            inventory.setItem(15, new ItemBuilderAPI(Material.EMERALD).setDisplayName("§aEntfernen").
                                    setLore("§7➥Linksklick um das §eItem §7zu entfernen§7.").build());
                            inventory.setItem(22, new ItemBuilderAPI(Material.PAPER).setDisplayName("§cInformationen").setLore("§7➥§eLinksklick §7um alle §cInformationen §7von Item zu bekommen.").build());
                            player.openInventory(inventory);
                        } else {
                            Inventory inventory = Bukkit.createInventory(null, 27, "§cVerkaufer§7: " + auctionManagerArrayList.get(i).getSeller());
                            for (int j = 0; j < inventory.getSize(); j++) {
                                inventory.setItem(j, new ItemBuilderAPI(blackGlass).setDisplayName("§1 ").build());
                            }
                            inventory.setItem(11, new ItemBuilderAPI(Material.REDSTONE).setDisplayName("§cAbbrechen").setLore("§7➥§eLinksklick um den Kauf zu beenden").build());
                            inventory.setItem(13, auctionManagerArrayList.get(i).getItemSell());
                            inventory.setItem(15, new ItemBuilderAPI(Material.EMERALD).setDisplayName("§aKaufen").
                                    setLore("§7➥Linksklick um das §eItem §7zu §akaufen§7.").build());
                            inventory.setItem(22, new ItemBuilderAPI(Material.PAPER).setDisplayName("§cInformationen").setLore("§7➥§eLinksklick §7um alle §cInformationen §7von Item zu bekommen.").build());
                            player.openInventory(inventory);
                            player.openInventory(inventory);
                        }
                        return;
                    }
                }
                if (event.getView().getTitle().equalsIgnoreCase("§eItemVerwaltung")) {

                    if(event.getCurrentItem().getType() == Material.PAPER) {
                        player.closeInventory();
                        player.sendMessage(Main.instance.prefix + "§cInformationen §7des Items");
                        player.sendMessage("§eUUID §7des Verkäufers: §e" + auctionManagerArrayList.get(i).getUUID());
                        player.sendMessage("§7Name des Items: §b" + auctionManagerArrayList.get(i).getItemSell().getType().name());
                        player.sendMessage("§7Preis des Items: §c" + auctionManagerArrayList.get(i).getPrice() + " Coins");
                    }


                    if(event.getCurrentItem().getType() == Material.EMERALD) {
                        String[] lore = event.getInventory().getItem(13).getItemMeta().getLore().toArray(new String[0]);
                        String ID = lore[lore.length - 1];
                        List<String> itemIDList = auctionManagerArrayList.get(i).getItemSell().getItemMeta().getLore();
                        String itemID = itemIDList.get(itemIDList.size() - 1);
                        if (ID.equals(itemID)) {
                            ItemStack itemStack = auctionManagerArrayList.get(i).getItemSell();

                            ItemMeta meta = itemStack.getItemMeta();
                            meta.setLore(null);
                            itemStack.setItemMeta(meta);

                            player.getInventory().addItem(itemStack);

                            auctionManagerArrayList.remove(i);
                            player.closeInventory();
                        }
                    }

                    if(event.getCurrentItem().getType() == Material.REDSTONE) {
                        openInventory(player);
                    }
                }

                if(event.getView().getTitle().equalsIgnoreCase("§cVerkaufer§7: " + auctionManagerArrayList.get(i).getSeller())) {
                    if (event.getCurrentItem().getType() == Material.EMERALD) {
                        String[] lore = event.getInventory().getItem(13).getItemMeta().getLore().toArray(new String[0]);
                        String ID = lore[lore.length - 1];
                        List<String> itemIDList = auctionManagerArrayList.get(i).getItemSell().getItemMeta().getLore();
                        String itemID = itemIDList.get(itemIDList.size() - 1);

                        ItemStack itemStack = auctionManagerArrayList.get(i).getItemSell();
                        int itemPrice = auctionManagerArrayList.get(i).getPrice();
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(itemPrice));
                        int buyerAmount = Main.instance.service.getCityBuildService().getCoinAmount(player.getUniqueId().toString());

                        if (ID.equals(itemID)) {

                            Player seller = Bukkit.getPlayer(auctionManagerArrayList.get(i).getUUID());

                            if(buyerAmount >= itemPrice) {
                                Map.Entry<Integer, Boolean> result = Main.instance.service.getCityBuildService().removeCoins(player.getUniqueId().toString(), itemPrice);

                                Main.instance.service.getCityBuildService().addCoins(auctionManagerArrayList.get(i).getUUID(), itemPrice);
                                List<String> lorefinal = Collections.singletonList("");
                                itemStack.getItemMeta().setLore(lorefinal);
                                player.getInventory().addItem(itemStack);
                                if(seller != null) {
                                    seller.sendMessage(Main.instance.prefix + "Hat §b" + itemStack.getType().name() + " §7für §e" + itemPrice + " Coins §7abgekauft.");
                                }
                                player.sendMessage(Main.instance.prefix + "Du hast von " + auctionManagerArrayList.get(i).getSeller() + " §b" + itemStack.getType().name() + " §7für §e" + itemPrice + " Coins §7gekauft.");
                                player.closeInventory();
                                auctionManagerArrayList.remove(i);
                            } else {
                                int coinsNeeded = itemPrice - buyerAmount;
                                player.sendMessage(Main.instance.prefix + "§7Dir fehlen §e" + coinsNeeded + " §coins §7um §b" + itemStack.getType().name() + " §7zu kaufen.");
                                player.closeInventory();
                                List<String> lorefinal = Collections.singletonList("");
                                itemStack.getItemMeta().setLore(lorefinal);
                                player.getInventory().addItem(itemStack);
                            }
                        } else {
                            player.sendMessage("4");
                        }
                    }
                    player.closeInventory();

                    if(event.getCurrentItem().getType() == Material.PAPER) {
                        player.closeInventory();
                        player.sendMessage(Main.instance.prefix + "§cInformationen §7des Items");
                        player.sendMessage("§eUUID §7des Verkäufers: §e" + auctionManagerArrayList.get(i).getUUID());
                        player.sendMessage("§7Name des Items: §b" + auctionManagerArrayList.get(i).getItemSell().getType().name());
                        player.sendMessage("§7Preis des Items: §c" + auctionManagerArrayList.get(i).getPrice() + " Coins");
                    }
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }

    public String generateID() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        if(ids.contains(generatedString)) {
            return generateID();
        }
        ids.add(generatedString);
        return generatedString;
    }

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "§cAuctionHaus");
        for (int i = 0; i < auctionManagerArrayList.size(); i++) {
            inventory.addItem(auctionManagerArrayList.get(i).getItemSell());
        }

        player.openInventory(inventory);
    }

    private static String setDate(int amount) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_WEEK, amount);
        String pattern = "dd/MM/yyyy H:m";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateAsString = df.format(c.getTime());
        return dateAsString;
    }

    public static String getDifferenceInHours(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if(elapsedDays < 0) {
            if(elapsedHours < 1) {
                if(elapsedMinutes < 1) {
                    return "weniger als 1 Minute";
                } else {
                    return "§e" + elapsedMinutes + " Minuten";
                }
            } else {
                return "§e" + elapsedHours + " §eStunden und " + elapsedMinutes + " Minuten";
            }
        } else {
            return "§e" + elapsedDays + " Tage " + elapsedHours + " §eStunden und " + elapsedMinutes + " Minuten";
        }
    }
}

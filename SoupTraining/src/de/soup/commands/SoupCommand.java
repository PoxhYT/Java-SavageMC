package de.soup.commands;

import com.google.common.collect.Maps;
import de.magnus.coinsapi.util.CoinsAPI;
import de.services.main.MainService;
import de.soup.events.SoupListener;
import de.soup.main.Main;
import de.soup.storage.Item;
import de.soup.storage.SpeedType;
import de.soup.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SoupCommand implements CommandExecutor {

    private static CopyOnWriteArrayList<Player> inTraining = new CopyOnWriteArrayList<>();

    private static ConcurrentHashMap<String, Integer> task = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, SpeedType> speedType = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, ItemStack[]> oldInventory = (ConcurrentHashMap)new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Integer> time = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Integer> timing = new ConcurrentHashMap<>();

    private static Map<UUID, Timer> timers = Maps.newHashMap();

    private static int number;
    
    private final String noob = "§7Der §eSchwierigkeitsgrad §7wurde auf §eNoob §7angepasst!";
    private final String leicht = "§7Der §eSchwierigkeitsgrad §7wurde auf §eLeicht §7angepasst!";
    private final String normal = "§7Der §eSchwierigkeitsgrad §7wurde auf §eNormal §7angepasst!";
    private final String hart = "§7Der §eSchwierigkeitsgrad §7wurde auf §eHart §7angepasst!";
    private final String legende = "§7Der §eSchwierigkeitsgrad §7wurde auf §eLegende §7angepasst!";

    private MainService service;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        System.out.println(servicePlugin);
        if (servicePlugin != null) {
            service = (MainService)servicePlugin;
        }

        if(command.getName().equals("soup"))
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 0) {
                    player.sendMessage(Main.prefix + "§e/soup start");
                    player.sendMessage(Main.prefix + "§e/soup stop");
                    player.sendMessage(Main.prefix + "§e/soup speed");
                    return true;
                }
                if (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("speed")) {
                    int i;
                    switch (args[0].toLowerCase()) {
                        case "start":
                            if (isInTraining(player)) {
                                player.sendMessage(Main.prefix + "Dein §eTraining §7läuft bereits...");
                                return true;
                            }
                            player.setGameMode(GameMode.SURVIVAL);
                            this.oldInventory.put(player.getName(), player.getInventory().getContents());
                            inTraining.add(player);
                            player.sendMessage(Main.prefix + "Dein §eTraining §7wurde gestartet!");
                            player.performCommand("timer start");
                            player.getInventory().clear();
                            for (i = 0; i < 35; i++)
                                player.getInventory().setItem(i, Item.getItem());
                            startTask(player);

                            break;
                        case "stop":
                            if (!isInTraining(player)) {
                                player.sendMessage(Main.prefix + "§cDu trainierst nicht!");
                                return true;
                            }
                            player.setHealth(20.0D);
                            player.setFoodLevel(20);
                            player.getInventory().clear();
                            Bukkit.getScheduler().cancelTask(((Integer)task.get(player.getName())).intValue());
                            player.sendMessage(Main.prefix + "§cDu hast das Training beendet!");

                            Random random = new Random();
                            for (int counter=1; counter<=100;counter++) {
                                number = random.nextInt(950);
                            }

                            if (!SoupListener.droppedItems.containsKey(player.getName()))
                                SoupListener.droppedItems.put(player.getName(), new Integer[] { Integer.valueOf(0), Integer.valueOf(0) });
                            player.sendMessage(Main.prefix + "§7§m-------------- §c§lStatistiken §7§m--------------");
                            player.sendMessage(Main.prefix + "§eSchaden: "+ this.time.get(player.getName()) + " §eMal");
                            player.sendMessage(Main.prefix + "§eGedroppte Suppen: " + ((Integer[])SoupListener.droppedItems.get(player.getName()))[1]);
                            player.sendMessage(Main.prefix + "§eGedroppte Schüsseln: " + ((Integer[])SoupListener.droppedItems.get(player.getName()))[0]);
                            player.performCommand("timer stop");
                            player.sendMessage(Main.prefix + number + " §eCoins");
                            CoinsAPI.addCoins(player.getUniqueId().toString(), number);
                            player.sendMessage(Main.prefix + "§7§m-------------- §c§lStatistiken §7§m--------------");
                            inTraining.remove(player);
                            player.getInventory().setContents(this.oldInventory.get(player.getName()));
                            player.updateInventory();
                            SoupListener.droppedItems.remove(player.getName());
                            break;
                        case "speed":
                            if (!isInTraining(player)) {
                                processSpeedType(player);
                                return true;
                            }
                            player.sendMessage(Main.prefix + "§cWährend des Trainings kannst du die Schnelligkeit nicht anpassen!");
                            break;
                    }
                } else {
                    player.sendMessage(Main.prefix + "§e/soup start");
                    player.sendMessage(Main.prefix + "§e/soup stop");
                    return true;
                }
            } else {
                sender.sendMessage(Main.prefix + "§cDu musst ein Spieler sein, damit du diesen Command ausfdarfst.");
            }
        return false;
    }

    public static boolean isInTraining(Player p) {
        return inTraining.contains(p);
    }

    private void startTask(final Player p) {
        getSpeed(p);
        this.time.put(p.getName(), 0);
        task.put(p.getName(), Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin) Main.getInstance(), new Runnable() {
            public void run() {
                if (SoupCommand.this.playerCanSurvive(p)) {
                    p.damage(8.0D);
                    SoupCommand.this.time.put(p.getName(), (Integer) SoupCommand.this.time.get(p.getName()) + 1);
                } else {
                    p.performCommand("soup stop");
                }
            }
        }, ((Integer) this.timing.get(p.getName())), (Integer) this.timing.get(p.getName())));
    }

    private boolean playerCanSurvive(Player p) {
        return ((int)p.getHealth() > 8);
    }

    private void processSpeedType(Player p) {
        if (this.speedType.containsKey(p.getName())) {
            final SpeedType type = this.speedType.get(p.getName());
            switch (type) {
                case NOOB:
                    this.speedType.put(p.getName(), SpeedType.SLOW);
                    p.sendMessage(Main.prefix + leicht);
                    break;
                case SLOW:
                    this.speedType.put(p.getName(), SpeedType.NORMAL);
                    p.sendMessage(Main.prefix + normal);
                    break;
                case NORMAL:
                    this.speedType.put(p.getName(), SpeedType.HARD);
                    p.sendMessage(Main.prefix + hart);
                    break;
                case HARD:
                    this.speedType.put(p.getName(), SpeedType.LEGEND);
                    p.sendMessage(Main.prefix + legende);
                    break;
                case LEGEND:
                    this.speedType.put(p.getName(), SpeedType.NOOB);
                    p.sendMessage(Main.prefix + noob);
                    break;
            }
        } else {
            this.speedType.put(p.getName(), SpeedType.NOOB);
            p.sendMessage(Main.prefix + noob);
        }
    }

    private int getSpeed(Player p) {
        if (!this.speedType.containsKey(p.getName())) {
            this.speedType.put(p.getName(), SpeedType.NOOB);
            this.timing.put(p.getName(), 25);
            p.sendMessage(Main.prefix + "§7Du startest mit der §eNoobgeschwindigkeit§7.");
            return 25;
        }
        switch ((SpeedType)this.speedType.get(p.getName())) {
            case NOOB:
                this.speedType.put(p.getName(), SpeedType.NOOB);
                this.timing.put(p.getName(), 20);
                p.sendMessage(Main.prefix + "§7Du startest mit der §eNoobgeschwindigkeit§7.");
                return 25;
            case SLOW:
                this.speedType.put(p.getName(), SpeedType.SLOW);
                p.sendMessage(Main.prefix + "§7Du startest mit der §eLangsamengeschwindigkeit§7.");
                this.timing.put(p.getName(), 15);
                return 20;
            case NORMAL:
                this.speedType.put(p.getName(), SpeedType.NORMAL);
                p.sendMessage(Main.prefix + "§7Du startest mit der §eNormalengeschwindigkeit§7.");
                this.timing.put(p.getName(), 10);
                return 15;
            case HARD:
                this.speedType.put(p.getName(), SpeedType.HARD);
                p.sendMessage(Main.prefix + "§7Du startest mit der §eHartengeschwindigkeit§7.");
                this.timing.put(p.getName(), 5);
                return 12;
            case LEGEND:
                this.speedType.put(p.getName(), SpeedType.LEGEND);
                p.sendMessage(Main.prefix + "§7Du startest mit der §eLegendengeschwindigkeit§7.");
                this.timing.put(p.getName(), 2);
                return 10;
        }
        this.timing.put(p.getName(), 25);
        return 25;
    }

    public static void killTask(Player p) {
        Bukkit.getScheduler().cancelTask((Integer) task.get(p.getName()));
    }
}

package de.mlgrush.gamestates;

import de.mlgrush.enums.GameState;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyCountdown {

    private static int taskID;
    private static int countDown;

    public void start(boolean resetCountdown, boolean delayed) {

        long startDelay = 0L;
        long period = 20L;
        for (Player current : Bukkit.getOnlinePlayers()) {
            if (!Main.getInstance().getTeamHandler().playing.contains(current))
                Main.getInstance().getTeamHandler().playing.add(current);
        }

        if (resetCountdown)
            countDown = 11;
        if (delayed)
            startDelay = 20L;

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            countDown--;

            Bukkit.getOnlinePlayers().forEach(current -> {
                final PlayerManager currentManager = new PlayerManager(current);
                currentManager.sendActionBar(Main.getInstance().getPrefix() + "§7Das Spiel startet in §a" + countDown + " §7Sekunden.");
            });

            switch (countDown) {
                case 10:
                case 5:
                case 3:
                case 2:
                    Bukkit.broadcastMessage(Main.getInstance().getPrefix() + "§7Das Spiel startet in §a" + countDown + " §7Sekunden");
                    Bukkit.getOnlinePlayers().forEach(current -> {
                        final PlayerManager currentManager = new PlayerManager(current);
                        currentManager.sendTitle("§3§l" + countDown, "§7Sekunden bist zum Spielstart");
                        current.playSound(current.getLocation(), Sound.ORB_PICKUP, 3, 1);
                    });
                    break;
                case 1:
                    Bukkit.broadcastMessage(Main.getInstance().getPrefix() + "§7Das Spiel startet in §aeiner §7Sekunde");
                    Bukkit.getOnlinePlayers().forEach(current -> {
                        final PlayerManager currentManager = new PlayerManager(current);
                        currentManager.sendTitle("§3§l" + countDown, "§7Sekunde bis zum Spielstart");
                        current.playSound(current.getLocation(), Sound.ORB_PICKUP, 3, 1);
                    });
                    break;
                case 0:
                    Main.getInstance().getTeamHandler().handleTeams();
                    GameStateHandler.setGameState(GameState.INGAME);
                    Bukkit.broadcastMessage(Main.getInstance().getPrefix() + "§7Das Spiel startet §ajetzt.");

                    Main.getInstance().getTeamHandler().fixTeams();
                    for (Player current : Main.getInstance().getTeamHandler().playing) {
                        final PlayerManager playerManager = new PlayerManager(current);
                        playerManager.resetPlayer();
                        playerManager.teleportToTeamIsland();
                        Main.getInstance().getInventoryManager().giveIngameItems(current);
                        Main.getInstance().getScoreboardHandler().setScoreboard(current);
                        Main.getInstance().getTabListHandler().setTabList(current);
                        current.sendMessage(Main.getInstance().getPrefix() + "§8§m------------------------");
                        current.sendMessage(Main.getInstance().getPrefix() + "§7Informationen über die aktuelle Karte§8:");
                        current.sendMessage(Main.getInstance().getPrefix() + "§7Name §8➜ §a" + Main.getInstance().getConfigManager().getMapName());
                        current.sendMessage(Main.getInstance().getPrefix() + "§7Gebaut von §8➜ §a" + Main.getInstance().getConfigManager().getMapBuilder());
                        current.sendMessage(Main.getInstance().getPrefix() + "");
                        current.sendMessage(Main.getInstance().getPrefix() + "§7Wir wünschen viel Spaß beim Spielen!");
                        current.sendMessage(Main.getInstance().getPrefix() + "§8§m------------------------");
                    }

                    GameStateHandler.setAllowMove(false);
                    Main.getInstance().getPlayerMoveScheduler().startListening();
                    Main.getInstance().getStartCountdown().start();
                    Main.getInstance().getPointsHandler().sendPointsActionBar();

                    this.stop();
                    break;
            }

        }, startDelay, period);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}


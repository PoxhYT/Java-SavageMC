package de.signsystem.api;

import de.gamestateapi.main.GameStateAPIManager;
import de.signsystem.main.Main;
import de.signsystem.main.Var;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class SignView {
    private static int count = 0;

    private static String[] loading = new String[] { "O o o o o", "o O o o o", "o o O o o", "o o o O o", "o o o o O", "o o o O o", "o o O o o", "o O o o o" };

    public static void startUpdater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Main.getInstance(), new Runnable() {
            public void run() {
                for (ServerSign signs : Main.serversigns) {
                    signs.update();
                    if (signs.getState().equals(GameStateAPIManager.Offline)) {
                        Main.clearSign(signs.getSign());
                        signs.getSign().setLine(1, Var.LOADING);
                        signs.getSign().setLine(2, SignView.loading[SignView.count]);
                    } else if (signs.getState().equals(GameStateAPIManager.LOBBY)) {
                        Main.clearSign(signs.getSign());
                        signs.getSign().setLine(0, "- " + signs.getName() + " -");
                        signs.getSign().setLine(1, signs.getStatusText().split(";")[0]);
                        signs.getSign().setLine(2, signs.getStatusText().split(";")[1]);
                        try {
                            signs.getSign().setLine(3, signs.getStatusText().split(";")[2].split(" ")[0]);
                        } catch (IndexOutOfBoundsException e) {
                            signs.getSign().setLine(3, "");
                        }
                    } else if (signs.getState().equals(GameStateAPIManager.Maintenance)) {
                        Main.clearSign(signs.getSign());
                        signs.getSign().setLine(1, Var.MAINTENANCE);
                        signs.getSign().setLine(2, SignView.loading[SignView.count]);
                    } else if (signs.getState().equals(GameStateAPIManager.INGAME)) {
                        if (Var.HIDEINGAMESERVERS) {
                            Main.clearSign(signs.getSign());
                            signs.getSign().setLine(1, Var.LOADING);
                            signs.getSign().setLine(2, SignView.loading[SignView.count]);
                        } else {
                            Main.clearSign(signs.getSign());
                            signs.getSign().setLine(0, "- " + signs.getName() + " -");
                            signs.getSign().setLine(1, Var.INGAME);
                            signs.getSign().setLine(2, signs.getStatusText().split(";")[1]);
                            signs.getSign().setLine(3, signs.getStatusText().split(";")[2].split(" ")[0]);
                        }
                    }
                    signs.getSign().update();
                }
                SignView.count = SignView.count + 1;
                if (SignView.count >= SignView.loading.length)
                    SignView.count = 0;
            }
        }, 0L, 11L);
    }
}


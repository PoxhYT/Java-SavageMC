package de.sw.countdown;

import org.bukkit.entity.Player;

public abstract class Countdown {

    protected int taskID;

    public abstract void start();
    public abstract void stop();
}

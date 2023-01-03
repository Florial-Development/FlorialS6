package me.florial.utils;

import me.florial.Florial;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneralUtils {

    public static void runAsync(BukkitRunnable code) {
        code.runTaskAsynchronously(Florial.getInstance());
    }
}

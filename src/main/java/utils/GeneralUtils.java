package utils;

import core.Florial;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneralUtils {

    public static void runAsync(BukkitRunnable code) {
        code.runTaskAsynchronously(Florial.getInstance());
    }
}

package net.florial.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Cooldown {
    public static HashMap<String, HashMap<UUID, Long>> cooldown = new HashMap();

    public Cooldown() {
    }

    public static void createCooldown(String k) {
        if (cooldown.containsKey(k)) {
            throw new IllegalArgumentException("Cooldown already exists.");
        } else {
            cooldown.put(k, new HashMap());
        }
    }

    public static HashMap<UUID, Long> getCooldownMap(String k) {
        return cooldown.containsKey(k) ? (HashMap)cooldown.get(k) : null;
    }

    public static void addCooldown(String k, Player p, int seconds) {
        if (!cooldown.containsKey(k)) {
            throw new IllegalArgumentException(k + " does not exist");
        } else {
            long next = System.currentTimeMillis() + (long)seconds * 1000L;
            ((HashMap)cooldown.get(k)).put(p.getUniqueId(), next);
        }
    }

    public static boolean isOnCooldown(String k, Player p) {
        return cooldown.containsKey(k) && ((HashMap)cooldown.get(k)).containsKey(p.getUniqueId()) && System.currentTimeMillis() <= (Long)((HashMap)cooldown.get(k)).get(p.getUniqueId());
    }

    public static boolean isOnCooldown(String k, UUID uuid) {
        return cooldown.containsKey(k) && ((HashMap)cooldown.get(k)).containsKey(uuid) && System.currentTimeMillis() <= (Long)((HashMap)cooldown.get(k)).get(uuid);
    }

    public static int getCooldownForPlayerInt(String k, Player p) {
        return (int)((Long)((HashMap)cooldown.get(k)).get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
    }

    public static long getCooldownForPlayerLong(String k, Player p) {
        return (Long)((HashMap)cooldown.get(k)).get(p.getUniqueId()) - System.currentTimeMillis();
    }

    public static void removeCooldown(String k, Player p) {
        if (!cooldown.containsKey(k)) {
            throw new IllegalArgumentException(k + " does not exist");
        } else {
            ((HashMap)cooldown.get(k)).remove(p.getUniqueId());
        }
    }

    public static void removeCooldown(String k, UUID uuid) {
        if (!cooldown.containsKey(k)) {
            throw new IllegalArgumentException(k + " does not exist");
        } else {
            ((HashMap)cooldown.get(k)).remove(uuid);
        }
    }
}

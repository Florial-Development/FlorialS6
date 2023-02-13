package net.florial.species.disguises;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Morph {

    /**
    positions:
    sitting
    sleeping
    example for setting species: disguise(p, "fox", "", false);
    example for modifying position: disguise(p, "", "sitting", true);
    example for taking them out of sitting/sleeping: disguise(p, "", "sitting", false);

     */
    public static void activate(Player p, String type, String position, Boolean state) {
        if (!position.isBlank()) {
            Bukkit.dispatchCommand(p, "modifydisguise set" + position + " " + state);
            return;
        }
        Bukkit.dispatchCommand(p, "disguise " + type);

    }
}

package net.florial.species.impl;

import net.florial.Florial;
import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Fox extends Species {
    
    public Fox(int id) {
        super("Fox", id, 14);

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());

    }


    @Override
    public Set<PotionEffect> effects() {

        return new HashSet<>(List.of(
                new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 1, false, false, true)));
    }

    @Override
    public Set<String> descriptions() {
        return new HashSet<>(Arrays.asList(
                "", ""));
    }
    
}

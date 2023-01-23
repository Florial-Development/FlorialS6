package net.florial.species.impl;

import net.florial.Florial;
import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Fox extends Species {
    
    public Fox(int id) {
        super("Fox", id);

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());

    }


    @Override
    public Set<PotionEffect> effects() {
        Set<PotionEffect> effects = new HashSet<>(Arrays.asList(
                new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 1, false, false, true)));

        return effects;
    }

    @Override
    public Set<String> descriptions() {
        Set<String> descriptions = new HashSet<>(Arrays.asList(
                ""));

        return descriptions;
    }
    
}

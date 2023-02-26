package net.florial.species.impl;

import net.florial.Florial;
import net.florial.species.Species;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fox extends Species {
    
    public Fox(int id) {
        super("Fox", id, 14, true);

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

    @Override
    public Set<Material> diet() {
        return new HashSet<>(Arrays.asList(
                Material.BEEF, Material.PORKCHOP,
                Material.CHICKEN, Material.MUTTON,
                Material.SWEET_BERRIES, Material.COD,
                Material.SALMON, Material.GLOW_BERRIES));
    }
    
}

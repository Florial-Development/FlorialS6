package net.florial.species.impl;

import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.species.Species;
import net.florial.utils.Cooldown;
import net.florial.utils.NumberGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Fox extends Species {
    
    public Fox(int id) {
        super("Fox", id, 16, true);

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());

    }

    @Override
    public HashMap<Integer, PotionEffect> specific() {

        return new HashMap<>(Map.ofEntries(
                Map.entry(1, new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 0, false, false, true)),
                Map.entry(2, new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 1, false, false, true)),
                Map.entry(3, new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 2, false, false, true))));
    }

    @Override
    public Set<PotionEffect> effects() {

        return new HashSet<>(List.of(
                new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false, true),
                new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1, false, false, true)));
    }

    @Override
    public Set<String> descriptions() {
        return new HashSet<>(Arrays.asList(
                "BURROWING", "Haste, double materials, etc"));
    }

    @Override
    public Set<Material> diet() {
        return new HashSet<>(Arrays.asList(
                Material.BEEF, Material.PORKCHOP,
                Material.CHICKEN, Material.MUTTON,
                Material.SWEET_BERRIES, Material.COD,
                Material.SALMON, Material.GLOW_BERRIES));
    }

    @EventHandler
    public void foxBite(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player p) || (!(e.getEntity() instanceof LivingEntity))) return;

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        if (data.getSpecies() != this || (Cooldown.isOnCooldown("c1", p))) return;

        ((LivingEntity) e.getEntity()).damage(NumberGenerator.generate(10, 15), p);

        p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, (float) 0.5);

        Cooldown.addCooldown("c1", p, 6);

    }

    @EventHandler
    public void foxHeatAcclimation(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player p) || e.getCause() != EntityDamageEvent.DamageCause.LAVA && e.getCause() != EntityDamageEvent.DamageCause.FIRE) return;
        
        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        if (data.getSpecies() != this || (Cooldown.isOnCooldown("c2", p))) return;

        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1, false, false, true));

        Cooldown.addCooldown("c2", p, 30);

    }
    
}

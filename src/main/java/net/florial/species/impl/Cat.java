package net.florial.species.impl;

import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.species.Species;
import net.florial.utils.Cooldown;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class Cat extends Species {
    
    public Cat(int id) {
        super("Cat", id, 14, true);
    }


    @Override
    public HashMap<Integer, Boolean> sharedAbilities() {

        return new HashMap<>(Map.ofEntries(
                Map.entry(1, true)));
    }
    @Override
    public HashMap<Integer, PotionEffect> specific() {

        return new HashMap<>(Map.ofEntries(
                Map.entry(1, new PotionEffect(PotionEffectType.SPEED, 1, 0, false, false, true))));
    }

    @Override
    public Set<PotionEffect> effects() {

        return new HashSet<>(List.of(
                new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false, true),
                new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1, false, false, true)));
    }

    @Override
    public Set<String> descriptions() {

        return new HashSet<>(Arrays.asList(
                "UNKNOWN", "still being decided"));
    }

    @Override
    public Set<Material> diet() {
        return new HashSet<>(Arrays.asList(
                Material.BEEF, Material.PORKCHOP,
                Material.CHICKEN, Material.MUTTON,
                Material.TROPICAL_FISH, Material.COD,
                Material.SALMON));
    }


    @EventHandler
    public void makePurr(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (!(p.isSneaking()) || Florial.getPlayerData().get(p.getUniqueId()).getSpecies() != this || (Cooldown.isOnCooldown("c2", p))) return;

        for (Entity ent : p.getNearbyEntities(7, 7, 7)) {
            if (!(e instanceof LivingEntity)) return;
            ((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 1, false, false, true));
        }

        Cooldown.addCooldown("c1", p, 200);


    }

    @EventHandler
    public void nineLives(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        if (data.getSpecies() != this || (Cooldown.isOnCooldown("c1", p))) return;

        if (p.getHealth() < 2) p.setHealth(p.getHealth() + 7);

        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, (float) 0.4, 1);
        p.playSound(p.getLocation(), Sound.ENTITY_CAT_BEG_FOR_FOOD, 1, 1);

        Cooldown.addCooldown("c1", p, 300);


    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {

            PlayerData data = Florial.getPlayerData().get(attacker.getUniqueId());

            if (data.getSpecies() != this) return;

            if (attacker.getInventory().getItemInMainHand().getType() == Material.AIR) {

                Location particleLoc = attacker.getLocation().clone()
                    .add(0.0, 1.0, 0.0)
                    .add(attacker.getLocation().getDirection().clone().normalize().multiply(0.75));

                attacker.spawnParticle(Particle.SWEEP_ATTACK, particleLoc, 2);

                for (Entity e : attacker.getNearbyEntities(3, 3, 3)) {
                    if (!(e instanceof LivingEntity)) return;
                    Vector launchDirection = e.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(1.2);
                    launchDirection.setY(0.5);
                    e.setVelocity(launchDirection);
                    ((LivingEntity) e).damage(6D);
                }
            }
        }
    }
}

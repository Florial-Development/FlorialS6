package net.florial.species.impl;

import net.florial.species.SpecieType;
import net.florial.species.Species;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cat extends Species {
    
    public Cat(int id) {
        super("Cat", id, 12, true);
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A cat has respawned!");
    }

    @Override
    public Set<String> descriptions() {

        return new HashSet<>(Arrays.asList(
                "", ""));
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {
            if (attacker.getInventory().getItemInMainHand().getType() == Material.AIR) {

                Bukkit.broadcast(Component.text("Called"));

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

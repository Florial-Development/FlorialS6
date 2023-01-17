package net.florial.listeners;

import net.florial.features.enemies.events.MobDeathEvent;
import net.florial.features.enemies.events.MobSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobsListener implements Listener {

    @EventHandler
    private void CustomMobSpawn(CreatureSpawnEvent e) {
        Entity ent = e.getEntity();

        if (!(ent instanceof Zombie) && (!(ent instanceof Spider) && (!(ent instanceof Skeleton) && (!(ent instanceof Creeper)))) || ent instanceof CaveSpider) return;

        e.setCancelled(true);

        MobSpawnEvent eve = new MobSpawnEvent(
                ent,
                e.getEntity().getWorld(),
                e.getLocation()
        );

        Bukkit.getPluginManager().callEvent(eve);
    }

    @EventHandler
    private void CustomMobDeath(EntityDeathEvent e) {
        Entity ent = e.getEntity();

        if (!(ent instanceof Hoglin) && (!(ent instanceof CaveSpider) && (!(ent instanceof Witch)) && (!(ent instanceof Ravager)))) return;

        MobDeathEvent eve = new MobDeathEvent(
                ent.getType(),
                e.getDrops()
        );

        Bukkit.getPluginManager().callEvent(eve);
    }
}

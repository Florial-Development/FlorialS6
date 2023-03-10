package net.florial.features.enemies.impl;

import net.florial.Florial;
import net.florial.features.enemies.Mob;
import net.florial.features.enemies.events.MobDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class Crawlies extends Mob implements Listener {
    public Crawlies(EntityType entity) {
        super(EntityType.CAVE_SPIDER, EntityType.ZOMBIE, 35, 4, 15, List.of(new ItemStack(Material.GUNPOWDER)));

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());

    }

}

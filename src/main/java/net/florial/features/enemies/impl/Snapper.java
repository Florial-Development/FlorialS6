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

public class Snapper extends Mob implements Listener {
    public Snapper(EntityType entity) {
        super(EntityType.RAVAGER, EntityType.SPIDER, 100, 8, 20, List.of(new ItemStack(Material.GUNPOWDER)));

    }

}

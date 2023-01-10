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

public class Wisps extends Mob implements Listener {
    public Wisps(EntityType entity) {
        super(EntityType.WITCH, EntityType.CREEPER, 45, 5, 20, List.of(new ItemStack(Material.GUNPOWDER)));

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());

    }

}
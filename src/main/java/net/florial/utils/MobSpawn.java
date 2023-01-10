package net.florial.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class MobSpawn {

    public static Entity spawnMob(EntityType e, World w, Location loc){
        Entity ent = w.spawnEntity(loc, e);
        return ent;
    }
}

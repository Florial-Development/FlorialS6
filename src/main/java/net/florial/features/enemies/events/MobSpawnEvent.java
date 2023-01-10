package net.florial.features.enemies.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MobSpawnEvent extends MobEvent {

    Entity former;

    World w;

    Location loc;

    public MobSpawnEvent(Entity former, World w, Location loc) {

        this.former = former;
        this.w = w;
        this.loc = loc;
    }

}

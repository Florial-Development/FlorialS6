package net.florial.features.enemies.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MobDeathEvent extends MobEvent {

    EntityType entity;

    List<ItemStack> drops;


    public MobDeathEvent(EntityType entity, List<ItemStack> drops) {

        this.entity = entity;
        this.drops = drops;
    }

}

package net.florial.features.enemies;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.florial.Florial;
import net.florial.features.enemies.events.MobDeathEvent;
import net.florial.features.enemies.events.MobSpawnEvent;
import net.florial.utils.MobSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class Mob implements Listener {

    EntityType entity;

    EntityType ireplace;

    int health;

    int dmg;

    int toughness;

    List<ItemStack> drops;


    protected Mob(EntityType entity, EntityType ireplace, int health, int dmg, int toughness, List<ItemStack> drops) {
        this.entity = entity;
        this.health = health;
        this.dmg = dmg;
        this.toughness = toughness;
        this.ireplace = ireplace;
        this.drops = drops;

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());

    }

    @EventHandler
    public void spawnMyself(MobSpawnEvent event) {

        if (!(event.getFormer().getType() != this.ireplace)) return;

        Collection<Entity> aminear = event.getW().getNearbyEntitiesByType(this.entity.getEntityClass(), event.getLoc(), 15);

        if (aminear.size() > 0) return;

        LivingEntity me = (LivingEntity) MobSpawn.spawnMob(this.entity, event.getW(), event.getLoc());
        me.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(this.dmg);
        me.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(this.toughness);
        me.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.health);

        me.setRemoveWhenFarAway(true);

        if (this.entity == EntityType.HOGLIN) ((Hoglin) me).setImmuneToZombification(true);


    }

    @EventHandler
    public void whenIdie(MobDeathEvent event) {

        if (event.getEntity() != this.entity) return;

        for (ItemStack i : this.drops) {event.getDrops().add(i);}
    }


}

package net.florial.listeners;

import net.florial.utils.GetChance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AnimalListener implements Listener {

    GetChance GetChance = new GetChance();


    @EventHandler
    public void passiveSpawn(CreatureSpawnEvent e) {
        EntityType ent = e.getEntityType();
        if (!List.of(EntityType.SHEEP, EntityType.COW, EntityType.CHICKEN, EntityType.PIG, EntityType.RABBIT).contains(ent))
            return;

        if (GetChance.getChance(15)) {
            e.setCancelled(true);
        } else {
            addPotionEffects(e.getEntity());
        }
    }

    private static void addPotionEffects(LivingEntity e) {
        PotionEffect resist = new PotionEffect(PotionEffectType.SPEED, 1000000, 2, false, false, true);
        PotionEffect speed = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 3, false, false, true);

        for (PotionEffect effect : List.of(resist, speed)) {e.addPotionEffect(effect);}
    }


}

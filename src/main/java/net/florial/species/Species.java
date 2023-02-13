package net.florial.species;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.florial.Florial;
import net.florial.features.enemies.events.MobSpawnEvent;
import net.florial.models.PlayerData;
import net.florial.species.disguises.Morph;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import net.florial.species.events.impl.SpeciesSwitchEvent;
import net.florial.utils.MobSpawn;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class Species implements Listener {
    
    String name;
    int id;
    double maxHealth;
    
    protected Species(String name, int id, double maxHealth) {
        this.name = name;
        this.id = id;
        this.maxHealth = maxHealth;

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());


    }
    
    public Set<PotionEffect> effects() {
        return new HashSet<>();
    }

    public Set<ItemStack> diet() {
        return new HashSet<>();
    }

    public Set<String> descriptions() {
        return new HashSet<>();
    }

    @EventHandler
    public void whenISwitch(SpeciesSwitchEvent event) {

        PlayerData data = event.getPlayerData();

        data.refresh();

        Morph.activate(event.getPlayer(), "" + event.getSpecie(), "", false);

        data.getSpecies().effects().forEach(effect -> data.getPlayer().removePotionEffect(effect.getType()));
    }
    
}

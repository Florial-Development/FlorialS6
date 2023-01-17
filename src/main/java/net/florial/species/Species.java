package net.florial.species;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class Species implements Listener {
    
    String name;
    int id;
    double maxHealth = 20d;
    
    protected Species(String name, int id) {
        this.name = name;
        this.id = id;

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());


    }
    
    public Set<PotionEffect> effects() {
        return new HashSet<>();
    }

    public void apply(Player player) {}

    
}

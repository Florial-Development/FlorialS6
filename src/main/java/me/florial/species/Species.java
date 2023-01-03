package me.florial.species;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

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
    }
    
    public Set<PotionEffectType> performAbility() {
        return new HashSet<>();
    }
    
}

package net.florial.species;

import lombok.Getter;
import net.florial.Florial;
import net.florial.species.impl.Cat;
import net.florial.species.impl.Fox;
import net.florial.species.impl.Human;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SpecieType {
    NONE(0, null),
    CAT(1, new Cat(1)),
    FOX(2, new Fox(2)),
    HUMAN(3, new Human(3));

    @Getter private final int id;
    @Getter private final Species specie;
    
    SpecieType(int id, Species specie) {
        this.id = id;
        this.specie = specie;
    }
    
    public static SpecieType fromID(int id) {
        for (SpecieType e : values())
            if (e.id == id) return e;
        
        return null;
    }

    public static List<Species> getAllSpecies() {
        return Arrays.stream(values()).map(SpecieType::getSpecie).collect(Collectors.toList());
    }
}

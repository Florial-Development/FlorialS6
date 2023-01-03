package me.florial.species;

import lombok.Getter;
import me.florial.species.impl.Cat;
import me.florial.species.impl.Fox;
import me.florial.species.impl.Human;

public enum SpecieType {
    NONE(0, null),
    CAT(1, new Cat()),
    FOX(2, new Fox()),
    HUMAN(3, new Human());

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
    
}

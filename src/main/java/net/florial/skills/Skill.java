package net.florial.skills;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Transient;
import lombok.Getter;
import lombok.Setter;

public enum Skill {

    //base skills

    SCENT(1, 1),
    RESISTANCE(2, 1),
    STRENGTH(3, 1),
    SURVIVAL(4, 1),
    //a species specific skill
    SPECIFIC(5, 1);
    @Getter private final int skill;
    @Transient @Setter @Getter private int lvl;
    
    Skill(int skill, int lvl) {
        this.skill = skill;
        this.lvl = lvl;
    }

    public static Skill fromID(int skill) {
        for (Skill e : values())
            if (e.skill == skill) return e;
        
        return null;
    }

}

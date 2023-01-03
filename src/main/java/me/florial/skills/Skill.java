package me.florial.skills;

import lombok.Getter;
import lombok.Setter;

public enum Skill {
    BURROW(1, 0),
    SCENT(2, 0),
    RESISTANCE(3, 0),
    STRENGTH(4, 0),
    TBA(5, 0);

    @Setter @Getter private int skill;
    @Getter private final int lvl;
    
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

package net.florial.species.events.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.florial.models.PlayerData;
import net.florial.species.SpecieType;
import net.florial.species.events.SpeciesEvent;
import org.bukkit.entity.Player;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpeciesKillEvent extends SpeciesEvent {
    
    Player attacker;
    Player victim;
    PlayerData playerData;
    SpecieType species;
    
    public SpeciesKillEvent(Player attacker, Player victim, PlayerData data, SpecieType specie) {
        this.attacker = attacker;
        this.victim = victim;
        this.playerData = data;
        this.species = specie;
    }

}

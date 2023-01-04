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
public class SpeciesRespawnEvent extends SpeciesEvent {
    
    Player player;
    PlayerData playerData;
    SpecieType species;
    
    public SpeciesRespawnEvent(Player player, PlayerData data, SpecieType specie) {
        this.player = player;
        this.playerData = data;
        this.species = specie;
    }

}

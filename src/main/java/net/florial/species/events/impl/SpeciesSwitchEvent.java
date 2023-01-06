package net.florial.species.events.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.florial.models.PlayerData;
import net.florial.species.SpecieType;
import net.florial.species.events.SpeciesEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpeciesSwitchEvent extends SpeciesEvent implements Cancellable {

    @Setter boolean cancelled = false;
    final Player player;
    final PlayerData playerData;
    final SpecieType previous;
    final SpecieType specie;

    public SpeciesSwitchEvent(Player player, PlayerData data, SpecieType previousSpecie, SpecieType newSpecie) {
        this.player = player;
        this.playerData = data;
        this.previous = previousSpecie;
        this.specie = newSpecie;
    }

}

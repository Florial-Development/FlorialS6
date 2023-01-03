package me.florial.species.impl;

import me.florial.species.SpecieType;
import me.florial.species.Species;
import org.bukkit.event.EventHandler;
import me.florial.species.events.impl.SpeciesRespawnEvent;

public class Human extends Species {
    
    public Human() {
        super("Human", SpecieType.HUMAN.getId());
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A human has respawned!");
    }
    
}

package net.florial.species.impl;

import net.florial.species.SpecieType;
import net.florial.species.Species;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import org.bukkit.event.EventHandler;

public class Human extends Species {
    
    public Human() {
        super("Human", SpecieType.HUMAN.getId());
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A human has respawned!");
    }
    
}

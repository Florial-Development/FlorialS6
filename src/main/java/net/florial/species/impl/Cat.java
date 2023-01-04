package net.florial.species.impl;

import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bukkit.event.EventHandler;
import net.florial.species.events.impl.SpeciesRespawnEvent;

public class Cat extends Species {
    
    public Cat() {
        super("Cat", SpecieType.CAT.getId());
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A cat has respawned!");
    }
    
}

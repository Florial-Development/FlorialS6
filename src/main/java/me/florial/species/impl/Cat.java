package me.florial.species.impl;

import me.florial.species.SpecieType;
import me.florial.species.Species;
import org.bukkit.event.EventHandler;
import me.florial.species.events.impl.SpeciesRespawnEvent;

public class Cat extends Species {
    
    public Cat() {
        super("Cat", SpecieType.CAT.getId());
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A cat has respawned!");
    }
    
}

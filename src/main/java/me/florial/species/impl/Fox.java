package me.florial.species.impl;

import me.florial.species.SpecieType;
import me.florial.species.Species;
import org.bukkit.event.EventHandler;
import me.florial.species.events.impl.SpeciesRespawnEvent;

public class Fox extends Species {
    
    public Fox() {
        super("Fox", SpecieType.FOX.getId());
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A fox has respawned!");
    }
    
}

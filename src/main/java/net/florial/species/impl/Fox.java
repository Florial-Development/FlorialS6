package net.florial.species.impl;

import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bukkit.event.EventHandler;
import net.florial.species.events.impl.SpeciesRespawnEvent;

public class Fox extends Species {
    
    public Fox(int id) {
        super("Fox", id);
    }
    
    @EventHandler
    public void onRespawn(SpeciesRespawnEvent event) {
        event.getPlayer().sendMessage("A fox has respawned!");
    }
    
}

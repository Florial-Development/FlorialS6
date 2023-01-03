package me.florial.species.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class SpeciesEvent extends Event {
    public static final HandlerList handlerList = new HandlerList();
    
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
    
    public static HandlerList getHandlerList() {
        return handlerList;
    }
}

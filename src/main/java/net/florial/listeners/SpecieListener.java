package net.florial.listeners;

import net.florial.Florial;
import net.florial.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import net.florial.species.events.impl.SpeciesDeathEvent;
import net.florial.species.events.impl.SpeciesKillEvent;
import net.florial.species.events.impl.SpeciesRespawnEvent;

public class SpecieListener implements Listener {
    
    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        if (data == null) return;
        
        SpeciesRespawnEvent e = new SpeciesRespawnEvent(
            event.getPlayer(),
            data,
            data.getSpecieType()
        );

        data.refresh();

        Bukkit.getPluginManager().callEvent(e);
    }
    
    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        if (data == null) return;
        
        SpeciesDeathEvent death = new SpeciesDeathEvent(
            event.getPlayer(),
            data,
            data.getSpecieType()
        );
        
        Bukkit.getPluginManager().callEvent(death);
    
        if (event.getPlayer().getKiller() != null) {
            SpeciesKillEvent kill = new SpeciesKillEvent(
                event.getPlayer().getKiller(),
                event.getPlayer(),
                Florial.getPlayerData().get(event.getPlayer().getKiller().getUniqueId()),
                Florial.getPlayerData().get(event.getPlayer().getUniqueId()).getSpecieType()
            );
    
            Bukkit.getPluginManager().callEvent(kill);
        }
    }
    
}

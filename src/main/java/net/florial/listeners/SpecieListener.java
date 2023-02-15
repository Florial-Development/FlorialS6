package net.florial.listeners;

import dev.morphia.query.filters.Filters;
import net.florial.Florial;
import net.florial.Refresh;
import net.florial.models.PlayerData;
import net.florial.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import net.florial.species.events.impl.SpeciesDeathEvent;
import net.florial.species.events.impl.SpeciesKillEvent;
import net.florial.species.events.impl.SpeciesRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.florial.models.PlayerData.getFieldValue;

public class SpecieListener implements Listener {

    Florial florial = Florial.getInstance();

    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        if (data == null) return;
        
        SpeciesRespawnEvent e = new SpeciesRespawnEvent(
            event.getPlayer(),
            data,
            data.getSpecieType()
        );
        Bukkit.getPluginManager().callEvent(e);
        GeneralUtils.runAsync(new BukkitRunnable() {@Override public void run() {Bukkit.getScheduler().runTaskLater(florial, data::refresh, 40L);}});


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

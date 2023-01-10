package net.florial.listeners;

import net.florial.features.thirst.ThirstManager;
import net.florial.Florial;
import net.florial.database.FlorialDatabase;
import net.florial.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        FlorialDatabase.getPlayerData(p).thenAccept(playerData -> {
           Florial.getPlayerData().put(p.getUniqueId(), playerData);
        });

        if (Florial.getThirst().get(p.getUniqueId()) == null) Florial.getThirst().put(p.getUniqueId(), 20);

        Bukkit.getScheduler().runTaskTimerAsynchronously(Florial.getInstance(), () -> {
            if (!p.isOnline()) return;
            ThirstManager.updateThirst(p);
            }, 35L, 35);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        data.save(true);
    }

}

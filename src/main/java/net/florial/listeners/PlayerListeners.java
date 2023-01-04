package net.florial.listeners;

import net.florial.Florial;
import net.florial.database.FlorialDatabase;
import net.florial.models.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FlorialDatabase.getPlayerData(event.getPlayer()).thenAccept(playerData -> {
           Florial.getPlayerData().put(event.getPlayer().getUniqueId(), playerData);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        data.save(true);
    }
}

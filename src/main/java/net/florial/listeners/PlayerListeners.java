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

    private static ThirstManager ThirstManager = new ThirstManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        FlorialDatabase.getPlayerData(p).thenAccept(playerData -> {
           Florial.getPlayerData().put(p.getUniqueId(), playerData);
        });

        ThirstManager.thirstRunnable(p);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        data.save(true);
    }

}

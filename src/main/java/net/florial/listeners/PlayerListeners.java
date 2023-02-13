package net.florial.listeners;

import net.florial.features.thirst.ThirstManager;
import net.florial.Florial;
import net.florial.database.FlorialDatabase;
import net.florial.models.PlayerData;
import net.florial.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerListeners implements Listener {

    private static final ThirstManager ThirstManager = new ThirstManager();
    private static final Florial florial = Florial.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID u = p.getUniqueId();

        FlorialDatabase.getPlayerData(p).thenAccept(playerData -> Florial.getPlayerData().put(u, playerData));

        GeneralUtils.runAsync((BukkitRunnable) Bukkit.getScheduler().runTaskLater(florial, Florial.getPlayerData().get(u)::refresh, 20L));

        ThirstManager.thirstRunnable(p);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        data.save(true);
    }

}

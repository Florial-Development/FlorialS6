package listeners;

import core.Florial;
import mysql.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import species.speciesinternal.SpeciesEnum;
import utils.GeneralUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        // Check if they don't exist in the db (first time join)
                        PreparedStatement statement = Florial.getInstance().getDatabase().getConnection().prepareStatement("SELECT * FROM florial_players WHERE uuid = ?");
                        statement.setString(1, event.getPlayer().getUniqueId().toString());
                        ResultSet results = statement.executeQuery();
                        if (!results.next()) {
                            PlayerData data = new PlayerData(event.getPlayer().getUniqueId().toString(), 0, 0);
                            Florial.getInstance().getDatabase().createPlayerStats(data);
                            Florial.getInstance().playerData.put(event.getPlayer(), data);
                            statement.close();
                        } else {
                            PlayerData data = new PlayerData(event.getPlayer().getUniqueId().toString(), results.getInt(2), results.getInt(3));
                            Florial.getInstance().playerData.put(event.getPlayer(), data);
                        }
                    } catch (SQLException e) {
                        event.getPlayer().sendMessage(Component.text());
                    }
                }
            }.runTaskAsynchronously(Florial.getInstance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerData data = Florial.getInstance().getPlayerData(event.getPlayer());
                data.save();
            }
        }.runTaskAsynchronously(Florial.getInstance());
    }
}

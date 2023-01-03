package me.florial.species;

import me.florial.Florial;
import me.florial.models.PlayerData;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class SpeciesWrapper {

    public static void setSpecies(UUID player, int species) throws SQLException {
        PlayerData data = Florial.getDatabase().getPlayerStatsfromDatabase(player);
        data.setSpecieId(species);

        Florial.getDatabase().updatePlayerStats(data);
    }

    // Method to get the species of a player
    public static String getSpecies(UUID player) throws SQLException {
        PlayerData data = Florial.getDatabase().findPlayerStatsbyUUID(player.toString());
        return data.getSpecies().getName();
    }

}

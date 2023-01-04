package net.florial.species;

import net.florial.Florial;
import net.florial.models.PlayerData;

import java.sql.SQLException;
import java.util.UUID;

public class SpeciesWrapper {

    public static void setSpecies(UUID player, int species) throws SQLException {
        PlayerData data = Florial.getPlayerData().get(player);
        data.setSpecieId(species);

        Florial.getPlayerData().put(player, data);
    }

    // Method to get the species of a player
    public static Species getSpecies(UUID player) throws SQLException {
        return Florial.getPlayerData().get(player).getSpecies();

    }

}

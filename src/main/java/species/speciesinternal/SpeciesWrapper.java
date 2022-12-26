package species.speciesinternal;

import core.Florial;
import mysql.PlayerData;
import species.Cat;
import species.Fox;
import species.Human;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class SpeciesWrapper {

    private Florial plugin;
    // Define the different species that a player can be
    public static final int HUMAN = 0;
    public static final int CAT = 1;
    public static final int FOX = 2;

    public SpeciesWrapper(Florial plugin) {
        this.plugin = plugin;
    }

    //public SpeciesWrapper() {;
    //}

    // Method to set the species of a player
    public void setSpecies(UUID player, int species) throws SQLException {
        PlayerData data = plugin.getDatabase().getPlayerStatsfromDatabase(player);
        data.setSpecies(species);

        plugin.getDatabase().updatePlayerStats(data);
    }

    // Method to get the species of a player
    public String getSpecies(UUID player, Boolean obj) throws SQLException {
        PlayerData data = plugin.getDatabase().findPlayerStatsbyUUID(player.toString());
        Integer type =  data.getSpecies();
        if (obj == true) return type.toString();
        switch (type) {
            case CAT:
                return "cat";
            case HUMAN:
                return "human";
            case FOX:
                return "fox";
        }
        return null;
    }

    public Species getSpeciesObject(UUID player) throws SQLException {
        int species = Integer.valueOf(getSpecies(player, true));
        switch (species) {
            case FOX:
                return new Fox();
            case CAT:
                return new Cat();
            case HUMAN:
                return new Human();
        }
        return null;
    }
}

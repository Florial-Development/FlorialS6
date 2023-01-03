package me.florial.models;

import me.florial.Florial;
import lombok.Data;
import me.florial.species.SpecieType;
import me.florial.species.Species;
import org.bukkit.Bukkit;

import java.sql.SQLException;

@Data
public class PlayerData {

    String uuid;
    int dna;
    int specieId;

    public PlayerData(String uuid, int species, int dna) {
        this.uuid = uuid;
        this.dna = dna;
        this.specieId = species;
    }

    public SpecieType getSpecieType() {
        return SpecieType.fromID(specieId);
    }
    
    public Species getSpecies() {
        return getSpecieType().getSpecie();
    }
    
    public void save() {
        try {
            Florial.getInstance().getDatabase().updatePlayerStats(this);
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not update player stats to the database, ignoring.");
            e.printStackTrace();
        }
    }
}

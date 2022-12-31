package mysql;

import core.Florial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import species.speciesinternal.SpeciesEnum;

import java.sql.SQLException;

public class PlayerData {

    @Getter @Setter
    private String uuid;
    @Getter @Setter
    private int dna;
    @Getter @Setter
    private int species;


    public PlayerData(String uuid, int species, int dna) {
        this.uuid = uuid;
        this.dna = dna;
        this.species = species;
    }

    public SpeciesEnum getSpeciesEnum() {
        return SpeciesEnum.getSpeciesFromID(species);
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

package mysql;

import core.Florial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import species.speciesinternal.SpeciesEnum;
import upgrades.Skill;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerData {

    @Getter @Setter
    private String uuid;
    @Getter @Setter
    private int dna;
    @Getter @Setter
    private int species;

    @Getter @Setter
    private ArrayList<Skill> skills;


    public PlayerData(String uuid, int species, int dna, ArrayList<Skill> skills) {
        this.uuid = uuid;
        this.dna = dna;
        this.species = species;
        this.skills = skills;
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

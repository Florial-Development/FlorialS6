package net.florial.models;

import com.mongodb.lang.Nullable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Data;
import net.florial.database.FlorialDatabase;
import net.florial.skills.Skill;
import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

@Data
@Entity("playerdata")
public class PlayerData {

    @Id
    private ObjectId _id = new ObjectId();

    private String UUID;
    private int flories = 0;
    private int dna = 0;
    private int dnaXP = 0;
    private int specieId = 0;
    @Nullable
    String pronouns = "";
    ArrayList<Skill> skills = new ArrayList<>();

    public PlayerData(String uuid, int flories, int dna, int dnaXP, int specieId, @org.jetbrains.annotations.Nullable String pronouns, ArrayList<Skill> skills) {
        this.UUID = uuid;
        this.flories = flories;
        this.dna = dna;
        this.dnaXP = dnaXP;
        this.specieId = specieId;
        this.pronouns = pronouns;
        this.skills = skills;
    }

    public PlayerData(String uuid) {
        this.UUID = uuid;
    }

    public PlayerData() {}

    public SpecieType getSpecieType() {
        return SpecieType.fromID(specieId);
    }
    
    public Species getSpecies() {
        return getSpecieType().getSpecie();
    }

    @BsonIgnore
    public Player getPlayer() {
        return Bukkit.getPlayer(java.util.UUID.fromString(this.UUID));
    }

    @BsonIgnore
    public void save(boolean async) {
        if (async) FlorialDatabase.createNewPlayerDataAsync(this);
        else FlorialDatabase.createNewPlayerData(this);
    }

    @BsonIgnore
    public void refresh() {
        for (PotionEffectType effect : getSpecies().effects()) {
            getPlayer().addPotionEffect(new PotionEffect(effect, 20000000, 1, true, false));
        }
    }

}

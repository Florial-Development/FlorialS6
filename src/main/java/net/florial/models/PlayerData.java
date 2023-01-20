package net.florial.models;

import com.mongodb.lang.Nullable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Data;
import net.florial.Florial;
import net.florial.database.FlorialDatabase;
import net.florial.skills.Skill;
import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;

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

    private int event = 0;
    @Nullable
    String pronouns = "";

    HashMap<Skill, Integer> skills = new HashMap<>(Map.of(Skill.SCENT,1, Skill.RESISTANCE,1, Skill.STRENGTH,1, Skill.SURVIVAL,1, Skill.SPECIFIC,1));
    public PlayerData(String uuid, int flories, int dna, int dnaXP, int specieId, @org.jetbrains.annotations.Nullable String pronouns, HashMap<Skill,Integer> skills, int event) {

        this.UUID = uuid;
        this.flories = flories;
        this.dna = dna;
        this.dnaXP = dnaXP;
        this.specieId = specieId;
        this.pronouns = pronouns;
        this.skills = skills;
        this.event = event;
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

    public int getDnaXP() {

        DnaLVLup();
        return dnaXP;
    }

    public int getDna() {
        DnaLVLup();
        return dna;
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

        if (getSpecies() == null) return;

        Bukkit.getScheduler().runTaskLater(Florial.getInstance(), () -> {
            for (PotionEffect effect : getSpecies().effects()) {
            getPlayer().addPotionEffect(effect);}}, 70L);

    }

    @BsonIgnore
    private void DnaLVLup(){
        if (!(dnaXP > 499)) return;
        dnaXP = 0;
        dna = dna+1;
        
    }

}

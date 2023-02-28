package net.florial.models;

import com.mongodb.lang.Nullable;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import lombok.Data;
import net.florial.Florial;
import net.florial.Refresh;
import net.florial.database.FlorialDatabase;
import net.florial.features.skills.Skill;
import net.florial.features.upgrades.Upgrade;
import net.florial.species.SpecieType;
import net.florial.species.Species;
import net.florial.utils.CustomItem;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Field;
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
    @Nullable
    String prefix = "";

    HashMap<Skill, Integer> skills = new HashMap<>(Map.of(Skill.SCENT,1, Skill.RESISTANCE,1, Skill.STRENGTH,1, Skill.SURVIVAL,1, Skill.SPECIFIC,1));
    HashMap<Upgrade, Boolean> upgrades = new HashMap<>();

    public PlayerData(String uuid, int flories, int dna, int dnaXP, int specieId, @org.jetbrains.annotations.Nullable String pronouns, HashMap<Skill,Integer> skills, HashMap<Upgrade,Boolean> upgrades, int event, String prefix) {

        this.UUID = uuid;
        this.flories = flories;
        this.dna = dna;
        this.dnaXP = dnaXP;
        this.specieId = specieId;
        this.pronouns = pronouns;
        this.skills = skills;
        this.upgrades = upgrades;
        this.event = event;
        this.prefix = prefix;
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

        if (getSpecies() == null) return;

        Bukkit.getScheduler().runTaskLater(Florial.getInstance(), () -> {
            for (PotionEffect effect : getSpecies().effects()) {
                getPlayer().addPotionEffect(effect);}}, 70L);

        if (getSpecies().isCanSmell()) getPlayer().getInventory().setItem(8, NBTEditor.set(CustomItem.MakeItem(new ItemStack(Material.PAPER), "#6A3A2F&lSCENT [CLICK]", "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑\n #ffa2c4&l︳#ffa2c4 Right-Click to smell Entity\n #ffa2c4&l︳#ffa2c4 Left-Click to Track Food\n  #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false), 1, "CustomModelData"));

        Refresh.load(getPlayer(), this);

    }

    public static int getFieldValue(PlayerData playerData, String fieldName) {
        try {
            Field field = playerData.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (int) field.get(playerData);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // dna section

    public int getDnaXP() {
        DnaLVLup();
        return dnaXP;
    }

    public int getDna() {
        DnaLVLup();
        return dna;
    }

    @BsonIgnore
    private void DnaLVLup(){
        if (dnaXP <= 500) return;
        dnaXP = 0;
        dna = dna+1;

    }

    public void overwrite() {
        if (Bukkit.getPlayer(UUID) == null) return;
        Florial.getPlayerData().put(Bukkit.getPlayer(UUID).getUniqueId(), this);
    }

}

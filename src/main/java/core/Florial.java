package core;

import co.aikar.commands.PaperCommandManager;
import commands.ChangeSpecies;
import commands.SpeciesCheckCommand;
import io.github.nosequel.menu.MenuHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import mysql.FlorialDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import species.speciesinternal.SpeciesWrapper;

import java.sql.SQLException;
import java.util.*;

@Getter
public final class Florial extends JavaPlugin {


    @Getter
    private static Florial instance;

    private FlorialDatabase database;

    public HashMap<UUID, Integer> species = new HashMap<>();
    //set on join
    //remove on leave


    final FileConfiguration config2 = this.getConfig();


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        setupCommands();
        setupListeners();
        new MenuHandler(this);
        enableRecipes();
        setupManagers();
        new SpeciesWrapper(this);


        try{
            this.database = new FlorialDatabase(this);
            database.initializeDatabase();
        } catch (SQLException e){
            System.out.println("Unable to load database, connect, or create tables");
            e.printStackTrace();

        }

        // test

    }


    @Override
    public void onDisable() {
        //if (!(Cooldown.getCooldownMap("powercooldown1") == null)) Cooldown.getCooldownMap("powercooldown1").clear();
        this.database.closeConnection();
        saveConfig();
    }

    private void setupManagers() {
        //if (!(Cooldown.getCooldownMap("powercooldown1") == null)) Cooldown.getCooldownMap("powercooldown1").clear();

    }

    private void setupListeners() {
    }

    private void enableRecipes() {
        ArrayList<ItemStack> ritems;
        registerRecipes("cheat_apple", true, "121", "   ", "   ", ritems = new ArrayList<>(Arrays.asList(
                new ItemStack(Material.APPLE),
        new ItemStack(Material.GOLD_BLOCK),
        null, null, null, null, null, null, null)), new ItemStack(Material.GOLDEN_APPLE));
    }

    private void registerRecipes(String thekey, Boolean isShaped, String column1, String column2, String column3, ArrayList<ItemStack> ritems, ItemStack output) {
        NamespacedKey key = new NamespacedKey(this, thekey);
        ShapelessRecipe therecipe = new ShapelessRecipe(key, output);
        ShapedRecipe therecipe2 = new ShapedRecipe(key, output);
        if (isShaped) therecipe2.shape(column1, column2, column3);
        Iterator i = ritems.iterator();
        int itemindex = 1;
        while (i.hasNext()) {
            if (itemindex == 9) break;
            if (isShaped) {
                if (itemindex == 1) {
                    therecipe2.setIngredient(Character.forDigit(1, 10), new RecipeChoice.ExactChoice(ritems.get(0)));
                } else {
                    if (ritems.get(itemindex - 1) != null) therecipe2.setIngredient(Character.forDigit(itemindex, 10), new RecipeChoice.ExactChoice(ritems.get(itemindex - 1)));
                }
            } else {
                if (ritems.get(itemindex-1) != null)
                    therecipe.addIngredient(new RecipeChoice.ExactChoice(ritems.get(itemindex-1)));
            }
            itemindex++;
        }
        Bukkit.removeRecipe(key);
        Bukkit.addRecipe(therecipe2);
        if (!(isShaped)) Bukkit.addRecipe(therecipe2);
    }


    public void updateScoreBoards() {
    }

    public FlorialDatabase getDatabase(){
        return database;
    }

    private void setupCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new SpeciesCheckCommand(this));
        manager.registerCommand(new ChangeSpecies(this));

    }

    public Florial getInstance(){
        return instance;
    }
}
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

@Getter
public final class Florial extends JavaPlugin {


    @Getter
    private static Florial instance;

    private FlorialDatabase database;


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
        saveConfig();
    }

    private void setupManagers() {
        //if (!(Cooldown.getCooldownMap("powercooldown1") == null)) Cooldown.getCooldownMap("powercooldown1").clear();

    }

    private void setupListeners() {
    }

    private void enableRecipes() {
    }

    private void registerRecipes(String thekey, Boolean isShaped, String column1, String column2, String column3,
                                 ItemStack recipeitem1, ItemStack recipeitem2, ItemStack recipeitem3, ItemStack recipeitem4,
                                 ItemStack recipeitem5, ItemStack recipeitem6, ItemStack recipeitem7, ItemStack recipeitem8, ItemStack recipeitem9, ItemStack output) {
        NamespacedKey key = new NamespacedKey(this, thekey);
        ShapelessRecipe therecipe = new ShapelessRecipe(key, output);
        ShapedRecipe therecipe2 = new ShapedRecipe(key, output);
        if (isShaped == true) {
            therecipe2.shape(column1, column2, column3);
            if (!(recipeitem1.getType() == Material.AIR))
                therecipe2.setIngredient('1', new RecipeChoice.ExactChoice(recipeitem1));
            if (!(recipeitem2.getType() == Material.AIR))
                therecipe2.setIngredient('2', new RecipeChoice.ExactChoice(recipeitem2));
            if (!(recipeitem3.getType() == Material.AIR))
                therecipe2.setIngredient('3', new RecipeChoice.ExactChoice(recipeitem3));
            if (!(recipeitem4.getType() == Material.AIR))
                therecipe2.setIngredient('4', new RecipeChoice.ExactChoice(recipeitem4));
            if (!(recipeitem5.getType() == Material.AIR))
                therecipe2.setIngredient('5', new RecipeChoice.ExactChoice(recipeitem5));
            if (!(recipeitem6.getType() == Material.AIR))
                therecipe2.setIngredient('6', new RecipeChoice.ExactChoice(recipeitem6));
            if (!(recipeitem7.getType() == Material.AIR))
                therecipe2.setIngredient('7', new RecipeChoice.ExactChoice(recipeitem7));
            if (!(recipeitem8.getType() == Material.AIR))
                therecipe2.setIngredient('8', new RecipeChoice.ExactChoice(recipeitem8));
            if (!(recipeitem9.getType() == Material.AIR))
                therecipe2.setIngredient('9', new RecipeChoice.ExactChoice(recipeitem9));
            Bukkit.removeRecipe(key);
            Bukkit.addRecipe(therecipe2);
            return;
        } else {
            if (!(recipeitem1.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem1));
            if (!(recipeitem2.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem2));
            if (!(recipeitem3.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem3));
            if (!(recipeitem4.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem4));
            if (!(recipeitem5.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem5));
            if (!(recipeitem6.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem6));
            if (!(recipeitem7.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem7));
            if (!(recipeitem8.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem8));
            if (!(recipeitem9.getType() == Material.AIR))
                therecipe.addIngredient(new RecipeChoice.ExactChoice(recipeitem9));
            Bukkit.removeRecipe(key);
            Bukkit.addRecipe(therecipe);
            return;
        }
        //test
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
package me.florial;

import co.aikar.commands.PaperCommandManager;
import me.florial.commands.ChangeSpeciesCommand;
import me.florial.commands.SpeciesCheckCommand;
import io.github.nosequel.menu.MenuHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import me.florial.mysql.FlorialDatabase;
import me.florial.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.*;

public final class Florial extends JavaPlugin {
    
    public static Florial getInstance() {
        return getPlugin(Florial.class);
    }
    
    @Getter private static final HashMap<Player, PlayerData> playerData = new HashMap<>();
    @Getter private static FlorialDatabase database;


    @SneakyThrows
    @Override
    public void onEnable() {
        setupCommands();
        enableRecipes();
        
        new MenuHandler(this);
        
        try{
            database.initializeDatabase();
        } catch (SQLException e){
            System.out.println("Unable to load database, connect, or create tables");
            e.printStackTrace();
        }
    }


    @Override
    public void onDisable() {
        database.closeConnection();
        saveConfig();
    }

    private void enableRecipes() {
        registerRecipes("cheat_apple", true, "121", "   ", "   ", Arrays.asList(
                new ItemStack(Material.APPLE),
                new ItemStack(Material.GOLD_BLOCK),
                null, null, null, null, null, null, null), new ItemStack(Material.GOLDEN_APPLE));
    }
    
    @SuppressWarnings("SameParameterValue")
    private void registerRecipes(String key, boolean isShaped, String column1, String column2, String column3, List<ItemStack> ritems, ItemStack output) {
        Recipe recipe = isShaped ? new ShapedRecipe(NamespacedKey.minecraft(key),output) : new ShapelessRecipe(
                NamespacedKey.minecraft(key),output);
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            shapedRecipe.shape(column1, column2, column3);
            for (int i = 0; i < ritems.size(); i++) {

                if (i >= 9) break;

                ItemStack itemStack = ritems.get(i);

                if (itemStack != null)
                    shapedRecipe.setIngredient(Character.forDigit(i+1,10), new RecipeChoice.ExactChoice(itemStack) );
            }
        } else {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            ritems.forEach((itemStack) ->
                shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(itemStack)));
        }

        Bukkit.addRecipe(recipe);

    }

    private void setupCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new SpeciesCheckCommand());
        manager.registerCommand(new ChangeSpeciesCommand());

    }
    
    public PlayerData getPlayerData(Player player) {
        return playerData.get(player);
    }
}
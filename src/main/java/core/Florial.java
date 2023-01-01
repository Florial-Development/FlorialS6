package core;

import co.aikar.commands.PaperCommandManager;
import commands.ChangeSpecies;
import commands.SpeciesCheckCommand;
import io.github.nosequel.menu.MenuHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import mysql.FlorialDatabase;
import mysql.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import species.speciesinternal.SpeciesWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
public final class Florial extends JavaPlugin {


    @Getter
    private static Florial instance;

    private FlorialDatabase database;

    //set on join
    //remove on leave

    public HashMap<Player, PlayerData> playerData;

    final FileConfiguration config2 = this.getConfig();


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        playerData = new HashMap<>();
        setupCommands();
        setupListeners();
        new MenuHandler(this);
        enableRecipes();
        setupManagers();
        new SpeciesWrapper(this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        // Check if they don't exist in the db (first time join)
                        PreparedStatement statement = Florial.getInstance().getDatabase().getConnection().prepareStatement("SELECT * FROM florial_players WHERE uuid = ?");
                        statement.setString(1, player.getUniqueId().toString());
                        ResultSet results = statement.executeQuery();
                        if (!results.next()) {
                            PlayerData data = new PlayerData(player.getUniqueId().toString(), 0, 0);
                            Florial.getInstance().getDatabase().createPlayerStats(data);
                            Florial.getInstance().playerData.put(player, data);
                            statement.close();
                        } else {
                            PlayerData data = new PlayerData(player.getUniqueId().toString(), results.getInt(2), results.getInt(3));
                            Florial.getInstance().playerData.put(player, data);
                        }
                    } catch (SQLException e) {
                        player.sendMessage(Component.text());
                    }
                }
            }.runTaskAsynchronously(this);
        }


        try{
            this.database = new FlorialDatabase(this);
            database.initializeDatabase();
        } catch (SQLException e){
            Bukkit.getLogger().severe("Unable to load database, connect, or create tables");
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
        List<ItemStack> ritems;
        registerRecipes("cheat_apple", true, "121", "   ", "   ", ritems = Arrays.asList(
                new ItemStack(Material.APPLE),
                new ItemStack(Material.GOLD_BLOCK),
                null, null, null, null, null, null, null), new ItemStack(Material.GOLDEN_APPLE));
    }
    private void registerRecipes(String key, Boolean isShaped, String column1, String column2, String column3, List<ItemStack> ritems, ItemStack output) {
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
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            ritems.forEach((itemStack) ->
                    shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(itemStack)));
        }

        Bukkit.addRecipe(recipe);

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

    public PlayerData getPlayerData(Player player) {
        return playerData.get(player);
    }
}
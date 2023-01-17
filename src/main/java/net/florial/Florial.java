package net.florial;

import co.aikar.commands.PaperCommandManager;
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import net.florial.commands.ChangeSpeciesCommand;
import net.florial.commands.ChocolateerCommand;
import net.florial.commands.NuzzleCommand;
import net.florial.commands.SpeciesCheckCommand;
import lombok.Getter;
import lombok.SneakyThrows;
import net.florial.database.FlorialDatabase;
import net.florial.features.enemies.Mob;
import net.florial.features.enemies.impl.Boar;
import net.florial.features.enemies.impl.Crawlies;
import net.florial.features.enemies.impl.Snapper;
import net.florial.features.enemies.impl.Wisps;
import net.florial.features.thirst.ThirstManager;
import net.florial.listeners.MobsListener;
import net.florial.listeners.PlayerListeners;
import net.florial.listeners.SpecieListener;
import net.florial.listeners.ThirstListener;
import net.florial.models.PlayerData;
import net.florial.species.SpecieType;
import net.florial.species.Species;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Florial extends JavaPlugin {

    public static Florial getInstance() {
        return getPlugin(Florial.class);
    }

    @Getter
    private static final HashMap<UUID, PlayerData> playerData = new HashMap<>();

    @Getter private static final HashMap<UUID, Integer> thirst = new HashMap<>();


    @Getter
    private final InventoryManager manager = new InventoryManager(this);


    @SneakyThrows
    @Override
    public void onEnable() {

        init();

        manager.invoke();


        FlorialDatabase.initializeDatabase();
        enableRecipes();
    }


    @Override
    public void onDisable() {
        for (PlayerData data : playerData.values()) data.save(false);
        FlorialDatabase.closeConnection();
        saveConfig();
    }

    private void init(){
        saveDefaultConfig();
        setupCommands();
        setupListeners();
        loadData();
    }

    private void loadData(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            FlorialDatabase.getPlayerData(p).thenAccept(playerData -> {
                Florial.getPlayerData().put(p.getUniqueId(), playerData);
            });}

    }

    private void setupListeners(){

        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        getServer().getPluginManager().registerEvents(new SpecieListener(), this);
        getServer().getPluginManager().registerEvents(new ThirstListener(), this);
        getServer().getPluginManager().registerEvents(new ThirstManager(), this);
        getServer().getPluginManager().registerEvents(new MobsListener(), this);


        getServer().getPluginManager().registerEvents(new Boar(EntityType.HOGLIN), this);
        getServer().getPluginManager().registerEvents(new Snapper(EntityType.RAVAGER), this);
        getServer().getPluginManager().registerEvents(new Wisps(EntityType.WITCH), this);
        getServer().getPluginManager().registerEvents(new Crawlies(EntityType.CAVE_SPIDER), this);

        SpecieType.getAllSpecies().forEach(species -> {
            if (species == null) return;
            getServer().getPluginManager().registerEvents(species, this);
        });

    }

    private void enableRecipes() {
       // registerRecipes("cheat_apple", true, "121", "   ", "   ", Arrays.asList(
          //      new ItemStack(Material.APPLE),
             //   new ItemStack(Material.GOLD_BLOCK),
               // null, null, null, null, null, null, null), new ItemStack(Material.GOLDEN_APPLE));
    }

    @SuppressWarnings("SameParameterValue")
    private void registerRecipes(String key, boolean isShaped, String column1, String column2, String column3, List<ItemStack> ritems, ItemStack output) {
        Recipe recipe = isShaped ? new ShapedRecipe(NamespacedKey.minecraft(key), output) : new ShapelessRecipe(
                NamespacedKey.minecraft(key), output);
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            shapedRecipe.shape(column1, column2, column3);
            for (int i = 0; i < ritems.size(); i++) {

                if (i >= 9) break;

                ItemStack itemStack = ritems.get(i);

                if (itemStack != null)
                    shapedRecipe.setIngredient(Character.forDigit(i + 1, 10), new RecipeChoice.ExactChoice(itemStack));
            }
        } else {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            ritems.forEach((itemStack) ->
                    shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(itemStack)));
        }
        Bukkit.removeRecipe(new NamespacedKey(this, key));
        Bukkit.addRecipe(recipe);

    }

    private void setupCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new SpeciesCheckCommand());
        manager.registerCommand(new ChangeSpeciesCommand());
        manager.registerCommand(new NuzzleCommand());
        manager.registerCommand(new ChocolateerCommand());

    }

    public PlayerData getPlayerData(Player player) {return playerData.get(player);}

    public static HashMap<UUID, Integer> getThirst(){return thirst;}
}
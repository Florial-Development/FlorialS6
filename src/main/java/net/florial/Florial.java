package net.florial;

import co.aikar.commands.PaperCommandManager;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.florial.commands.*;
import net.florial.commands.discord.DiscordMuteCommand;
import net.florial.commands.discord.DiscordUwUCommand;
import net.florial.commands.species.ResetSpeciesCommand;
import net.florial.commands.species.SpeciesCommand;
import net.florial.database.FlorialDatabase;
import net.florial.features.chocolates.ChocolateEatListener;
import net.florial.features.chocolates.ChocolateerCommand;
import net.florial.features.enemies.impl.Boar;
import net.florial.features.enemies.impl.Crawlies;
import net.florial.features.enemies.impl.Snapper;
import net.florial.features.enemies.impl.Wisps;
import net.florial.features.skills.attack.AttackSkillListener;
import net.florial.features.skills.scent.ScentManager;
import net.florial.features.thirst.ThirstManager;
import net.florial.listeners.*;
import net.florial.models.PlayerData;
import net.florial.species.SpecieType;
import net.florial.utils.Cooldown;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Florial extends JavaPlugin {

    public static Florial getInstance() {
        return getPlugin(Florial.class);
    }
    @Getter private static final HashMap<UUID, PlayerData> playerData = new HashMap<>();
    @Getter private static final HashMap<UUID, Integer> thirst = new HashMap<>();
    @Getter private static Guild discordServer;
    @Getter
    private JDA discordBot;
    @Getter
    private final InventoryManager manager = new InventoryManager(this);

    @Getter
    private Economy economy = null;
    @Getter
    private LuckPerms lpapi = null;

    private static final ThirstManager ThirstManager = new ThirstManager();


    @SneakyThrows
    @Override
    public void onEnable() {

        init();
        manager.invoke();

        FlorialDatabase.initializeDatabase();
        enableRecipes();
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new UnknownDependencyException("Vault was not found on this site");
        }

        initializeDiscord();

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) throw new NullPointerException("Economy service provider was not found");
        economy = rsp.getProvider();
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            lpapi = provider.getProvider();
        } else {
            throw new NullPointerException("No luckperms found");
        }
    }


    @Override
    public void onDisable() {
        discordBot.shutdownNow();
        while (discordBot.getStatus() != JDA.Status.SHUTDOWN) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (PlayerData data : playerData.values()) data.save(false);
        FlorialDatabase.closeConnection();
        saveConfig();
    }

    private void init(){
        saveDefaultConfig();
        setupCommands();
        manager.invoke();

        Bukkit.broadcastMessage("confirm");


        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        getServer().getPluginManager().registerEvents(new SpecieListener(), this);
        getServer().getPluginManager().registerEvents(new ThirstListener(), this);
        getServer().getPluginManager().registerEvents(new ThirstManager(), this);
        getServer().getPluginManager().registerEvents(new MobsListener(), this);
        getServer().getPluginManager().registerEvents(new AnimalListener(), this);


        getServer().getPluginManager().registerEvents(new Boar(EntityType.HOGLIN), this);
        getServer().getPluginManager().registerEvents(new Snapper(EntityType.RAVAGER), this);
        getServer().getPluginManager().registerEvents(new Wisps(EntityType.WITCH), this);
        getServer().getPluginManager().registerEvents(new Crawlies(EntityType.CAVE_SPIDER), this);

        getServer().getPluginManager().registerEvents(new ChocolateEatListener(), this);
        getServer().getPluginManager().registerEvents(new AttackSkillListener(), this);
        getServer().getPluginManager().registerEvents(new ScentManager(), this);

        SpecieType.getAllSpecies().forEach(species -> {
            if (species == null) return;
            getServer().getPluginManager().registerEvents(species, this);
        });

        if (!(Bukkit.getOnlinePlayers().size() > 0)) return;
        for (Player p : Bukkit.getOnlinePlayers()) {FlorialDatabase.getPlayerData(p.getUniqueId()).thenAccept(playerData -> {
            Florial.getPlayerData().put(p.getUniqueId(), playerData);});
            ThirstManager.thirstRunnable(p);}

        if (!(Cooldown.getCooldownMap("c1") == null)) Cooldown.getCooldownMap("c1").clear();
        if (!(Cooldown.getCooldownMap("c2") == null)) Cooldown.getCooldownMap("c2").clear();
        if (!(Cooldown.getCooldownMap("scent") == null)) Cooldown.getCooldownMap("scent").clear();
        if (Cooldown.getCooldownMap("c1") == null) Cooldown.createCooldown("c1");
        if (Cooldown.getCooldownMap("c2") == null) Cooldown.createCooldown("c2");
        if (Cooldown.getCooldownMap("scent") == null) Cooldown.createCooldown("scent");

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
        manager.registerCommand(new ResetSpeciesCommand());
        manager.registerCommand(new SpeciesCommand());
        manager.registerCommand(new ChangeDNACommand());
        manager.registerCommand(new LeaderboardCommand());
        manager.registerCommand(new ShowScentUICommand());
        manager.registerCommand(new ChangeSkillsCommand());
        manager.registerCommand(new NuzzleCommand());
        manager.registerCommand(new ChocolateerCommand());

    }

    private void initializeDiscord() {
        try {
            CommandClientBuilder builder = new CommandClientBuilder();
            builder.setPrefix("/");
            builder.forceGuildOnly(getConfig().getString("discord.serverid"));
            builder.setOwnerId("349819317589901323");
            builder.setCoOwnerIds("366301720109776899");
            builder.addSlashCommands(new DiscordUwUCommand(), new DiscordMuteCommand());
            builder.setHelpWord(null);
            builder.setActivity(Activity.watching("the RosaCage"));
            CommandClient commandClient = builder.build();
            discordBot = JDABuilder.createDefault(getConfig().getString("discord.token"),
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                    .setActivity(Activity.watching("the rosacage"))
                    .addEventListeners(commandClient)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize the discord bot, did you forget to add the information to the config file?");
        }

        if (getConfig().getString("discord.serverid") == null) {
            throw new RuntimeException("ADD THE DATA YA TURD");
        }
//        discordServer = discordBot.getGuildById(Objects.requireNonNull(getConfig().getString("discord.serverid")));
//        if (discordServer == null) throw new RuntimeException("Could not find discord server from ID, did you forget to add the information to the config file?");
//        discordServer.updateCommands().addCommands(Commands.slash("uwu", "uwu")).queue();

    }

    public PlayerData getPlayerData(Player player) {return playerData.get(player.getUniqueId());}

    public static HashMap<UUID, Integer> getThirst(){return thirst;}
}
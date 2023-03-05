package net.florial.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.ReplaceOptions;
import dev.morphia.query.filters.Filters;
import lombok.Getter;
import lombok.val;
import net.florial.Florial;
import net.florial.models.FilterEntry;
import net.florial.models.PlayerData;
import net.florial.utils.GeneralUtils;
import net.kyori.adventure.text.Component;
import org.bson.UuidRepresentation;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static net.florial.models.PlayerData.getFieldValue;

public class FlorialDatabase {

    @Getter
    private static Datastore datastore;

    @Getter
    private static MongoClient mongo;

    @Getter
    private static MongoDatabase database;

    public static void initializeDatabase() {
        if (Florial.getInstance().getConfig().getString("mongo.uri") == null || Florial.getInstance().getConfig().getString("mongo.database") == null) {
            Bukkit.getLogger().severe("Could not connect to the database, you forgot the enter stuff in the config you twat.");
            return;
        }

        // mongo = MongoClients.create(Florial.getInstance().getConfig().getString("mongo.uri"));
        val settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(Florial.getInstance().getConfig().getString("mongo.uri")))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();

        mongo = MongoClients.create(settings);
        database = mongo.getDatabase(Florial.getInstance().getConfig().getString("mongo.database"));
        datastore = Morphia.createDatastore(mongo, Florial.getInstance().getConfig().getString("mongo.database"));
        datastore.getMapper().map(PlayerData.class);
        datastore.getMapper().map(FilterEntry.class);
        datastore.ensureIndexes();
    }

    /**
     * Returns the first found version of PlayerData from the database for the given UUID
     *
     * @param uuid The UUID of the player you want to search
     * @return the first found entry as a PlayerData object
     *
     */
    public static CompletableFuture<PlayerData> getPlayerData(UUID uuid) {
        CompletableFuture<PlayerData> future = new CompletableFuture<>();
        GeneralUtils.runAsync(new BukkitRunnable() {

            @Override
            public void run() {
                val temp = datastore.find(PlayerData.class).filter(Filters.eq("UUID", uuid.toString()));
                Bukkit.broadcast(Component.text(temp.stream().count()));
                future.complete(temp.stream().findFirst().orElse(new PlayerData(uuid.toString())));
            }
        });
        return future;
    }

    /**
     * Returns the first found version of PlayerData from the database for the given UUID
     * @param player the player you want to get the PlayerData of
     * @return a completable future of the first found PlayerData
     */
    public static CompletableFuture<PlayerData> getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    /**
     * Creates or overwrites a new copy of PlayerData
     * @param data
     */

    public static void createNewPlayerDataAsync(PlayerData data) {
        GeneralUtils.runAsync(new BukkitRunnable() {
            @Override
            public void run() {
                datastore.replace(data, new ReplaceOptions().upsert(true));
            }
        });
    }

    public static CompletableFuture<List<String>> sortDataByField(String field, boolean descending, int limit) {

        CompletableFuture<List<String>> future = new CompletableFuture<>();

        GeneralUtils.runAsync(new BukkitRunnable() {
            @Override
            public void run() {
                List<PlayerData> playerList = new ArrayList<>();
                datastore.find(PlayerData.class)
                        .filter(Filters.gte(field, 0))
                        .iterator()
                        .forEachRemaining(playerList::add);

                playerList.sort((PlayerData p1, PlayerData p2) -> {
                    if (descending) return getFieldValue(p2, field) - getFieldValue(p1, field);
                    else return getFieldValue(p1, field) - getFieldValue(p2, field);
                });

                List<String> sortedList = playerList.subList(0, limit)
                        .stream()
                        .map(playerData -> {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerData.getUUID()));
                            return offlinePlayer.getName() + ": " + getFieldValue(playerData, field);
                        })
                        .collect(Collectors.toList());

                future.complete(sortedList);
            }

        });
        return future;

    }

    public static CompletableFuture<List<String>> sortDataByField(String field, boolean descending) {
        return sortDataByField(field, descending, 10);
    }
    public static void fetchBoard(String field, boolean descending, Consumer<List<String>> callback){
        sortDataByField(field, descending, 10).thenAccept(callback);
    }

    public static void createNewPlayerData(PlayerData data) {
        datastore.replace(data, new ReplaceOptions().upsert(true));
    }

    public static void closeConnection() {
        mongo.close();
    }
}


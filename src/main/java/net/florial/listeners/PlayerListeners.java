package net.florial.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.mongodb.client.model.Filters;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.santio.utils.bukkit.impl.MessageUtils;
import net.florial.features.thirst.ThirstManager;
import net.florial.Florial;
import net.florial.database.FlorialDatabase;
import net.florial.models.PlayerData;
import net.florial.utils.CC;
import net.florial.utils.Message;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerListeners implements Listener {

    private static final ThirstManager ThirstManager = new ThirstManager();
    private static final Florial florial = Florial.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID u = p.getUniqueId();

        FlorialDatabase.getPlayerData(p).thenAccept(playerData -> {
            Florial.getPlayerData().put(u, playerData);
            Florial.getPlayerData().get(u).refresh();
            new Message("&a[MONGO] &fLoaded your player data successfully!").showOnHover(playerData.toString()).send(p);
        });
        ThirstManager.thirstRunnable(p);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData data = Florial.getPlayerData().get(event.getPlayer().getUniqueId());
        data.save(true);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String prefix = Florial.getPlayerData().get(event.getPlayer().getUniqueId()).getPrefix();
        if (Objects.equals(prefix, "")) {
            try {
                prefix = Objects.requireNonNull(Florial.getInstance().getLpapi().getUserManager().getUser(event.getPlayer().getUniqueId())).getCachedData().getMetaData().getPrefix();
            } catch (NullPointerException e) {
                prefix = "";
            }
        }
        event.setCancelled(true);
        Bukkit.broadcast(Component.text(CC.translate("&7" + prefix + " " + event.getPlayer().getName() + ": " + event.message())));
    }
}

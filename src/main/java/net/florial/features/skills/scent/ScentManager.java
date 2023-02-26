package net.florial.features.skills.scent;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.florial.utils.GetTarget;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class ScentManager implements Listener {

    private static final ScentUI ScentUI = new ScentUI();

    private static final GetTarget GetTarget = new GetTarget();

    @EventHandler
    public void scentListener(PlayerInteractEvent e) {

        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_AIR) return;

        if (e.getItem() == null || (!NBTEditor.contains(e.getItem(), "CustomModelData", 1))) return;

        Player p = e.getPlayer();

        switch (e.getAction()) {
            case LEFT_CLICK_AIR -> ScentUI.scentUI(p);
            case RIGHT_CLICK_AIR -> handleRightClick(p);
            default -> {}
        }

        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1, 1);
    }

    private static void handleRightClick(Player player) {
        LivingEntity entity = GetTarget.of(player);
        if (entity == null) return;

        if (entity instanceof Player target) {
            player.sendMessage("Armor: " + Arrays.toString(target.getInventory().getArmorContents()));
        } else {
            player.sendMessage("" + entity.getHealth());
        }
    }

    @EventHandler
    public void scentProhibitClick(InventoryClickEvent e) {
        if (e.getSlot() != 8 || e.getWhoClicked().getGameMode() != GameMode.SURVIVAL) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void scentProhibiterDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() != Material.PAPER || (!NBTEditor.contains(e.getItemDrop().getItemStack(), "CustomModelData", 1))) return;
        e.setCancelled(true);
    }
}

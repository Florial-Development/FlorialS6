package net.florial.features.skills.scent;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.florial.utils.GetTarget;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class ScentListener implements Listener {

    private static final ScentUI ScentUI = new ScentUI();

    private static final GetTarget GetTarget = new GetTarget();

    @EventHandler
    public void scentListener(PlayerInteractEvent e) {

        if (!(e.getAction() == Action.LEFT_CLICK_AIR) && (!(e.getAction() == Action.RIGHT_CLICK_AIR))) return;
        if (e.getItem() == null || (!NBTEditor.contains(e.getItem(), "CustomModelData", 1))) return;

        Player p = e.getPlayer();

        if (e.getAction().isLeftClick()) {

            ScentUI.scentUI(p);

        } else if (e.getAction().isRightClick()) {

            LivingEntity ent = GetTarget.of(p);

            if (ent == null) return;

            if (ent instanceof Player) {
                Player target = (Player) ent;
                p.sendMessage("Armor: " + Arrays.toString(target.getInventory().getArmorContents()));
            } else {
                p.sendMessage("" + ent.getHealth());
            }

        }
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1, 1);



    }
}

package net.florial.features.chocolates;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.florial.Florial;
import net.florial.models.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ChocolateEatListener implements Listener {


    @EventHandler
    public void eatChocolate(PlayerItemConsumeEvent e) {

        if (!NBTEditor.contains(e.getItem(), "Chocolate")) return;

        Player p = e.getPlayer();
        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        data.setDnaXP(data.getDnaXP() + 250);
        p.playSound(p.getLocation(), Sound.BLOCK_MUD_BREAK, 1, 4);
        p.playSound(p.getLocation(), Sound.BLOCK_SCAFFOLDING_BREAK, 1, 2);

    }
}

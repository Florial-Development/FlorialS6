package net.florial.listeners;

import net.florial.features.thirst.HydrateEvent;
import net.florial.features.thirst.ThirstManager;
import net.florial.utils.Eyes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ThirstListener  implements Listener {


    @EventHandler
    private void OnDrink(PlayerItemConsumeEvent event) {
        ItemStack i = event.getItem();
        Player p = event.getPlayer();

        if (i.getType() != Material.POTION) return;

        HydrateEvent e = new HydrateEvent(
                p,
                i,
                ThirstManager.getThirst(p)

        );

        Bukkit.getPluginManager().callEvent(e);
    }

    @EventHandler
    private void OnDrink(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();

        if (Eyes.eyes(p, Material.WATER, 5) == false || i.getType() != Material.AIR) return;

        HydrateEvent e = new HydrateEvent(
                p,
                i,
                ThirstManager.getThirst(p)

        );

        Bukkit.getPluginManager().callEvent(e);
    }
}

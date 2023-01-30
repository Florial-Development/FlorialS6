package net.florial.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MaterialDetector {

    public int detectMaterial(Player player, Material mat, int radius) {
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        int amount = 0;

        for (int x = playerX - radius; x <= playerX + radius; x++) {
            for (int y = playerY - radius; y <= playerY + radius; y++) {
                for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                    Location blockLocation = new Location(player.getWorld(), x, y, z);
                    if (player.getLocation().distance(blockLocation) <= radius) {
                        Block block = player.getWorld().getBlockAt(blockLocation);
                        if (block.getType() == mat) amount++;
                    }
                }
            }
        }
        return amount;
    }




}

package net.florial.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {



    public static ItemStack MakeItem(ItemStack i, String name, String lore, Boolean shiny) {
        ItemMeta meta = i.getItemMeta();
        String thename = CC.translate(name);
        List<String> lore1 = new ArrayList<>();

        String[] thelore =  lore.split("\n");

        for (String m : thelore) {lore1.add(CC.translate(m));}

        meta.setLore(lore1);
        meta.setDisplayName(thename);
        i.setItemMeta(meta);
        if (shiny) i.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        return i;
    }
}

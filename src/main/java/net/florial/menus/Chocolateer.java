package net.florial.menus;


import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.rysefoxx.inventory.plugin.content.*;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.utils.CC;
import net.florial.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Chocolateer {

    //test

    public void chocolaterMenu(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        ItemStack themap = new ItemStack(Material.MAP);
                        themap = NBTEditor.set( themap, 1010, "CustomModelData");
                        ItemStack filling = CustomItem.MakeItem(themap, "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ MAKE FILLING\n #ffa2c4&l︳ REQUIREMENTS:\n #ffa2c4&l︳ • x32#ffa2c4 Milk\n #ffa2c4&l︳ • x32#ffa2c4 Eggs\n #ffa2c4&l︳ • x64#ffa2c4 Sugar\n #ffa2c4&l︳ • x16#ffa2c4 Sweet Berries\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false);

                        contents.set(List.of(27,28,29), IntelligentItem.of(filling, event -> {
                            chocolateering(List.of(new ItemStack(Material.LEATHER, 15),
                                    new ItemStack(Material.LEATHER_BOOTS, 2),
                                    new ItemStack(Material.LEATHER_CHESTPLATE, 2)),
                                    player, new ItemStack(Material.BLUE_ICE));
                        }));
                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }


    private void chocolateering(List<ItemStack> necessities, Player p, ItemStack output){

        List<ItemStack> missing = new ArrayList<>();

        for (ItemStack i : necessities){if (!p.getInventory().containsAtLeast(i, i.getAmount())) missing.add(i);}

        if (missing.size() > 0){

            p.sendMessage(" You don't have the required items... : " + missing);

        }else{

            p.getInventory().addItem(output);
            p.getInventory().removeItem(necessities.toArray(new ItemStack[necessities.size()]));
        }

    }

}

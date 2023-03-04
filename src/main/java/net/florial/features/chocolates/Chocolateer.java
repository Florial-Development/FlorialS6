package net.florial.features.chocolates;


import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.utils.CC;
import net.florial.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chocolateer {

    public void chocolaterMenu(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        List<ItemStack> entries = Stream.of(CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ MAKE MALT\n #ffa2c4&l︳ REQUIREMENTS:\n #ffa2c4&l︳ • x5#ffa2c4 Milk\n #ffa2c4&l︳ • x32#ffa2c4 Cocoa Beans\n #ffa2c4&l︳ • x64#ffa2c4 Sugar\n #ffa2c4&l︳ • x32#ffa2c4 Eggs\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false), CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ MAKE CHOCOLATE\n #ffa2c4&l︳ REQUIREMENTS:\n #ffa2c4&l︳ • x1#ffa2c4 Filling\n #ffa2c4&l︳ • x1#ffa2c4 Malt\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false),
                                CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ MAKE FILLING\n #ffa2c4&l︳ REQUIREMENTS:\n #ffa2c4&l︳ • x5#ffa2c4 Milk\n #ffa2c4&l︳ • x32#ffa2c4 Eggs\n #ffa2c4&l︳ • x64#ffa2c4 Sugar\n #ffa2c4&l︳ • x16#ffa2c4 Sweet Berries\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false)).map(i -> NBTEditor.set(i, 1010, "CustomModelData"))
                                .collect(Collectors.toList());

                        final ItemStack output = NBTEditor.set(CustomItem.MakeItem(new ItemStack(Material.COOKIE), "#6A3A2F&lChocolate [EAT]", "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑\n #ffa2c4&l︳ Eat it and get DNA XP.\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false), 1, "Chocolate");

                        contents.set(List.of(27,28,29), IntelligentItem.of(entries.get(2), event -> chocolateering(List.of(new ItemStack(Material.MILK_BUCKET, 5),
                                                new ItemStack(Material.EGG, 32),
                                                new ItemStack(Material.SUGAR, 64),
                                                new ItemStack(Material.SWEET_BERRIES, 16)),
                                        player, CustomItem.MakeItem(new ItemStack(Material.MUSIC_DISC_11), "#FAE4DF&lFilling", "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑\n #ffa2c4&l︳ Use this to Make Chocolate.\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false))));

                        contents.set(List.of(33,34,35), IntelligentItem.of(entries.get(0), event -> chocolateering(List.of(new ItemStack(Material.MILK_BUCKET, 5),
                                                new ItemStack(Material.EGG, 32),
                                                new ItemStack(Material.SUGAR, 64),
                                                new ItemStack(Material.COCOA_BEANS, 32)),
                                        player, CustomItem.MakeItem(new ItemStack(Material.MUSIC_DISC_WAIT), "#6A3A2F&lChocolate Malt", "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑\n #ffa2c4&l︳ Use this to Make Chocolate.\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false))));

                        contents.set(List.of(39,40,41), IntelligentItem.of(entries.get(1), event -> chocolateering(List.of(new ItemStack(Material.MUSIC_DISC_11),
                                        new ItemStack(Material.MUSIC_DISC_WAIT)),
                                        player, output)));
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
            p.playSound(p.getLocation(), Sound.BLOCK_MUD_BREAK, 1, 3);
            p.getInventory().removeItem(necessities.toArray(new ItemStack[necessities.size()]));

            p.setFoodLevel(p.getFoodLevel() - 2);
        }

    }

}

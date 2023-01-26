package net.florial.features.skills.scent;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.utils.CC;
import net.florial.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrackingUI {

    // work in progress & test

    public void trackingUI(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七\uE250"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        List<ItemStack> entries = Stream.of(CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ Animals\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false), CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ Ores\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false)).map(i -> NBTEditor.set(i, 1010, "CustomModelData"))
                                .collect(Collectors.toList());


                        contents.set(List.of(27), IntelligentItem.of(entries.get(0), event -> p.sendMessage("test")));
                        contents.set(List.of(28), IntelligentItem.of(entries.get(0), event -> p.sendMessage("test2")));
                        contents.set(List.of(29), IntelligentItem.of(entries.get(0), event -> p.sendMessage("test3")));
                        contents.set(List.of(30), IntelligentItem.of(entries.get(0), event -> p.sendMessage("test4")));
                        contents.set(List.of(31), IntelligentItem.of(entries.get(0), event -> p.sendMessage("test5")));
                        contents.set(List.of(32), IntelligentItem.of(entries.get(0), event -> p.sendMessage("test6")));



                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }
}

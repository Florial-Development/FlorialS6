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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public class ScentUI {

    private static final AnimalTrackingUI trackingUI = new AnimalTrackingUI();
    private static final OreTrackingUI oretrackingUI = new OreTrackingUI();

    private static final Florial florial = Florial.getInstance();


    public void scentUI(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七\uE249"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        List<ItemStack> entries = Stream.of(CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ Animals\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false), CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", " #ff79a1&l︳ Ores\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙", false)).map(i -> NBTEditor.set(i, 1010, "CustomModelData"))
                                .toList();


                        contents.set(List.of(37, 38, 28, 29), IntelligentItem.of(entries.get(0), event -> loadMenu(p, 1)));

                        contents.set(List.of(32, 33, 41, 42, 43, 34), IntelligentItem.of(entries.get(1), event -> loadMenu(p, 2)));


                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }

    private static void loadMenu(Player p, int type) {
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_STEP, 1, 1);
        p.closeInventory();
        switch(type) {
        case 1:
            trackingUI.trackingUI(p);
            break;
        case 2:
            oretrackingUI.trackingUIOre(p);
            break;
        }
    }
}


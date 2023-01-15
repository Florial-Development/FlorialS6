package net.florial.menus;


import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.rysefoxx.inventory.plugin.content.*;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Chocolateer {

    public void chocolaterMenu(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        ItemStack themap = new ItemStack(Material.MAP);
                        themap = NBTEditor.set( themap, 1010, "CustomModelData");
                        contents.set(27, IntelligentItem.of(themap, event -> {
                            player.sendMessage("test");
                        }));
                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }

}

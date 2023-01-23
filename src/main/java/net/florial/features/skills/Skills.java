package net.florial.features.skills;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.utils.CC;
import net.florial.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Skills {

    // work in progress

    public void skillMenu(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七\uE248"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

                        List<String> descriptions;


                        try {
                            descriptions = new ArrayList<>(data.getSpecies().descriptions());
                        } catch (NullPointerException e) {
                            descriptions = new ArrayList<>(Collections.nCopies(6, "This species does not have a unique Skill"));
                        }

                        List<ItemStack> entries = Stream.of(CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                "SCENT", "" + data.getSkills().get(Skill.SCENT), "#ffa2c4 25 #9ECEFF&lD#FFC2DA&lN#9ECEFF&lA",
                                                "lower cooldown : stronger skill")), false)).map(i -> NBTEditor.set(i, 1010, "CustomModelData"))
                                .collect(Collectors.toList());


                        contents.set(List.of(36,37), IntelligentItem.of(entries.get(0), event -> skill(p)));

                        contents.set(List.of(18,19), IntelligentItem.of(entries.get(0), event -> skill(p)));

                        contents.set(List.of(20,21), IntelligentItem.of(entries.get(0), event -> skill(p)));

                        contents.set(List.of(22), IntelligentItem.of(entries.get(0), event -> skill(p)));

                        contents.set(List.of(23,24), IntelligentItem.of(entries.get(0), event -> skill(p)));

                        contents.set(List.of(25,26), IntelligentItem.of(entries.get(0), event -> skill(p)));

                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }

    public void skill(Player p){

        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_STEP, 1, 1);


    }

    private String format(List<String> iterations){
        return "  #ff79a1&l︳ " + iterations.get(0) +
            " SKILL\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙\n #ffa2c4&l︳ • YOUR LEVEL: #ffa2c4 "
            + iterations.get(1) + "\n #ff79a1&l︳  INCREASE BY:\n #ffa2c4&l︳ •#ffa2c4 "
            + iterations.get(2) + "\n #ffa2c4&l︳ • [CLICK HERE]\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙\n #ffa2c4&l︳#ff79a1&l INFORMATION\n #ffa2c4&l︳ • LEVELS BRING:\n #ffa2c4&l︳#ffa2c4 "
            + iterations.get(3) + "\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙";}
}

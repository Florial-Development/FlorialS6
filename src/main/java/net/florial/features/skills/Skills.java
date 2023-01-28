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
                                                "lower cooldown : stronger skill")), false),
                                        CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "STRENGTH", "" + data.getSkills().get(Skill.STRENGTH), "#ffa2c4 30 #9ECEFF&lD#FFC2DA&lN#9ECEFF&lA",
                                                "Increase in Strength")), false),
                                        CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "RESISTANCE", "" + data.getSkills().get(Skill.RESISTANCE), "#ffa2c4 50 #9ECEFF&lD#FFC2DA&lN#9ECEFF&lA",
                                                "Increase in Resistance")), false),
                                        CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "SURVIVAL", "" + data.getSkills().get(Skill.SURVIVAL), "#ffa2c4 25 #9ECEFF&lD#FFC2DA&lN#9ECEFF&lA",
                                                "More health : higher dmg")), false),
                                        CustomItem.MakeItem(new ItemStack(Material.MAP), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "" + descriptions.get(0), "" + data.getSkills().get(Skill.SPECIFIC), "#ffa2c4 25 #9ECEFF&lD#FFC2DA&lN#9ECEFF&lA",
                                                "" + descriptions.get(1))), false)).map(i -> NBTEditor.set(i, 1010, "CustomModelData"))
                                .collect(Collectors.toList());

                       // contents.set(List.of(36,37), IntelligentItem.of(entries.get(0), event -> skill(p,0,data,Skill.SPECIFIC)));

                        contents.set(List.of(18,19), IntelligentItem.of(entries.get(0), event -> skill(p,25,data,Skill.SCENT)));

                        contents.set(List.of(20,21), IntelligentItem.of(entries.get(1), event -> skill(p,30,data,Skill.STRENGTH)));

                        contents.set(List.of(22), IntelligentItem.of(entries.get(2), event -> skill(p,50,data,Skill.RESISTANCE)));

                        contents.set(List.of(23,24), IntelligentItem.of(entries.get(3), event -> p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_STEP, 1, 1)));

                        contents.set(List.of(25,26), IntelligentItem.of(entries.get(4), event -> skill(p,25,data,Skill.SPECIFIC)));

                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }

    private static void skill(Player p, int i, PlayerData data, Skill skill){

        int dna = data.getDna();

        if (dna >= i) {

            data.setDna(dna-i);

            data.getSkills().put(skill, data.getSkills().get(skill) + 1);

            p.sendMessage("Successfully upgraded!");

        } else {
            p.sendMessage("Not enough DNA");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);

        }

        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_STEP, 1, 1);


    }

    private static String format(List<String> iterations){
        return "  #ff79a1&l︳ " + iterations.get(0) +
            " SKILL\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙\n #ffa2c4&l︳ • YOUR LEVEL: #ffa2c4 "
            + iterations.get(1) + "\n #ff79a1&l︳  INCREASE BY:\n #ffa2c4&l︳ •#ffa2c4 "
            + iterations.get(2) + "\n #ffa2c4&l︳ • [CLICK HERE]\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙\n #ffa2c4&l︳#ff79a1&l INFORMATION\n #ffa2c4&l︳ • LEVELS BRING:\n #ffa2c4&l︳#ffa2c4 "
            + iterations.get(3) + "\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙";}
}

package net.florial.features.skills.scent;

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.features.skills.Skill;
import net.florial.models.PlayerData;
import net.florial.utils.CC;
import net.florial.utils.CustomItem;
import net.florial.utils.MaterialDetector;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OreTrackingUI {

    //wip

    private static final MaterialDetector MaterialDetector = new MaterialDetector();

    public void trackingUIOre(Player p) {

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七\uE250"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

                        int scent = data.getSkills().get(Skill.SCENT);

                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1, 1);

                        List<ItemStack> entries = Stream.of(CustomItem.MakeItem(new ItemStack(Material.COAL_ORE), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "COAL", "1"), scent), false),
                                        CustomItem.MakeItem(new ItemStack(Material.IRON_ORE), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "IRON", "2"), scent), false),
                                        CustomItem.MakeItem(new ItemStack(Material.EMERALD_ORE), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "EMERALD", "3"), scent), false),
                                        CustomItem.MakeItem(new ItemStack(Material.LAPIS_ORE), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "LAPIS", "4"), scent), false),
                                        CustomItem.MakeItem(new ItemStack(Material.DIAMOND_ORE), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                "DIAMOND", "5"), scent), false))
                                .collect(Collectors.toList());

                        contents.set(List.of(27), IntelligentItem.of(entries.get(0), event -> oreLocate(Material.COAL_ORE, p, scent, 0)));
                        contents.set(List.of(28), IntelligentItem.of(entries.get(1), event -> oreLocate(Material.IRON_ORE, p, scent, 2)));
                        contents.set(List.of(29), IntelligentItem.of(entries.get(2), event -> oreLocate(Material.EMERALD_ORE, p, scent, 3)));
                        contents.set(List.of(30), IntelligentItem.of(entries.get(3), event -> oreLocate(Material.LAPIS_LAZULI, p, scent, 4)));
                        contents.set(List.of(31), IntelligentItem.of(entries.get(4), event -> oreLocate(Material.DIAMOND_ORE, p, scent, 5)));


                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }

    private static void oreLocate(Material mat, Player p, int scent, int required) {
        Sound sound;

        if (scent >= required) {

            int nearby = MaterialDetector.detectMaterial(p, mat, scent*5);

            p.sendMessage("Amount Nearby: " + nearby);
            sound = Sound.ENTITY_PLAYER_BREATH;

        } else {
            sound = Sound.BLOCK_NOTE_BLOCK_BASS;
            p.sendMessage("You need Scent level " +  required + " for this, but you only have " + scent);
        }
        p.playSound(p.getLocation(), sound, 1, 1);

    }


    private static String format(List<String> iterations, int scent){
        scent = 60-(scent*10);
        return "  #ff79a1&l︳ " + iterations.get(0) +
                "\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙\n #ffa2c4&l︳ • REQUIRES: #ffa2c4\n #ffa2c4&l︳ • SCENT LVL: #ffa2c4 "
                + iterations.get(1) + "\n #ffa2c4&l︳ • COOLDOWN:#ffa2c4 "
                + scent + "\n #ffa2c4&l︳ • [CLICK HERE]" +
                "\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙";}

}


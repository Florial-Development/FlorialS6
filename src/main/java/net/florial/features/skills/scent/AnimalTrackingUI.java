package net.florial.features.skills.scent;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import net.florial.Florial;
import net.florial.features.skills.Skill;
import net.florial.models.PlayerData;
import net.florial.utils.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalTrackingUI {

    private static final Random RANDOM = new Random();

    public void trackingUI(Player p) {

        final GetCustomSkull GetCustomSkull = new GetCustomSkull();

        RyseInventory.builder()
                .title(CC.translate("&f七七七七七七七七七七七七七七七七\uE250"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

                        int scent = data.getSkills().get(Skill.SCENT);

                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1, 1);

                        List<ItemStack> entries = Stream.of(CustomItem.MakeItem(GetCustomSkull.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI3MmVmYmE5NGQzZWM3OWQyM2M3ODkyNjk2NzQ5MTEyNWM5YTEwM2VlZDAwZDM2MDJlOTg0MTk1NTBlNTViYyJ9fX0"), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                    "COW", "Plains, Snowy", "1"), scent), false),
                                            CustomItem.MakeItem(GetCustomSkull.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I0NGZiOGEwODA1N2U0YTcyNzhhNmM1YWEyY2I2OTJmMmU3Y2ZlYTk2MGM2OGZjMGQ0ZDJlMjZlODhkZDM1OSJ9fX0"), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                    "SHEEP", "Plains, Snowy", "2"), scent), false),
                                            CustomItem.MakeItem(GetCustomSkull.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjVkYzhjNDkxZjE3ZTgyZTNlZTA3NWYwOWZiZGVhOTdlZGY2ZDNlN2RiMWU0YmI4YjIwMDFhODBkNzlhNWIxZiJ9fX0"), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                    "WILD CHICKEN", "Plains, Forests, Jungle", "3"), scent), false),
                                            CustomItem.MakeItem(GetCustomSkull.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M0MzQ5ZmU5OTAyZGQ3NmMxMzYxZjhkNmExZjc5YmZmNmY0MzNmM2I3YjE4YTQ3MDU4ZjBhYTE2YjkwNTNmIn19fQ"), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                    "HARE", "Plains, Deserts, Forests, Snowy", "4"), scent), false),
                                            CustomItem.MakeItem(GetCustomSkull.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzljMjI4ZDc4ZTM5NTUwMGJjNTRlOGU0NWU5ODExYWY4YzllYTU4MDQ1ZDcyZmE2ZjA5OTIxZGE1N2UwNTViNCJ9fX0"), "#ff79a1&l ┍━━━━━━━━━━━━━━━━━━┑", format(List.of(
                                                    "HOG", "Forests, Jungle", "5"), scent), false))
                                    .collect(Collectors.toList());

                        contents.set(List.of(27), IntelligentItem.of(entries.get(0), event -> trackdown(EntityType.COW, List.of(Biome.PLAINS,Biome.SNOWY_PLAINS,
                                        Biome.SUNFLOWER_PLAINS,Biome.SNOWY_TAIGA,Biome.SNOWY_SLOPES), p, scent, 0)));
                        contents.set(List.of(28), IntelligentItem.of(entries.get(1), event -> trackdown(EntityType.SHEEP, List.of(Biome.PLAINS,Biome.SNOWY_PLAINS,
                                Biome.SUNFLOWER_PLAINS,Biome.SNOWY_TAIGA,Biome.SNOWY_SLOPES), p, scent, 2)));
                        contents.set(List.of(29), IntelligentItem.of(entries.get(2), event -> trackdown(EntityType.CHICKEN, List.of(Biome.PLAINS,Biome.FLOWER_FOREST,
                                Biome.SUNFLOWER_PLAINS,Biome.BIRCH_FOREST,Biome.DARK_FOREST,Biome.JUNGLE,Biome.SPARSE_JUNGLE,Biome.BAMBOO_JUNGLE,Biome.FOREST), p, scent, 3)));
                        contents.set(List.of(30), IntelligentItem.of(entries.get(3), event -> trackdown(EntityType.RABBIT, List.of(Biome.PLAINS,Biome.FLOWER_FOREST,
                                Biome.SUNFLOWER_PLAINS,Biome.BIRCH_FOREST,Biome.DARK_FOREST,Biome.JUNGLE,Biome.SPARSE_JUNGLE,Biome.BAMBOO_JUNGLE,Biome.FOREST,Biome.DESERT,Biome.SNOWY_PLAINS,
                        Biome.SNOWY_SLOPES,Biome.SNOWY_TAIGA), p, scent, 4)));
                        contents.set(List.of(31), IntelligentItem.of(entries.get(4), event -> trackdown(EntityType.PIG, List.of(Biome.FLOWER_FOREST,
                                Biome.BIRCH_FOREST,Biome.DARK_FOREST,Biome.JUNGLE,Biome.SPARSE_JUNGLE,Biome.BAMBOO_JUNGLE,Biome.FOREST,Biome.DESERT), p, scent, 5)));

                    }
                })
                .build(Florial.getInstance())
                .openAll();

    }

    private static void trackdown(EntityType e, List<Biome> acceptable, Player p, int scent, int required){

        Location loc = p.getLocation();
        World w = loc.getWorld();

        if (scent < required) {
            p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage("You need Scent level " + required + " for this, but you only have " + scent);
            return;
        }

        if (!acceptable.contains(loc.getBlock().getBiome())){
            p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage("You are not in the correct biome.");
            return;
        }
        int chance = 20 + (scent * 10);
        if (GetChance.getChance(chance)) {
            p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            return;
        }

        double x = loc.getX() + (RANDOM.nextDouble() * 20) - 10;
        double z = loc.getZ() + (RANDOM.nextDouble() * 20) - 10;

        LivingEntity them = (LivingEntity) MobSpawn.spawnMob(e, w, new Location(w, x, loc.getY(), z));

        PotionEffect resist = new PotionEffect(PotionEffectType.SPEED, 1000000, 2, false, false, true);
        PotionEffect speed = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 3, false, false, true);
        PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, 2400, 1, false, false, true);

        for (PotionEffect effect : List.of(resist, speed, glow)) {them.addPotionEffect(effect);}

        p.playSound(loc, Sound.ENTITY_PLAYER_BREATH, 1, 1);


    }

    private static String format(List<String> iterations, int scent){
        int chance = 20+(scent*10);
        int cooldown = 60-(scent*10);
        return "  #ff79a1&l︳ " + iterations.get(0) +
                "\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙\n #ffa2c4&l︳ • REQUIRES: #ffa2c4\n #ffa2c4︳ " + iterations.get(1) + "\n #ffa2c4&l︳ • CHANCE: #ffa2c4 " + chance
                + "\n #ffa2c4&l︳ • SCENT LVL: #ffa2c4 "
                + iterations.get(2) + "\n #ffa2c4&l︳ • COOLDOWN: #ffa2c4 " + cooldown + "\n #ffa2c4&l︳ • [CLICK HERE]" +
                "\n #ff79a1&l┕━━━━━━━━━━━━━━━━━━┙";}

}

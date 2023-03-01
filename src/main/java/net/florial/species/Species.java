package net.florial.species;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.species.disguises.Morph;
import net.florial.species.events.impl.SpeciesSwitchEvent;
import net.florial.utils.GeneralUtils;
import net.florial.utils.NumberGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class Species implements Listener {

    private static final Florial florial = Florial.getInstance();
    private static final Map<Material, Integer> fillingValues = Map.ofEntries(
            Map.entry(Material.CHICKEN, 20),
            Map.entry(Material.PORKCHOP, 15),
            Map.entry(Material.BEEF, 20),
            Map.entry(Material.SWEET_BERRIES, 10),
            Map.entry(Material.COD, 13),
            Map.entry(Material.SALMON, 13),
            Map.entry(Material.MUTTON, 20)
    );



    String name;
    int id;
    double maxHealth;

    boolean canSmell;
    
    protected Species(String name, int id, double maxHealth, boolean canSmell) {
        this.name = name;
        this.id = id;
        this.maxHealth = maxHealth;
        this.canSmell = canSmell;

        Bukkit.getPluginManager().registerEvents(this, Florial.getInstance());


    }
    
    public Set<PotionEffect> effects() {
        return new HashSet<>();
    }

    public Set<Material> diet() {
        return new HashSet<>();
    }

    public Set<String> descriptions() {
        return new HashSet<>();
    }

    @EventHandler
    public void whenISwitch(SpeciesSwitchEvent event) {

        PlayerData data = event.getPlayerData();

        data.setSpecieId(event.getSpecie().getId());

        Morph.activate(event.getPlayer(), "" + event.getSpecie(), "", false);

        data.getSpecies().effects().forEach(effect -> data.getPlayer().removePotionEffect(effect.getType()));

        GeneralUtils.runAsync(new BukkitRunnable() {@Override public void run() {Bukkit.getScheduler().runTaskLater(florial, data::refresh, 40L);}});

    }

    @EventHandler
    public void whenIEat(PlayerItemConsumeEvent event) {

        Player p = event.getPlayer();

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        if (data.getSpecies() != this) return;

        if (data.getSpecies().diet() == null) return;

        Material mat = event.getItem().getType();

        event.setCancelled(true);

        if (this.diet().contains(mat)) {
            p.setFoodLevel(p.getFoodLevel() + fillingValues.get(mat));
            if (!(p.getSaturation() >= 20)) p.setSaturation(p.getSaturation() + fillingValues.get(mat)/2);
        } else {
            p.setFoodLevel(p.getFoodLevel() + 1);
        }

    }


}

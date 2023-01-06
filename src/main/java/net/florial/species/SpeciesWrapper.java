package net.florial.species;

import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.species.events.impl.SpeciesSwitchEvent;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.UUID;

public class SpeciesWrapper {

    public static void setSpecies(UUID player, SpecieType species) {
        PlayerData data = Florial.getPlayerData().get(player);

        SpeciesSwitchEvent event = new SpeciesSwitchEvent(
            Bukkit.getPlayer(player),
            data,
            data.getSpecieType(),
            species
        );

        if (event.isCancelled()) return;

        data.getSpecies().effects().forEach(effect -> {
            data.getPlayer().removePotionEffect(effect);
        });

        data.setSpecieId(species.getId());
        data.refresh();
    }

    // Method to get the species of a player
    public static Species getSpecies(UUID player) {
        return Florial.getPlayerData().get(player).getSpecies();
    }

}

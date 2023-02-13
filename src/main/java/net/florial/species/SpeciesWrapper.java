package net.florial.species;

import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.species.events.impl.SpeciesSwitchEvent;
import org.bukkit.Bukkit;

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
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return;

        data.setSpecieId(species.getId());

    }

    // Method to get the species of a player
    public static Species getSpecies(UUID player) {
        return Florial.getPlayerData().get(player).getSpecies();
    }

}

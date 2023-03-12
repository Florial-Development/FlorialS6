package net.florial.species;

import net.florial.Florial;
import net.florial.database.FlorialDatabase;
import net.florial.models.PlayerData;
import net.florial.species.disguises.Morph;
import net.florial.species.events.impl.SpeciesSwitchEvent;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SpeciesWrapper {


    public static void setSpecies(UUID player, SpecieType species) {

        final PlayerData[] data = new PlayerData[1];
        try {
            data[0] = Florial.getPlayerData().get(player);
        } catch (Exception e) {
            FlorialDatabase.getPlayerData(player).thenAccept(playerData -> {
                    Florial.getPlayerData().put(player, playerData);
                    data[0] = playerData;
                    data[0].save(true);
                }
            );
        }

        SpeciesSwitchEvent event = new SpeciesSwitchEvent(
            Bukkit.getPlayer(player),
                data[0],
            data[0].getSpecieType(),
            species
        );
        Bukkit.getPluginManager().callEvent(event);


    }

    // Method to get the species of a player
    public static Species getSpecies(UUID player) {
        return Florial.getPlayerData().get(player).getSpecies();
    }

}

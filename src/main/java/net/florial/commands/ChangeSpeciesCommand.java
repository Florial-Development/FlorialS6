package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.species.SpecieType;
import org.bukkit.entity.Player;
import net.florial.species.SpeciesWrapper;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ChangeSpeciesCommand extends BaseCommand {

    @CommandAlias("setspecies")
    public void onInfoPanel(Player p, String species) {
        SpecieType type;

        try { type = SpecieType.valueOf(species.toUpperCase().replace(" ", "_")); }
        catch (Exception e){
            p.sendMessage("§cInvalid species, species are: " + Arrays.stream(SpecieType.values()).map(Enum::name).collect(Collectors.joining(", ")));
            return;
        }

        SpeciesWrapper.setSpecies(p.getUniqueId(), type);
        
        p.sendMessage("Successfully set species to " + species);
    }
}

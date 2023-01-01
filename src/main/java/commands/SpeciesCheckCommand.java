package commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import core.Florial;
import org.bukkit.entity.Player;
import species.speciesinternal.SpeciesWrapper;

import java.sql.SQLException;

public class SpeciesCheckCommand extends BaseCommand {

    public Florial plugin;
    public SpeciesCheckCommand(Florial plugin){this.plugin = plugin;}

    SpeciesWrapper SpeciesWrapper = new SpeciesWrapper();


    //SpeciesWrapper SpeciesWrapper;
    @CommandAlias("myspecies")
    public void onInfoPanel(Player p) throws SQLException {
        //SpeciesWrapper = new SpeciesWrapper(plugin);
         p.sendMessage("your species: " + SpeciesWrapper.getSpecies(p.getUniqueId(), false));
    }
}
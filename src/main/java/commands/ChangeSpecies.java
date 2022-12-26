package commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import core.Florial;
import org.bukkit.entity.Player;
import species.speciesinternal.SpeciesWrapper;

import java.sql.SQLException;

public class ChangeSpecies extends BaseCommand {

    public Florial plugin;
    public ChangeSpecies(Florial plugin){
        this.plugin = plugin;
    }
    SpeciesWrapper SpeciesWrapper = null;

    @CommandAlias("setspecies")
    public void onInfoPanel(Player p, String species) throws SQLException {
        SpeciesWrapper = new SpeciesWrapper(plugin);
        try {
            SpeciesWrapper.setSpecies(p.getUniqueId(), Integer.valueOf(species));

        }catch (SQLException e){
            e.printStackTrace();
        }
        p.sendMessage("Successfully set species to " + species);
    }
}

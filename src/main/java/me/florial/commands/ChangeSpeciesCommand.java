package me.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import org.bukkit.entity.Player;
import me.florial.species.SpeciesWrapper;

import java.sql.SQLException;

public class ChangeSpeciesCommand extends BaseCommand {

    @CommandAlias("setspecies")
    public void onInfoPanel(Player p, String species) throws SQLException {
        try {
            SpeciesWrapper.setSpecies(p.getUniqueId(), Integer.parseInt(species));
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        p.sendMessage("Successfully set species to " + species);
    }
}

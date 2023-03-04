package net.florial.commands.species;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.menus.SpeciesMenu;
import org.bukkit.entity.Player;

public class SpeciesCommand extends BaseCommand {

    private static final SpeciesMenu Species = new SpeciesMenu();

    @CommandAlias("species")
    public void onInfoPanel(Player p) {Species.speciesMenu(p);}
}

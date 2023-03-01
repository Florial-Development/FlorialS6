package net.florial.commands.species;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.menus.Species;
import org.bukkit.entity.Player;

public class SpeciesCommand extends BaseCommand {

    private static final Species Species = new Species();

    @CommandAlias("species")
    public void onInfoPanel(Player p) {Species.speciesMenu(p);}
}

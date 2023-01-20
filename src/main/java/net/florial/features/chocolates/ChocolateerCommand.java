package net.florial.features.chocolates;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.florial.features.chocolates.Chocolateer;
import org.bukkit.entity.Player;

public class ChocolateerCommand extends BaseCommand {

    Chocolateer Chocolateer = new Chocolateer();

    @CommandAlias("chocolateer")
        @Default
        public void onOpenChocolateerMenu(Player player) {
             Chocolateer.chocolaterMenu(player);
        }
}

package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.florial.features.skills.scent.ScentUI;
import org.bukkit.entity.Player;

public class ShowScentUICommand extends BaseCommand {

    ScentUI ScentUI = new ScentUI();

    @CommandAlias("scent")
    @Default
    public void onOpenScentUI(Player player) {
        ScentUI.scentUI(player);
    }
}

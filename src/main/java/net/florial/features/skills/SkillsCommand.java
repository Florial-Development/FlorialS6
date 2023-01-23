package net.florial.features.skills;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

public class SkillsCommand extends BaseCommand {

    Skills Skills = new Skills();

    @CommandAlias("skills")
    @Default
    public void onOpenSkillMenu(Player player) {Skills.skillMenu(player);
    }
}

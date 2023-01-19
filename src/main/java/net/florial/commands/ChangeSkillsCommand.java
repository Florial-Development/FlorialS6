package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.skills.Skill;
import net.florial.species.SpecieType;
import net.florial.species.SpeciesWrapper;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeSkillsCommand extends BaseCommand {

    @CommandAlias("changeskill")
    public void onInfoPanel(Player p, String s, int lvl){

        //test

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        data.getSkills().put(Skill.SCENT, 3);

    }
}

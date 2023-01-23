package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.Florial;
import net.florial.models.PlayerData;
import net.florial.features.skills.Skill;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ChangeSkillsCommand extends BaseCommand {

    @CommandAlias("changeskill")
    public void onInfoPanel(Player p, String s, int lvl){

        //test

        Skill skill;

        try { skill = Skill.valueOf(s.toUpperCase().replace(" ", "_")); }
        catch (Exception e){
            p.sendMessage("§cInvalid skill, skills are: " + Arrays.stream(Skill.values()).map(Enum::name).collect(Collectors.joining(", ")));
            return;
        }

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        data.getSkills().put(skill, lvl);

    }
}

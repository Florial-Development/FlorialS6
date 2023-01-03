package me.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.florial.Florial;
import me.florial.models.PlayerData;
import me.florial.skills.Skill;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeSkillsCommand extends BaseCommand {

    @CommandAlias("changeskill")
    public void onInfoPanel(Player p, String s, int lvl) throws SQLException {

        // make method to change each separate skill coming soon
        //testing purposes
        // inefficient

//        if (s.contains("burrow")) {
//            PlayerData data = Florial.getDatabase().findPlayerStatsbyUUID(p.getUniqueId().toString());
//
//            ArrayList<Skill> original = data.getSkills();
//            ArrayList skillarray = new ArrayList<Skill>();
//            int index = 0;
//            for (int i = 0; i < 5; i++) {
//                index++;
//                Skill skill = Skill.getSkillsId(index);
//                if (index == 1) {
//                    skill.setLevel(lvl);
//                } else {
//                    skill.setLevel(original.get(index - 1).getSkillLevel());
//                }
//                skillarray.add(skill);
//            }
//            data.setSkills(skillarray);
//        }else if (s.contains("scent")){
//                PlayerData data = Florial.getDatabase().findPlayerStatsbyUUID(p.getUniqueId().toString());
//
//                ArrayList<Skill> original = data.getSkills();
//                ArrayList skillarray = new ArrayList<Skill>();
//                int index = 0;
//                for (int i = 0; i < 5; i++){
//                    index++;
//                    Skill skill = Skill.getSkillsId(index);
//                    if(index == 2){
//                        skill.setLevel(lvl);
//                    }else{
//                        skill.setLevel(original.get(index-1).getSkillLevel());
//                    }
//                    skillarray.add(skill);
//                }
//                data.setSkills(skillarray);
//            }
//
    }
}

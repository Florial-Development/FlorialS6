package net.florial.features.skills.attack;

import net.florial.Florial;
import net.florial.features.skills.Skill;
import net.florial.models.PlayerData;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackSkillListener implements Listener {

    @EventHandler
    public void attackskillboost(EntityDamageByEntityEvent e){

        Entity damager = e.getDamager();
        Entity ent = e.getEntity();

        if (!(damager instanceof Player || (!(ent  instanceof LivingEntity)))) return;

        Player p = (Player) damager;
        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        int attackSkill =  data.getSkills().get(Skill.STRENGTH);
        if (attackSkill > 1) ((Damageable) e.getEntity()).damage(2*attackSkill, p);
    }
}

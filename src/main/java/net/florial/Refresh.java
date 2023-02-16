package net.florial;

import net.florial.features.skills.Skill;
import net.florial.features.upgrades.Upgrade;
import net.florial.models.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Refresh {

    /*
    This is a necessary, unique class
    Over the course of a user's lifetime, he may get
    several various upgrades, or may have skills that give buffs.
    Because of how minecraft works, these buffs fade after a restart or on death
    therefore it's our responsibility to load it everytime he dies or rejoins.
    additionally he may get max health enhancements somehow, someway. it is
    therefore our responsibility to load that too in the off-case it is somehow lost
     */

    public void load(Player p, PlayerData data) {

        if (data == null) data = Florial.getPlayerData().get(p.getUniqueId());

        // now additions shall be.. have we anything to add to the max health?
        int additions = 0;
        AtomicReference<Double> maxhealth = new AtomicReference<>(data.getSpecies().getMaxHealth());
        HashMap<Skill, Integer> skills = data.getSkills();
        // shall we get upgrades? if it is null leave it blank! We needn't assign upgrades to players who will never get them, so let that not be null overtime
        HashMap<Upgrade, Boolean> upgrades = data.getUpgrades() != null ? data.getUpgrades() : new HashMap<>();
        int resistance = skills.get(Skill.RESISTANCE);
        int survival = skills.get(Skill.SURVIVAL);

        // let's see if the user has upgraded resistance?
        if (resistance > 1) p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, resistance-2, false, false, true));

        if (skills.get(Skill.STRENGTH) > 4) p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 1, false, false, true));
        //we use this statement to check if survival is 20 or just 5. because at those levels max health INCREASES!

        if (survival > 4) additions = survival >= 20 ? 6 : 4;

        if (survival > 14) p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, 0, false, false, true));

        // let's set their maxhealth now
        p.setMaxHealth(maxhealth.get() + additions);

        // OK! let's begin the Great Upgrade Check, and then just stop this whole code if our player hasn't got an upgrade yet!
        if (upgrades.isEmpty()) return;
        Map<Upgrade, Runnable> upgradeHandlers = new HashMap<>() {{
            put(Upgrade.DOUBLEHEALTH, () -> maxhealth.set(Math.max(maxhealth.get(), 40)));
        }};

        for (Map.Entry<Upgrade, Runnable> entry : upgradeHandlers.entrySet()) {
            Upgrade upgrade = entry.getKey();
            Runnable handler = entry.getValue();
            if (upgrades.get(upgrade)) {
                handler.run();
            }
        }

      //  if (!(upgrades.isEmpty()) && upgrades.get(Upgrade.DOUBLEHEALTH)) maxhealth.set(Math.max(maxhealth.get(), 20));

    }
}

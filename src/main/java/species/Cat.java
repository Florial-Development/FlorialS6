package species;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import species.speciesinternal.Species;
import species.speciesinternal.SpeciesEnum;

import java.util.List;

public class Cat extends Species {

    public Cat() {
        super(List.of(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false)), 16, SpeciesEnum.CAT);
    }


    @Override
    public void performAbility() {

    }

    @Override
    public void onPlayerAttackEntity(Player attacker, Entity victim) {

    }

    @Override
    public void speciesRespawn(PlayerRespawnEvent e) {

    }

}

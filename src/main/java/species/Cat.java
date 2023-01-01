package species;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import species.speciesinternal.Species;

import java.util.List;

public class Cat extends Species {
    /**
     * permEffects = list of effects the species will always have, permanently, despite its level
     * maxHealth = the max health of the species. may change upon levels
     *
     * @param permEffects
     * @param maxhealth
     */
    public Cat(List<PotionEffect> permEffects, int maxhealth) {
        super(List.of(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false)), 16, 1);
    }


    @Override
    public void performAbility() {

    }
}

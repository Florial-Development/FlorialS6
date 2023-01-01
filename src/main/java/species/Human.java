package species;

import org.bukkit.potion.PotionEffect;
import species.speciesinternal.Species;

import java.util.List;

public class Human extends Species {
    /**
     * permEffects = list of effects the species will always have, permanently, despite its level
     * maxHealth = the max health of the species. may change upon levels
     *
     * @param permEffects
     * @param maxhealth
     */
    public Human(List<PotionEffect> permEffects, int maxhealth) {
        super(permEffects, maxhealth);
    }

    @Override
    public void performAbility() {

    }
}

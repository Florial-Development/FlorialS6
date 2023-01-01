package species.speciesinternal;

import mysql.PlayerData;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public abstract class Species implements Listener {

    SpeciesWrapper SpeciesWrapper = new SpeciesWrapper();


    public List<PotionEffect> permEffects;
    public int maxHealth;

    public int species;

    public abstract void performAbility();

    /**
    * permEffects = list of effects the species will always have, permanently, despite its level
     * maxHealth = the max health of the species. may change upon levels
    */


    public Species(List<PotionEffect> permEffects, int maxhealth, int species){
        this.permEffects = permEffects;
        this.maxHealth = maxHealth;
        this.species = species;
    }

    private void refresh(Player p){
        List<PotionEffect> effects = new ArrayList<>(this.permEffects);

        for (PotionEffect effect : effects) {

            p.addPotionEffect(effect);
        }

        p.setMaxHealth(this.maxHealth);
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent e ){
        Player p = e.getPlayer();
        PlayerData data = SpeciesWrapper.getData(p);
        if (data.getSpecies() != (this.species)) return;
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.maxHealth);
    }

}

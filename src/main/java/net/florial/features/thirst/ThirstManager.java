package net.florial.features.thirst;

import net.florial.Florial;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ThirstManager implements Listener {


    @EventHandler
    public void DrinkWater(HydrateEvent event) {
        Player p = event.getPlayer();
        if (event.getThirst() >= 20) return;
        Hydrate(p, 2);
        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 3, 2);
        if (p.hasPotionEffect(PotionEffectType.WITHER)) p.removePotionEffect(PotionEffectType.WITHER);
    }

    @EventHandler
    public void DehydrateSelf(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player p) || (!(event.getEntity().getFoodLevel() > event.getFoodLevel()))) return;
        if (getThirst(p) > 0) {
            deHydrate(p, 1);
        } else {
            if (!p.hasPotionEffect(PotionEffectType.WITHER))
                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,
                        2400, 1, false, false, true));
        }
    }

    public static void updateThirst(Player p){
        int thirst = getThirst(p);
        int fullThirst = thirst / 2;
        int halfThirst = thirst % 2;

        StringBuilder thirstdisplay = new StringBuilder();

        thirstdisplay.append("\uE236".repeat(Math.max(0, fullThirst)));

        if (halfThirst == 1) thirstdisplay.append("\uE237");

        thirstdisplay.append("\uE330".repeat(Math.max(0, (10 - fullThirst - halfThirst))));

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("                     "+thirstdisplay));
    }

    public void thirstRunnable(Player p){

        Florial.getThirst().putIfAbsent(p.getUniqueId(), 20);

        Bukkit.getScheduler().runTaskTimerAsynchronously(Florial.getInstance(), () -> {
            if (!p.isOnline()) return;
            ThirstManager.updateThirst(p);
        }, 35L, 35);
    }


    public static Integer getThirst(Player p){
        return Florial.getThirst().get(p.getUniqueId());
    }

    private void Hydrate(Player p, int amount){
        Florial.getThirst().put(p.getUniqueId(), getThirst(p) + amount);

        if (getThirst(p) == 20)
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
                    6000, 1, false, false, true));
    }

    private void deHydrate(Player p, int amount){
        Florial.getThirst().put(p.getUniqueId(), getThirst(p) - amount);
    }

}

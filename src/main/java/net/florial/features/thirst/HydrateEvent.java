package net.florial.features.thirst;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HydrateEvent extends ThirstEvent {

    Player player;
    ItemStack i;
    Integer thirst;


    public HydrateEvent(Player player, ItemStack i, Integer thirst) {
        this.player = player;
        this.i = i;
        this.thirst = thirst;

    }

}

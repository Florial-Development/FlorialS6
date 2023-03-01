package net.florial.commands.species;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.Florial;
import net.florial.models.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ResetSpeciesCommand extends BaseCommand {

    @CommandAlias("resetspecies")
    public void onInfoPanel(Player p) {

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        if (data.getDna() >= 25) {

            data.setSpecieId(0);

            data.setDna(data.getDna() - 25);

            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 1, 1);


        } else {
            p.sendMessage("You need at least 25 DNA for this!");
        }

    }
}

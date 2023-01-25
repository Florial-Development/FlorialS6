package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import net.florial.Florial;
import net.florial.models.PlayerData;
import org.bukkit.entity.Player;

public class ChangeFlories extends BaseCommand {

    //test class

    @CommandAlias("changeflories")
    public void onInfoPanel(Player p, int a) {

        //test

        PlayerData data = Florial.getPlayerData().get(p.getUniqueId());

        data.setFlories(data.getFlories() + a);

    }
}

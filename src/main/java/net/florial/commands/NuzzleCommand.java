package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Optional;
import net.florial.utils.Message;
import org.bukkit.entity.Player;

@CommandAlias("nuzzle")
public class NuzzleCommand extends BaseCommand {

    @Default
    public static void onNuzzle(Player player, @Optional @Flags("other") Player target) {
         new Message("\n&d" + player.getName() + "&7 nuzzled &d" + target.getName() + "&7!\n").broadcast();
    }
}

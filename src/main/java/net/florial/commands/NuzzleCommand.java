package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import net.florial.utils.Message;
import org.bukkit.entity.Player;

@CommandAlias("nuzzle")
public class NuzzleCommand extends BaseCommand {
    @Default
    public static void onNuzzle(Player player, Player target) {
        new Message("\n&d").add(new Message(player.getName())).add(new Message("&7 nuzzled &d")).add(new Message(target.getName())).add(new Message("&7!\n")).broadcast();
    }
}

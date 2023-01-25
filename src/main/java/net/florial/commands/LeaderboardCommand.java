package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.florial.database.FlorialDatabase;
import org.bukkit.entity.Player;

public class LeaderboardCommand extends BaseCommand {

    //test class

    @CommandAlias("florieslb")
    public class LeaderBoardCommand extends BaseCommand {
        @Default
        public static void onlb(Player player, Player target) {

            FlorialDatabase.PlayerDataLeaderboard(player, "flories", true);


        }
    }
}

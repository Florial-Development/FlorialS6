package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.santio.utils.bukkit.impl.MessageUtils;
import me.santio.utils.minecraft.message.Message;
import net.florial.database.FlorialDatabase;
import net.florial.models.PlayerData;
import org.bukkit.entity.Player;

public class LeaderboardCommand extends BaseCommand {

    //test class

    @CommandAlias("florieslb")
    public class LeaderBoardCommand extends BaseCommand {
        @Default
        public static void onlb(Player player) {

            FlorialDatabase.sortDataByField("flories", true).thenAccept(res -> {
                Message msg = new Message("&d[Flories Leaderboard]\n");
                int iteration = 1;
                for (String it : res) {
                    msg.add(new Message("&7" + iteration + ": &d" + it));
                }
                MessageUtils.sendMessage(player, msg);
            });


        }
    }
}

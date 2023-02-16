package net.florial.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.florial.Florial;
import net.florial.features.skills.Skill;
import net.florial.features.upgrades.Upgrade;
import net.florial.models.PlayerData;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

@CommandAlias("upgrade")
public class ChangeUpgradeCommand extends BaseCommand {

    @Default
    public static void onUpgradeChange(Player player, String s, Boolean has) {
        PlayerData data = Florial.getPlayerData().get(player.getUniqueId());

        Upgrade upgrade;

        try {
            upgrade = Upgrade.valueOf(s.toUpperCase().replace(" ", "_"));
        } catch (Exception e) {
            player.sendMessage("§cInvalid upgrade, upgrades are: " + Arrays.stream(Upgrade.values()).map(Enum::name).collect(Collectors.joining(", ")));
            return;
        }


        data.getUpgrades().put(upgrade, has);
    }
}

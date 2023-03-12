package net.florial.commands.discord;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;

@CommandInfo (
        name = "uwu"
)
public class DiscordUwUCommand extends SlashCommand {

    public DiscordUwUCommand() {
        this.name = "uwu";
        this.help = "uwu";
        this.ownerCommand = false;
        this.guildOnly = true;
    }
    @Override
    public void execute(SlashCommandEvent slashCommandEvent) {
        slashCommandEvent.reply("OMG HAI UWU!!!!!!").queue();
    }


}

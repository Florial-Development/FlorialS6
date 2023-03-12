package net.florial.commands.discord;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.florial.Florial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandInfo(
        name = "mute",
        requirements = {}
)
public class DiscordMuteCommand extends SlashCommand {

    public DiscordMuteCommand() {
        this.name = "mute";
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.USER, "user", "The user you are muting").setRequired(true));
        options.add(new OptionData(OptionType.STRING, "reason", "The punishment reason").setRequired(true));
        options.add(new OptionData(OptionType.STRING, "duration", "The length of the punishment").setRequired(true));
        this.options = options;
    }
    @Override
    protected void execute(SlashCommandEvent slashCommandEvent) {
        Member member = Florial.getDiscordServer().getMemberById(slashCommandEvent.getUser().getId());
        if (member == null) {
            slashCommandEvent.reply("There was an error trying to perform this command").queue();
            return;
        }
        if (!member.hasPermission(Permission.KICK_MEMBERS)) {
            slashCommandEvent.reply("No permissions").setEphemeral(true).queue();
            return;
        }
        slashCommandEvent.deferReply().queue();

        Member victim = Florial.getDiscordServer().getMemberById(slashCommandEvent.getOption("user").getAsUser().getId());
        if (victim == null) {
            slashCommandEvent.reply("This user does not exist").queue();
            return;
        }
        String durationText = slashCommandEvent.getOption("duration").getAsString();
        long duration = 0;
        Pattern pattern;
        Matcher m;
        pattern = Pattern.compile("\"\\dmo\"gm");
        m = pattern.matcher(durationText);
        while (m.find()) {
            duration += Integer.parseInt(m.group(1).replaceAll("mo", "")) * 2630000L;
        }
        pattern = Pattern.compile("\"\\dd\"gm");
        m = pattern.matcher(durationText);
        while (m.find()) {
            duration += Integer.parseInt(m.group(1).replaceAll("d", "")) * 86400L;
        }
        pattern = Pattern.compile("\"\\dw\"gm");
        m = pattern.matcher(durationText);
        while (m.find()) {
            duration += Integer.parseInt(m.group(1).replaceAll("w", "")) * 604800L;
        }
        pattern = Pattern.compile("\"\\dh\"gm");
        m = pattern.matcher(durationText);
        while (m.find()) {
            duration += Integer.parseInt(m.group(1).replaceAll("h", "")) * 3600L;
        }
        pattern = Pattern.compile("\"\\dm\"gm");
        m = pattern.matcher(durationText);
        while (m.find()) {
            duration += Integer.parseInt(m.group(1).replaceAll("m", "")) * 60L;
        }
        pattern = Pattern.compile("\"\\dw\"gm");
        m = pattern.matcher(durationText);
        while (m.find()) {
            duration += Integer.parseInt(m.group(1).replaceAll("s", ""));
        }
        victim.timeoutFor(duration, TimeUnit.SECONDS).queue();
        slashCommandEvent.reply("").setEmbeds(new EmbedBuilder()
                .setTitle("Punishment processed")
                .addField("Victim", victim.getEffectiveName(), false)
                .addField("Staff", slashCommandEvent.getUser().getName(), false)
                .addField("Reason", slashCommandEvent.getOption("reason").getAsString(), false)
                .addField("Duration", durationText, false)
                .setColor(0xffd4ee)
                .setFooter("Tulip is a furry uwu").build()
        ).queue();
    }
}

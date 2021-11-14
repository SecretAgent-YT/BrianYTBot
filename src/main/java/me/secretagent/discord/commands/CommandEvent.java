package me.secretagent.discord.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandEvent {

    private final MessageReceivedEvent event;
    private final List<String> args;

    public CommandEvent(MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    public void reply(String string) {
        event.getMessage().reply(string).queue();
    }

    public void reply(MessageEmbed embed) {
        event.getMessage().replyEmbeds(embed).queue();
    }

    public User getUser() {
        return event.getAuthor();
    }

    public Member getMember() {
        return event.getMember();
    }

    public String getContent() {
        return event.getMessage().getContentRaw();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public List<String> getArgs() {
        return args;
    }

}

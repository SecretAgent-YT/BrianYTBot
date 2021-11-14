package me.secretagent.discord.commands.impl;

import me.secretagent.discord.commands.Command;
import me.secretagent.discord.commands.CommandEvent;
import me.secretagent.discord.commands.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatterBuilder;

public class InfoCommand extends Command {

    public InfoCommand(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public void onCalled(CommandEvent event) {
        User user;
        Member member;
        if (event.getArgs().size() > 0) {
            try {
                user = commandManager.getBrianBot().getUser(event.getArgs().get(0));
                member = commandManager.getBrianBot().getMember(event.getArgs().get(0));
            } catch (NullPointerException ex) {
                event.reply("Invalid members!");
                return;
            }
        } else {
            user = event.getUser();
            member = event.getMember();
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(user.getAvatarUrl());
        builder.setTitle(user.getAsTag());
        OffsetDateTime offsetDateTime = user.getTimeCreated();
        String month = offsetDateTime.getMonth().toString().substring(0, 1).toUpperCase() + offsetDateTime.getMonth().toString().substring(1).toLowerCase();
        builder.addField("Account Created: ", month + " " + String.valueOf(offsetDateTime.getDayOfMonth()) + ", " + String.valueOf(offsetDateTime.getYear()), true);
        offsetDateTime = member.getTimeJoined();
        month = offsetDateTime.getMonth().toString().substring(0, 1).toUpperCase() + offsetDateTime.getMonth().toString().substring(1).toLowerCase();
        builder.addField("Joined Guild: ", month + " " + String.valueOf(offsetDateTime.getDayOfMonth()) + ", " + String.valueOf(offsetDateTime.getYear()), true);
        event.reply(builder.build());
        System.out.println();
    }

}

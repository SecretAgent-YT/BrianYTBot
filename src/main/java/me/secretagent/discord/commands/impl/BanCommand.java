package me.secretagent.discord.commands.impl;

import me.secretagent.discord.commands.Command;
import me.secretagent.discord.commands.CommandEvent;
import me.secretagent.discord.commands.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public class BanCommand extends Command {

    public BanCommand(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public void onCalled(CommandEvent event) {
        Member member;
        if (event.getArgs().size() > 0) {
            try {
                member = commandManager.getBrianBot().getMember(event.getArgs().get(0));
            } catch (NullPointerException ex) {
                event.reply("Invalid member!");
                return;
            }
            String reason = "no reason specified";
            if (event.getArgs().size() > 1) {
                List<String> reasons = event.getArgs();
                reasons.remove(event.getArgs().get(0));
                String s = "";
                for (String s1 : reasons) {
                    s += " " + s1;
                }
                while (s.startsWith(" ")) {
                    s = s.substring(1);
                }
                reason = s;
            }
            User user = member.getUser();
            member.ban(7, reason).queue();
            event.reply("Banned " + user.getName() + " for " + reason);
        } else {
            event.reply("Member not specified!");
        }
    }

    @Override
    public List<Role> getRoles() {
        return Arrays.asList(commandManager.getBrianBot().getRole("Administrator"), commandManager.getBrianBot().getRole("Developers"), commandManager.getBrianBot().getRole("Owner"));
    }

}

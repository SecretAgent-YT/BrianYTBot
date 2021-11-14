package me.secretagent.discord.commands.impl;

import me.secretagent.discord.commands.Command;
import me.secretagent.discord.commands.CommandEvent;
import me.secretagent.discord.commands.CommandManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class UnmuteCommand extends Command {

    public UnmuteCommand(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public String getName() {
        return "unmute";
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
            final String finalReason = reason;
            commandManager.getBrianBot().getGuild().removeRoleFromMember(member, commandManager.getBrianBot().getRole("Muted")).queue();
            event.reply("Unmuted " + user.getAsMention() + " for " + reason);
        } else {
            event.reply("Member not specified!");
        }
    }

    @Override
    public List<Role> getRoles() {
        return Arrays.asList(commandManager.getBrianBot().getRole("Administrator"), commandManager.getBrianBot().getRole("Developers"), commandManager.getBrianBot().getRole("Owner"));
    }

}

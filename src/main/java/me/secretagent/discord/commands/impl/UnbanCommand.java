package me.secretagent.discord.commands.impl;

import me.secretagent.discord.commands.Command;
import me.secretagent.discord.commands.CommandEvent;
import me.secretagent.discord.commands.CommandManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class UnbanCommand extends Command {

    public UnbanCommand(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public void onCalled(CommandEvent event) {
        String userID;
        if (event.getArgs().size() > 0) {
            try {
                userID = event.getArgs().get(0);
            } catch (NullPointerException ex) {
                event.reply("Invalid user!");
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
            commandManager.getBrianBot().getGuild().unban(userID).queue();
            event.reply("Unbanned " + userID + " for " + reason);
        } else {
            event.reply("Member not specified!");
        }
    }

    @Override
    public List<Role> getRoles() {
        return Arrays.asList(commandManager.getBrianBot().getRole("Administrator"), commandManager.getBrianBot().getRole("Developers"), commandManager.getBrianBot().getRole("Owner"));
    }

}

package me.secretagent.discord.commands;

import me.secretagent.discord.BrianBot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private final BrianBot brianBot;
    private final JSONObject config;
    protected final List<Command> commands = new ArrayList<>();

    public CommandManager(BrianBot brianBot) {
        this.brianBot = brianBot;
        this.config = brianBot.getConfig();
        brianBot.getJda().addEventListener(this);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String prefix = config.get("prefix").toString();
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().startsWith(prefix)) return;
        String msg = event.getMessage().getContentRaw().replace(prefix, "");
        ArrayList<String> args = new ArrayList<>();
        args.addAll(Arrays.asList(msg.split(" ")));
        for (Command command : commands) {
            if (command.getPermission() != null && !event.getMember().hasPermission(command.getPermission())) return;
            if (command.getRoles().size() > 0) {
                boolean hasRole = false;
                for (Role role : event.getMember().getRoles()) {
                    if (command.getRoles().contains(role)) {
                        hasRole = true;
                    }
                }
                if (!hasRole) {
                    event.getTextChannel().sendMessage("Hi! Apparently, you don't have the role required to run that command!\nIf you believe this is a mistake, please contact the admins").queue();
                    return;
                }
            }
            if (msg.startsWith(command.getName())) {
                args.remove(command.getName());
                CommandEvent commandEvent = new CommandEvent(event, args);
                command.onCalled(commandEvent);
            }
        }
    }

    public BrianBot getBrianBot() {
        return brianBot;
    }
}

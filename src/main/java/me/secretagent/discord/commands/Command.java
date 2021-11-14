package me.secretagent.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;
import java.util.Collections;

public abstract class Command {

    protected final CommandManager commandManager;

    public Command(CommandManager commandManager) {
        this.commandManager = commandManager;
        commandManager.commands.add(this);
    }

    public abstract String getName();

    public Permission getPermission() {
        return null;
    }

    public List<Role> getRoles() {
        return Collections.emptyList();
    }

    public void onCalled(CommandEvent event) {

    }

}

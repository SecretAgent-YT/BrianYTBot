package me.secretagent.discord.listener;

import me.secretagent.discord.BrianBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class CensorListener extends ListenerAdapter {

    private final BrianBot brianBot;

    public CensorListener(BrianBot brianBot) {
        this.brianBot = brianBot;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        for (String string : brianBot.getCensorWords()) {
            if (event.getMessage().getContentRaw().toLowerCase().contains(string)) {
                event.getMessage().delete().queue();
                event.getTextChannel().sendMessage(event.getAuthor().getName() + ", please don't use naughty language!").queue(message -> {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            message.delete().queue();
                        }
                    }, 3000);
                });
                return;
            }
        }
    }

}

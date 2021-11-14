package me.secretagent.discord;

import me.secretagent.discord.commands.CommandManager;
import me.secretagent.discord.commands.impl.*;
import me.secretagent.discord.listener.CensorListener;
import me.secretagent.discord.tasks.CheckUploadTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BrianBot extends ListenerAdapter {

    private final JSONObject config;
    private final JDA jda;
    private final List<String> censorWords;

    public BrianBot(JSONObject config, List<String> censorWords) throws Exception {
        this.config = config;
        this.censorWords = censorWords;
        jda = JDABuilder.createDefault(config.get("token").toString())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.listening(";"))
                .addEventListeners(this, new CensorListener(this))
                .build();
        CommandManager commandManager = new CommandManager(this);
        InfoCommand infoCommand = new InfoCommand(commandManager);
        BanCommand banCommand = new BanCommand(commandManager);
        UnbanCommand unbanCommand = new UnbanCommand(commandManager);
        MuteCommand muteCommand = new MuteCommand(commandManager);
        UnmuteCommand unmuteCommand = new UnmuteCommand(commandManager);
    }

    public JSONObject getConfig() {
        return config;
    }

    public List<String> getCensorWords() {
        return censorWords;
    }

    public Guild getGuild() {
        return jda.getGuilds().get(0);
    }

    public Role getRole(String roleName) {
        return getGuild().getRolesByName(roleName, true).get(0);
    }

    public JDA getJda() {
        return jda;
    }

    public User getUser(String string) {
        if (string.matches("<@!?(\\d+)>")) {
            return getJda().getUserById(string.replace("<@!", "").replace("<@", "").replace(">", ""));
        }
        return null;
    }

    public Member getMember(String string) {
        if (string.matches("<@!?(\\d+)>")) {
            return getGuild().getMemberById(string.replace("<@!", "").replace("<@", "").replace(">", ""));
        }
        return null;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        CheckUploadTask task = new CheckUploadTask(this);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(11);
        service.scheduleAtFixedRate(task, 0, 20, TimeUnit.SECONDS);
    }

}

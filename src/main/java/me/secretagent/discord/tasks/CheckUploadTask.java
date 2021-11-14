package me.secretagent.discord.tasks;

import me.secretagent.discord.BrianBot;
import net.dv8tion.jda.api.entities.TextChannel;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.*;
import java.util.Iterator;
import java.util.Scanner;

public class CheckUploadTask implements Runnable {

    private String videoID = "none";
    private final BrianBot brianBot;
    private int i = 0;

    public CheckUploadTask(BrianBot brianBot) {
        this.brianBot = brianBot;
    }

    @Override
    public void run() {
        System.out.println("Checking uploads... Iteration " + i);
        try {
            String urlString = "https://www.youtube.com/feeds/videos.xml?channel_id=UCa8ZALSjBmNEVnDlgD8Oarw";
            URL url = new URL(urlString);;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Scanner scanner = new Scanner(url.openStream());
            String inline = "";
            while (scanner.hasNextLine()) {
                inline += scanner.nextLine();
            }
            Document document = DocumentHelper.parseText(inline);
            Element feed = document.getRootElement();
            Element entry = feed.element("entry");
            String tempID = entry.elementText("id").replace("yt:video:", "");
            if (!videoID.equals(tempID) && !videoID.equals("none")) {
                String msg = "@everyone\nB_RIAN just uploaded! Go check it out!\nMake sure to like and subscribe if you haven't already!\nhttps://youtube.com/watch?v=" + tempID;
                TextChannel channel = brianBot.getJda().getTextChannelById(brianBot.getConfig().get("uploadChannelID").toString());
                channel.sendMessage(msg).queue();
            }
            videoID = tempID;
            i++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

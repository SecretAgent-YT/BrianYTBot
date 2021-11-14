package me.secretagent.discord;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Arrays;
import java.util.Scanner;

public class Start {

    public static void main(String args[]) throws Exception {
        JSONObject object = new JSONObject();
        object.put("token", "OTAzMDI5NTk0NzAwMDE3NjY0.YXnB2A.Gh6VvYck7jY3fWX2htocq9r4ezc");
        object.put("prefix", ";");
        object.put("uploadChannelID", "762822345639198742");
        BrianBot brianBot = new BrianBot(object, Arrays.asList("oatmeal"));
    }

}

package me.secretagent.discord;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Scanner;

public class Start {

    public static void main(String args[]) throws Exception {
        System.out.println("Hello World!");
        JSONObject object = new JSONObject();
        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(System.getenv("PORT")), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                byte[] response = "B_RIANYT BOT".getBytes();
                httpExchange.sendResponseHeaders(200, response.length);
                OutputStream os = httpExchange.getResponseBody();
                os.write(response);
                os.close();
            }
        });
        server.start();
        object.put("token", "OTAzMDI5NTk0NzAwMDE3NjY0.YXnB2A.Gh6VvYck7jY3fWX2htocq9r4ezc");
        object.put("prefix", ";");
        object.put("uploadChannelID", "762822345639198742");
        BrianBot brianBot = new BrianBot(object, Arrays.asList("oatmeal"));
    }

}

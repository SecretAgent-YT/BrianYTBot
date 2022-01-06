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
        JSONObject object = new JSONObject();
        int port;
        if (System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        } else {
            port = 8000;
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", httpExchange -> {
            byte[] response = "B_RIANYT BOT".getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response);
            os.close();
        });
        server.start();
        object.put("token", "uthought");
        object.put("prefix", ";");
        object.put("uploadChannelID", "762822345639198742");
        BrianBot brianBot = new BrianBot(object, Arrays.asList("oatmeal"));
    }

}

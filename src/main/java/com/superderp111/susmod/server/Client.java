package com.superderp111.susmod.server;

import net.minecraft.client.MinecraftClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {

    public static SusModServer client;

    public static void load() {
        connect();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if(SusModServer.users.size() == 0) {
                    connect();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public static void connect() {
        try {
            String url = "wss://susmod.superderp111.repl.co/?username=" + MinecraftClient.getInstance().getSession().getUsername() + "&uuid=" + MinecraftClient.getInstance().getSession().getUuid();
            client = new SusModServer(new URI(url));
            client.connect();
            System.out.println("Connecting to SusServer");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message) {
        try {
            client.send("§§chat" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

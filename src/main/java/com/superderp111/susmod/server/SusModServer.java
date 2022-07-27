package com.superderp111.susmod.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.superderp111.susmod.SusMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class SusModServer extends WebSocketClient {

    public static JsonArray users = new JsonArray();

    public SusModServer(URI uri) {
        super(uri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

        System.out.println(message);

        try {
            JsonObject jsonObject = SusMod.jsonParser.parse(message).getAsJsonObject();
            if (jsonObject.get("type").getAsString().equals("users")) {
                users = jsonObject.get("content").getAsJsonArray();
                System.out.println(users);
            }
            if (jsonObject.get("type").getAsString().equals("chat")) {
                if(SusMod.settingsManager.getSettingByName("SusChat").getValBoolean()) {
                    String chatMessage = jsonObject.get("content").getAsString();
                    String userName = jsonObject.get("username").getAsString();
                    if (MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().player.sendMessage(Text.of(SusMod.chatPrefix + userName + ": " + chatMessage), false);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        users = new JsonArray();
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static int getUserAmount(){
        return users.size();
    }

    public static boolean hasUser(String user) {
        return users.toString().contains("\"username\":\""+user+"\"");
    }
}

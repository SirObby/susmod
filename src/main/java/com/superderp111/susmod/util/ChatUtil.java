package com.superderp111.susmod.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil {

    public static void sendMessage(String message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(message), false);
    }
}

package com.superderp111.susmod.mixin.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.module.ModuleManager;
import com.superderp111.susmod.server.SusModServer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHudListener;
//import net.minecraft.network.MessageType;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Mixin(ChatHudListener.class)
public class MixinChatHudListener {

    @Inject(method={"onChatMessage"}, at = @At("HEAD"), cancellable = true)
    public void onChatMessage(MessageType messageType, Text message, @Nullable MessageSender sender, CallbackInfo callbackInfo) {
        if(message.getString().equalsIgnoreCase("◆ Welcome back to DiamondFire! ◆")) {
            if (SusMod.moduleManager.getModuleByName("Flight").isToggled()) {
                try {
                    MinecraftClient.getInstance().player.sendChatMessage("/fly");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (JsonElement user : SusModServer.users) {
            JsonObject userObj = user.getAsJsonObject();
            String username = userObj.get("username").getAsString();

            if(message.getString().contains(username)) {
                Text joinedText = Text.of("");
                List<Text> siblings = message.getSiblings();
                for (Text sibling : siblings) {
                    int index = siblings.indexOf(sibling);
                    try {
                        if (siblings.get(index + 1).getString().contains(username)) {
                            if(!sibling.getString().contains(SusMod.prefix)) {
                                joinedText = joinedText.copy().append(sibling).append(SusMod.prefix);
                            }
                            else {
                                joinedText = joinedText.copy().append(sibling);
                            }
                        } else {
                            joinedText = joinedText.copy().append(sibling);
                        }
                    }
                    catch (Exception e) {
                        joinedText = joinedText.copy().append(sibling);
                    }
                }
                if (message.getSiblings().size() == 0) {
                    joinedText = Text.of(message.getString());
                    int index = joinedText.copy().toString().indexOf(username);
                    String string = joinedText.getString();
                    joinedText = Text.of(string.substring(0, index) + SusMod.prefix + string.substring(index));
                }
                message = joinedText;
            }
        }

        /*if (messageType != MessageType.CHAT) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(message);
        } else {
            MinecraftClient.getInstance().inGameHud.getChatHud().queueMessage(message);
        }*/

        callbackInfo.cancel();
    }
}

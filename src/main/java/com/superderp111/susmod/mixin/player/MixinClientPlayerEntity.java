package com.superderp111.susmod.mixin.player;

import com.superderp111.susmod.SusMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "sendChatMessage", at = @At("HEAD"))
    public void sendChatMessage(String message, CallbackInfo callbackInfo) {
        if(message.equalsIgnoreCase("/spawn") || message.equalsIgnoreCase("/s")) {
            if (SusMod.moduleManager.getModuleByName("Flight").isToggled()) {
                try {
                    //Thread.sleep(250);
                    MinecraftClient.getInstance().player.sendChatMessage("/fly");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

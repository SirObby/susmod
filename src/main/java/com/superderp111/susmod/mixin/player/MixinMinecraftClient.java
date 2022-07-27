package com.superderp111.susmod.mixin.player;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.OutOfMemoryScreen;
import net.minecraft.client.util.Session;
import net.minecraft.util.TickDurationMonitor;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Mutable
    @Shadow @Final private Session session;

    /*@Inject(at = @At("HEAD"), method = "isMultiplayerEnabled", cancellable = true)
    void isMultiplayerEnabled(CallbackInfoReturnable<Boolean> cir) {
        try {
            this.session = createSession("pass", "user", Proxy.NO_PROXY);
            System.out.println("Logged in as " + this.session.getUsername());
        }
        catch (Exception e) {
            System.out.println("UNABLE TO LOGIN");
        }
    }*/

    /*public Session createSession(String username, String password, @NotNull Proxy proxy) throws Exception {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(proxy, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(username);
        auth.setPassword(password);

        auth.logIn();
        return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                auth.getAuthenticatedToken(), "mojang");
    }*/
}

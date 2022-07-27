package com.superderp111.susmod.mixin.player;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.server.SusModServer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {

    @Inject(at = @At("HEAD"), method = "getPlayerName", cancellable = true)
    public void getPlayerName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        Text name = spectatorFormat(entry, Team.decorateName(entry.getScoreboardTeam(), Text.of(new String(entry.getProfile().getName()))));
        if (SusModServer.hasUser(entry.getProfile().getName()) || entry.getProfile().getName().equalsIgnoreCase("SuperDerp111")) {
            name = Text.of(SusMod.prefix + name.getString());
        }
        cir.setReturnValue(name);
    }

    private Text spectatorFormat(PlayerListEntry playerListEntry, MutableText mutableText) {
        return playerListEntry.getGameMode() == GameMode.SPECTATOR ? mutableText.formatted(Formatting.ITALIC) : mutableText;
    }
}

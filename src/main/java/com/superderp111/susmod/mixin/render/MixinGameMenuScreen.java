package com.superderp111.susmod.mixin.render;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.Gui;
import com.superderp111.susmod.module.ModuleManager;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
//import net.minecraft.text.TranslatableText;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class MixinGameMenuScreen extends Screen{

    protected MixinGameMenuScreen(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At(value = "HEAD"), cancellable = true)
    public void onInitWidgets(CallbackInfo callbackInfo) {
        int y = this.height / 4 + 120 -16;
        if(SusMod.moduleManager.getModuleByName("CactusKing").isToggled() && SusMod.settingsManager.getSettingByName("AntiDisconnect").getValBoolean()) {
            y = this.height / 2 + 15;
            if (this.height / 2 < this.height / 4 + 96) {
                y = this.height / 4 + 96 + 25;
            }
        }
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, Text.translatable("menu.returnToGame"), (button) -> {
            this.client.setScreen((Screen)null);
            this.client.mouse.lockCursor();
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 48 + -16, 98, 20, Text.translatable("gui.advancements"), (button) -> {
            this.client.setScreen(new AdvancementsScreen(this.client.player.networkHandler.getAdvancementHandler()));
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 48 + -16, 98, 20, Text.translatable("gui.stats"), (button) -> {
            this.client.setScreen(new StatsScreen(this, this.client.player.getStatHandler()));
        }));
        String string = SharedConstants.getGameVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, Text.translatable("menu.sendFeedback"), (button) -> {
            this.client.setScreen(new ConfirmChatLinkScreen((confirmed) -> {
                if (confirmed) {
                    Util.getOperatingSystem().open(string);
                }

                this.client.setScreen(this);
            }, string, true));
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, Text.translatable("menu.reportBugs"), (button) -> {
            this.client.setScreen(new ConfirmChatLinkScreen((confirmed) -> {
                if (confirmed) {
                    Util.getOperatingSystem().open("https://aka.ms/snapshotbugs?ref=game");
                }

                this.client.setScreen(this);
            }, "https://aka.ms/snapshotbugs?ref=game", true));
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 96 + -16, 98, 20, Text.translatable("menu.options"), (button) -> {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
        }));
        ButtonWidget buttonWidget = (ButtonWidget)this.addDrawableChild(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, Text.translatable("menu.shareToLan"), (button) -> {
            this.client.setScreen(new OpenToLanScreen(this));
        }));
        buttonWidget.active = this.client.isIntegratedServerRunning() && !this.client.getServer().isRemote();
        Text text = this.client.isInSingleplayer() ? Text.translatable("menu.returnToMenu") : Text.translatable("menu.disconnect");
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102, y, 204, 20, text, (button) -> {
            boolean bl = this.client.isInSingleplayer();
            boolean bl2 = this.client.isConnectedToRealms();
            button.active = false;
            this.client.world.disconnect();
            if (bl) {
                //this.client.disconnect(new SaveLevelScreen(Text.translatable("menu.savingLevel")));
            } else {
                this.client.disconnect();
            }

            TitleScreen titleScreen = new TitleScreen();
            if (bl) {
                this.client.setScreen(titleScreen);
            } else if (bl2) {
                this.client.setScreen(new RealmsMainScreen(titleScreen));
            } else {
                this.client.setScreen(new MultiplayerScreen(titleScreen));
            }

        }));
        callbackInfo.cancel();
    }
}

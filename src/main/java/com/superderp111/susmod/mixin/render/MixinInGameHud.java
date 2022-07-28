package com.superderp111.susmod.mixin.render;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.util.ColorUtil;
import com.superderp111.susmod.clickgui.util.FontUtil;
import com.superderp111.susmod.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Shadow private int scaledHeight;
    @Shadow private int scaledWidth;

    private boolean first = true;

    @Inject(method={"render"}, at = @At("TAIL"))
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo) {
        if (first) {
            first = false;
            FontUtil.setupFontUtils();
        }
        renderHUDInfo(matrices);
    }

    public void renderHUDInfo(MatrixStack matrices) {
        int y = 1;
        int x = 1;

        // NAME
        if(SusMod.settingsManager.getSettingByName("Name").getValBoolean()) {
            MatrixStack matrices2 = new MatrixStack();
            matrices2.scale(2, 2, 2);

            FontUtil.drawStringWithShadow(matrices2, "SUSMOD 1.0", x, y, ColorUtil.getClickGUIColor().getRGB());
            y += 20;
        }

        // ARRAYLIST
        if(SusMod.settingsManager.getSettingByName("ArrayList").getValBoolean()) {
            ArrayList<Module> enabledMods = new ArrayList<Module>();
            for (Module m : SusMod.moduleManager.getModules()) {
                if (m.isToggled()) {
                    enabledMods.add(m);
                }
            }
            enabledMods.sort((m1, m2) -> FontUtil.getStringWidth(m2.getDisplayName()) - FontUtil.getStringWidth(m2.getDisplayName()));

            for (int counter = 0; counter < enabledMods.size(); counter++) {
                FontUtil.drawStringWithShadow(matrices, enabledMods.get(counter).getDisplayName(), x, 1 + y, ColorUtil.getClickGUIColor().brighter().getRGB());
                y += 10;
            }
        }

        x = MinecraftClient.getInstance().getWindow().getScaledWidth();
        y = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // COORDS
        if(SusMod.settingsManager.getSettingByName("Coords").getValBoolean()) {
            // g = global (p = plots)
            int off = 40;
            String gx = String.valueOf(MinecraftClient.getInstance().player.getX());
            String gy = String.valueOf(MinecraftClient.getInstance().player.getY());
            String gz = String.valueOf(MinecraftClient.getInstance().player.getZ());
            gx = String.format("%.2f", Float.valueOf(gx)).replace(",", ".");
            gy = String.format("%.2f", Float.valueOf(gy)).replace(",", ".");
            gz = String.format("%.2f", Float.valueOf(gz)).replace(",", ".");
            if(FontUtil.getStringWidth(gx) >= 30 && FontUtil.getStringWidth(gx) >= FontUtil.getStringWidth(gy) && FontUtil.getStringWidth(gx) >= FontUtil.getStringWidth(gz)) {
                off = (FontUtil.getStringWidth(gx) + 10);
            }
            if(FontUtil.getStringWidth(gy) >= 30 && FontUtil.getStringWidth(gy) >= FontUtil.getStringWidth(gx) && FontUtil.getStringWidth(gy) >= FontUtil.getStringWidth(gz)) {
                off = (FontUtil.getStringWidth(gy) + 10);
            }
            if(FontUtil.getStringWidth(gz) >= 30 && FontUtil.getStringWidth(gz) >= FontUtil.getStringWidth(gx) && FontUtil.getStringWidth(gz) >= FontUtil.getStringWidth(gy)) {
                off = (FontUtil.getStringWidth(gz) + 10);
            }


            y -= 10;
            FontUtil.drawStringWithShadow(matrices, gz, x - 1 - FontUtil.getStringWidth(gz), y, ColorUtil.getClickGUIColor().getRGB());
            FontUtil.drawStringWithShadow(matrices, "Z", x - off - FontUtil.getStringWidth("Z"), y, ColorUtil.getClickGUIColor().getRGB());
            y -= 10;
            FontUtil.drawStringWithShadow(matrices, gy, x - 1 - FontUtil.getStringWidth(gy), y, ColorUtil.getClickGUIColor().getRGB());
            FontUtil.drawStringWithShadow(matrices, "Y", x - off - FontUtil.getStringWidth("Y"), y, ColorUtil.getClickGUIColor().getRGB());
            y -= 10;
            FontUtil.drawStringWithShadow(matrices, gx, x - 1 - FontUtil.getStringWidth(gx), y, ColorUtil.getClickGUIColor().getRGB());
            FontUtil.drawStringWithShadow(matrices, "X", x - off - FontUtil.getStringWidth("X"), y, ColorUtil.getClickGUIColor().getRGB());
        }

        // FPS
        if(SusMod.settingsManager.getSettingByName("FPS").getValBoolean()) {
            List<String> fpsString = Arrays.asList(String.valueOf(MinecraftClient.getInstance().fpsDebugString).split(" "));
            fpsString = fpsString.subList(0, 2);
            String fps = String.join(" ", fpsString);
            fps = fps.toUpperCase();

            y -= 10;
            FontUtil.drawStringWithShadow(matrices, fps, x - 1 - FontUtil.getStringWidth(fps), y, ColorUtil.getClickGUIColor().getRGB());
        }

        // TPS
        if(SusMod.settingsManager.getSettingByName("Ping").getValBoolean()) {
            String ping;
            if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
                ping = String.valueOf(MinecraftClient.getInstance().getCurrentServerEntry().ping);
            } else {
                ping = "0";
            }

            y -= 10;
            FontUtil.drawStringWithShadow(matrices, ping + " ms", x - 1 - FontUtil.getStringWidth(ping + " ms"), y, ColorUtil.getClickGUIColor().getRGB());
        }

        // cactus king more like cactus shit
        if(SusMod.settingsManager.getSettingByName("Cactus Counter").getValBoolean()) {
            int cacti = 0;
            int goldencacti = 0;
            for (ItemStack is : MinecraftClient.getInstance().player.getInventory().main) {
                if(is.getItem() == Items.YELLOW_DYE) {
                    goldencacti += is.getCount();
                }
                if(is.getItem() == Items.GREEN_DYE) {
                    cacti += is.getCount();
                }
            }
            ItemStack offhand = MinecraftClient.getInstance().player.getInventory().offHand.get(0);
            if(offhand.getItem() == Items.YELLOW_DYE) {
                goldencacti += offhand.getCount();
            }
            if(offhand.getItem() == Items.GREEN_DYE) {
                cacti += offhand.getCount();
            }
            FontUtil.drawTotalCenteredStringWithShadow(matrices, "Golden Cactus Counter: " + goldencacti, scaledWidth / 2, scaledHeight - 85, ColorUtil.getClickGUIColor().getRGB());
            FontUtil.drawTotalCenteredStringWithShadow(matrices, "Cactus Counter: " + cacti, scaledWidth / 2, scaledHeight - 78, ColorUtil.getClickGUIColor().getRGB());
        }
    }
}
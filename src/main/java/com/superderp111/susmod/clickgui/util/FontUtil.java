package com.superderp111.susmod.clickgui.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FontUtil {
    public static TextRenderer fontRenderer;


    public static void setupFontUtils() {
        fontRenderer = MinecraftClient.getInstance().textRenderer;
    }

    public static int getStringWidth(String text) {
        return fontRenderer.getWidth(text); //STRIPCONTROLCODES !!
    }

    public static int getFontHeight() {
        return 9;//fontRenderer.fontHeight;
    }

    public static void drawString(MatrixStack matrices, String text, int x, int y, int color) {
        fontRenderer.draw(matrices, text, x, y, color);
    }

    public static void drawStringWithShadow(MatrixStack matrices, String text, int x, int y, int color) {
        fontRenderer.drawWithShadow(matrices, text, x, y, color);
    }

    public static void drawCenteredString(MatrixStack matrices, String text, int x, int y, int color) {
        drawString(matrices, text, x - fontRenderer.getWidth(text) / 2, y, color);
    }

    public static void drawCenteredStringWithShadow(MatrixStack matrices, String text, int x, int y, int color) {
        drawStringWithShadow(matrices, text, x - fontRenderer.getWidth(text) / 2, y, color);
    }

    public static void drawTotalCenteredString(MatrixStack matrices, String text, int x, int y, int color) {
        drawString(matrices, text, x - fontRenderer.getWidth(text) / 2, y - fontRenderer.fontHeight / 2, color);
    }

    public static void drawTotalCenteredStringWithShadow(MatrixStack matrices, String text, int x, int y, int color) {
        drawStringWithShadow(matrices, text, x - fontRenderer.getWidth(text) / 2, (int) (y - fontRenderer.fontHeight / 2F), color);
    }
}

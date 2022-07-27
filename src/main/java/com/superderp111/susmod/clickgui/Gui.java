package com.superderp111.susmod.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class Gui <T extends ButtonWidget> {

    public static void drawRect(int x1, int y1, int x2, int y2, int color) {
        MatrixStack matrices = new MatrixStack();
        Screen.fill(matrices, x1, y1, x2, y2, color);
    }
}

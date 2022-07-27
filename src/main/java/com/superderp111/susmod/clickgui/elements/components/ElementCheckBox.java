package com.superderp111.susmod.clickgui.elements.components;

import com.superderp111.susmod.clickgui.Gui;
import com.superderp111.susmod.clickgui.elements.Element;
import com.superderp111.susmod.clickgui.elements.ModuleButton;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.clickgui.util.ColorUtil;
import com.superderp111.susmod.clickgui.util.FontUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class ElementCheckBox extends Element {

    public int checkWidth;
    public int checkHeight;

    public ElementCheckBox(ModuleButton iparent, Setting iset) {
        checkWidth = 14;
        checkHeight = 14;
        parent = iparent;
        set = iset;
        super.setup();
    }

    public void drawScreen(double mouseX, double mouseY, float partialTicks) {
        checkWidth = 10;
        checkHeight = 10;
        MatrixStack matrices = new MatrixStack();
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();


        Gui.drawRect(x, y, x + width, y + height, 0xff101010);

        FontUtil.drawString(matrices, setstrg, x + 2, (int)(y + FontUtil.getFontHeight() / 2 - 2), 0xffffffff);
        Gui.drawRect(x + width - 1, y + 1, x + width - checkWidth - 1, y + checkHeight + 1, 0xff242424);
        Gui.drawRect(x + width - 2, y + 2, x + width - checkWidth, y + checkHeight, set.getValBoolean() ? color : 0xff000000);
        if (isCheckHovered(mouseX, mouseY))
            Gui.drawRect(x + width - 2, y + 2, x + width - checkWidth, y + checkHeight, 0x55111111);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
            set.setValBoolean(!set.getValBoolean());
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isCheckHovered(double mouseX, double mouseY) {
        return mouseX <= x + width - 2 && mouseX >= x + width - checkWidth && mouseY >= y + 2 && mouseY <= y + checkHeight;
    }
}

package com.superderp111.susmod.clickgui.elements.components;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.Gui;
import com.superderp111.susmod.clickgui.elements.Element;
import com.superderp111.susmod.clickgui.elements.ModuleButton;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.clickgui.util.ColorUtil;
import com.superderp111.susmod.clickgui.util.FontUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ElementSlider extends Element {
    public boolean dragging;

    public ElementSlider(ModuleButton iparent, Setting iset) {
        parent = iparent;
        set = iset;
        dragging = false;
        super.setup();
    }

    public void drawScreen(double mouseX, double mouseY, float partialTicks) {
        MatrixStack matrices = new MatrixStack();
        String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
        if(set.onlyInt()) {
            displayval = displayval.replace(".0", "");
        }
        boolean hoveredORdragged = isSliderHovered(mouseX, mouseY) || dragging;

        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
        int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();

        //selected = iset.getValDouble() / iset.getMax();
        double percentBar = (set.getValDouble() - set.getMin())/(set.getMax() - set.getMin());

        Gui.drawRect(x, y, x + width, y + height, 0xff101010);

        FontUtil.drawString(matrices, setstrg, x + 1, y + 2, 0xffffffff);
        FontUtil.drawString(matrices, displayval, x + width - FontUtil.getStringWidth(displayval), y + 2, 0xffffffff);

        Gui.drawRect(x, y + 10, x + width, y + 12, 0xff000000);
        Gui.drawRect(x, y + 10, (int)(x + (percentBar * width)), y + 12, color);

        if(percentBar > 0 && percentBar < 1)
            Gui.drawRect((int)(x + (percentBar * width)-1), y + 10, (int)(x + Math.min((percentBar * width), width)), y + 12, color2);

        if (this.dragging) {
            double diff = set.getMax() - set.getMin();
            double val = set.getMin() + (MathHelper.clamp((mouseX - x) / width, 0, 1)) * diff;
            set.setValDouble(val);
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
            this.dragging = true;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(double mouseX, double mouseY, int state) {
        this.dragging = false;
    }

    public boolean isSliderHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y + 10 && mouseY <= y + 12;
    }
}

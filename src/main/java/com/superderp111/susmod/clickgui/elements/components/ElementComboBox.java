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
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.awt.*;

public class ElementComboBox extends Element {

    public ElementComboBox(ModuleButton iparent, Setting iset) {
        parent = iparent;
        set = iset;
        super.setup();
    }

    public void drawScreen(double mouseX, double mouseY, float partialTicks) {
        MatrixStack matrices = new MatrixStack();
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();

        Gui.drawRect(x, y, x + width, y + height, 0xff101010);

        FontUtil.drawTotalCenteredString(matrices, setstrg, x + width / 2, y + 15/2, 0xffffffff);
        int clr1 = color;
        int clr2 = temp.getRGB();

        Gui.drawRect(x, y + 14, x + width, y + 15, 0x77000000);
        if (comboextended) {
            Gui.drawRect(x, y + 15, x + width, y + height, 0xaa121212);
            int ay = y + 15;
            for (String sld : set.getOptions()) {
                String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
                FontUtil.drawCenteredString(matrices, elementtitle, x + width / 2, ay + 2, 0xffffffff);

                if (sld.equalsIgnoreCase(set.getValString())) {
                    Gui.drawRect(x, ay, (int) (x + 1.5), ay + FontUtil.getFontHeight() + 2, clr1);
                }
                if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY < ay + FontUtil.getFontHeight() + 2) {
                    Gui.drawRect((int) (x + width - 1.2), ay, x + width, ay + FontUtil.getFontHeight() + 2, clr2);
                }
                ay += FontUtil.getFontHeight() + 2;
            }
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (isButtonHovered(mouseX, mouseY)) {
                comboextended = !comboextended;
                return true;
            }

            if (!comboextended)return false;
            double ay = y + 15;
            for (String slcd : set.getOptions()) {
                if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY <= ay + FontUtil.getFontHeight() + 2) {
                    if(SusMod.settingsManager.getSettingByName("ClickGUI Sound").getValBoolean())
                        MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_PISTON_CONTRACT, 1f, 1f);

                    if(clickgui != null && clickgui.setmgr != null)
                        clickgui.setmgr.getSettingByName(set.getName()).setValString(slcd.toLowerCase());
                    return true;
                }
                ay += FontUtil.getFontHeight() + 2;
            }
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isButtonHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
    }
}

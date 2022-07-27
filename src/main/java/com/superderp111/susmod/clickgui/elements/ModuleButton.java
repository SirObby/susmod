package com.superderp111.susmod.clickgui.elements;
import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.Gui;
import com.superderp111.susmod.clickgui.Panel;
import com.superderp111.susmod.clickgui.elements.components.ElementCheckBox;
import com.superderp111.susmod.clickgui.elements.components.ElementComboBox;
import com.superderp111.susmod.clickgui.elements.components.ElementSlider;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.clickgui.util.ColorUtil;
import com.superderp111.susmod.clickgui.util.FontUtil;
import com.superderp111.susmod.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;


public class ModuleButton {
    public Module mod;
    public ArrayList<Element> menuelements;
    public Panel parent;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean extended = false;


    public ModuleButton(Module imod, Panel pl) {
        mod = imod;

        height = FontUtil.getFontHeight() + 2;
        parent = pl;
        menuelements = new ArrayList<>();

        if (SusMod.settingsManager.getSettingsByMod(imod) != null)
            for (Setting s : SusMod.settingsManager.getSettingsByMod(imod)) {
                if (s.isCheck()) {
                    menuelements.add(new ElementCheckBox(this, s));
                } else if (s.isSlider()) {
                    menuelements.add(new ElementSlider(this, s));
                } else if (s.isCombo()) {
                    menuelements.add(new ElementComboBox(this, s));
                }
            }

    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MatrixStack matrices = new MatrixStack();

        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();

        int textcolor = 0xffafafaf;
        if (mod.isToggled()) {
            //Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, color);
            textcolor = color; //0xffefefef;
        }


        if (isHovered(mouseX, mouseY)) {
            Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, 0x55111111);
        }


        FontUtil.drawTotalCenteredStringWithShadow(matrices, mod.getName(), x + width / 2, y + 2 + height / 2, textcolor);
        if(SusMod.settingsManager.getSettingsByMod(mod) != null) {
            if(!extended) {
                FontUtil.drawTotalCenteredStringWithShadow(matrices, "+", x + width - 2, y + 2 + height / 2, 0xffffffff);
            } else {
                FontUtil.drawTotalCenteredStringWithShadow(matrices, "-", x + width - 2, y + 2 + height / 2, 0xffffffff);
            }
        }
    }


    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY))
            return false;


        if (mouseButton == 0) {
            mod.toggle();

            if(SusMod.settingsManager.getSettingByName("ClickGUI Sound").getValBoolean())
                MinecraftClient.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.5f, 0.5f);
        } else if (mouseButton == 1) {

            if (menuelements != null && menuelements.size() > 0) {
                boolean b = !this.extended;
                SusMod.clickGui.closeAllSettings();
                this.extended = b;

                if(SusMod.settingsManager.getSettingByName("ClickGUI Sound").getValBoolean())
                    if(extended)
                        MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_PISTON_EXTEND, 1f, 1f);else MinecraftClient.getInstance().player.playSound(new SoundEvent(new Identifier("tile.piston.out")), 1f, 1f);
            }
        }
        return true;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

}

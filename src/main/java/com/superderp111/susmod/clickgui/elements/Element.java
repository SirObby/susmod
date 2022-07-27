package com.superderp111.susmod.clickgui.elements;

import com.superderp111.susmod.clickgui.ClickGUI;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.clickgui.util.FontUtil;

public class Element {
    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    public int offset;
    public int x;
    public int y;
    public int width;
    public int height;

    public String setstrg;

    public boolean comboextended;

    public void setup(){
        clickgui = parent.parent.clickgui;
    }

    public void update(){

        x = parent.x + parent.width + 2;
        y = parent.y + offset;
        width = parent.width + 10;
        height = 12;//FontUtil.getFontHeight() + 5;//15;

        String sname = set.getName();
        if(set.isCheck()){
            setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            double textx = x + width - FontUtil.getStringWidth(setstrg);
            if (textx < x + 13) {
                width += (x + 13) - textx + 1;
            }
        }else if(set.isCombo()){
            height = comboextended ? set.getOptions().size() * (FontUtil.getFontHeight() + 2) + 15 : 15;

            setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            int longest = FontUtil.getStringWidth(setstrg);
            for (String s : set.getOptions()) {
                int temp = FontUtil.getStringWidth(s);
                if (temp > longest) {
                    longest = temp;
                }
            }
            double textx = x + width - longest;
            if (textx < x) {
                width += x - textx + 1;
            }
        }else if(set.isSlider()){
            setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
            String displaymax = "" + Math.round(set.getMax() * 100D)/ 100D;
            if (set.onlyInt()) {
                displaymax = displaymax.replace(".0", "");
            }
            double textx = x + width - FontUtil.getStringWidth(setstrg) - FontUtil.getStringWidth(displaymax) - 4;
            if (textx < x) {
                width += x - textx + 1;
            }
        }
    }

    public void drawScreen(double mouseX, double mouseY, float partialTicks) {}

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return isHovered(mouseX, mouseY);
    }

    public void mouseReleased(double mouseX, double mouseY, int state) {}

    public boolean isHovered(double mouseX, double mouseY)
    {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}

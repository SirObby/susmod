package com.superderp111.susmod.clickgui;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.elements.ModuleButton;
import com.superderp111.susmod.clickgui.util.ColorUtil;
import com.superderp111.susmod.clickgui.util.FontUtil;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

public class Panel {
    public String title;
    public int x;
    public int y;
    private int x2;
    private int y2;
    public int width;
    public int height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList<ModuleButton> Elements = new ArrayList<>();
    public ClickGUI clickgui;

    public Panel(String ititle, int ix, int iy, int iwidth, int iheight, boolean iextended, ClickGUI parent) {
        this.title = ititle;
        this.x = ix;
        this.y = iy;
        this.width = iwidth;
        this.height = iheight;
        this.extended = iextended;
        this.dragging = false;
        this.visible = true;
        this.clickgui = parent;
        setup();
    }

    public void setup() {}
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MatrixStack matrices = new MatrixStack();
        if (!this.visible)
            return;

        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }

        Color temp = ColorUtil.getClickGUIColor().darker();
        int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();

        Gui.drawRect(x, y, x + width, y + height, ColorUtil.getClickGUIColor().darker().getRGB()); //Main category tab
        //Gui.drawRect(x + 4,			y + 2, (int) (x + 4.3), 		y + height - 2, 0xffaaaaaa);
        //Gui.drawRect(x - 4 + width, y + 2, (int) (x - 4.3 + width), y + height - 2, 0xffaaaaaa);
        Gui.drawRect(x, y + height - 2, x + width, y + height, ColorUtil.getClickGUIColor().darker().darker().getRGB());
        FontUtil.drawTotalCenteredStringWithShadow(matrices, title, x + width / 2, y + height / 2 + 1, 0xffefefef);

        if (this.extended && !Elements.isEmpty()) {
            int startY = y + height;
            int epanelcolor = 0xff040404;
            for (ModuleButton et : Elements) {
                //Gui.drawRect(x - 2, startY, x + width, startY + et.height + 1, outlineColor); //SIDE COLOR BAR
                Gui.drawRect(x, 	startY, x + width, startY + et.height + 1, epanelcolor);
                et.x = x + 2;
                et.y = startY;
                et.width = width - 4;
                et.drawScreen(mouseX, mouseY, partialTicks);
                startY += et.height + 1;
            }
            Gui.drawRect(x, startY + 1, x + width, startY + 1, epanelcolor);

        }
    }


    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            x2 = (int) (this.x - mouseX);
            y2 = (int) (this.y - mouseY);
            dragging = true;
            return true;
        } else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
            extended = !extended;
            return true;
        } else if (extended) {
            for (ModuleButton et : Elements) {
                if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
                    return true;
                }
            }
        }
        return false;
    }


    public void mouseReleased(double mouseX, double mouseY, int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }


    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}

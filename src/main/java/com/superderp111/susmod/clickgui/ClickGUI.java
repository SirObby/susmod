package com.superderp111.susmod.clickgui;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.elements.Element;
import com.superderp111.susmod.clickgui.elements.ModuleButton;
import com.superderp111.susmod.clickgui.elements.components.ElementSlider;
import com.superderp111.susmod.clickgui.settings.SettingsManager;
import com.superderp111.susmod.clickgui.util.ColorUtil;
import com.superderp111.susmod.clickgui.util.FontUtil;
import com.superderp111.susmod.module.Category;
import com.superderp111.susmod.module.Module;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ClickGUI extends Screen {
    public static ArrayList<Panel> panels;
    public static ArrayList<Panel> rpanels;
    public SettingsManager setmgr;

    public ClickGUI() {
        super(Text.of("ClickGUI"));
        FontUtil.setupFontUtils();
        setmgr = SusMod.settingsManager;

        panels = new ArrayList<>();
        int pwidth = 80;
        int pheight = 15;
        int px = 10;
        int py = 10;
        int pyplus = pheight + 10;


        for (Category c : Category.values()) {
            String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
            panels.add(new Panel(title, px, py, pwidth, pheight, false, this) {
                @Override
                public void setup() {
                    for (Module m : SusMod.moduleManager.getModules()) {
                        if (!m.getCategory().equals(c))
                            continue;
                        this.Elements.add(new ModuleButton(m, this));
                    }
                }
            });
            py += pyplus;
        }

        rpanels = new ArrayList<Panel>();
        for (Panel p : panels) {
            rpanels.add(p);
        }
        Collections.reverse(rpanels);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {

        if(FontUtil.fontRenderer == null) {
            FontUtil.setupFontUtils();
        }

        for (Panel p : panels) {
            p.drawScreen(mouseX, mouseY, partialTicks);
        }

        for (Panel panel : panels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (ModuleButton b : panel.Elements) {
                    if (b.extended && b.menuelements != null && !b.menuelements.isEmpty()) {
                        int off = 0;
                        Color temp = ColorUtil.getClickGUIColor().darker();
                        int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();

                        for (Element e : b.menuelements) {
                            e.offset = off;
                            e.update();

                            //Gui.drawRect(e.x, e.y, (int)(e.x + e.width + 2), (int)(e.y + e.height), outlineColor); //

                            e.drawScreen(mouseX, mouseY, partialTicks);
                            off += e.height;
                        }
                    }
                }
            }
        }

        super.render(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

        for (Panel panel : rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (ModuleButton b : panel.Elements) {
                    if (b.extended) {
                        for (Element e : b.menuelements) {
                            if (e.mouseClicked(mouseX, mouseY, mouseButton))
                                return true;
                        }
                    }
                }
            }
        }

        for (Panel p : rpanels) {
            if (p.mouseClicked(mouseX, mouseY, mouseButton))
                return true;
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {

        for (Panel panel : rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (ModuleButton b : panel.Elements) {
                    if (b.extended) {
                        for (Element e : b.menuelements) {
                            e.mouseReleased(mouseX, mouseY, state);
                        }
                    }
                }
            }
        }

        for (Panel p : rpanels) {
            p.mouseReleased(mouseX, mouseY, state);
        }

        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void init() {
        /*
         * Start blur
         *
        if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (mc.entityRenderer.theShaderGroup != null) {
                mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
         */
    }

    //@Override
    public void onClose() {
        /*
         * End blur
         *
        if (mc.entityRenderer.theShaderGroup != null) {
            mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            mc.entityRenderer.theShaderGroup = null;
        }*
         *
         * Sliderfix
         */
        for (Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (ModuleButton b : panel.Elements) {
                    if (b.extended) {
                        for (Element e : b.menuelements) {
                            if(e instanceof ElementSlider){
                                ((ElementSlider)e).dragging = false;
                            }
                        }
                    }
                }
            }
        }

        SusMod.saveConfig(SusMod.configPath);

        //super.onClose();
    }

    public void closeAllSettings() {
        for (Panel p : rpanels) {
            if (p != null && p.visible && p.extended && p.Elements != null
                    && p.Elements.size() > 0) {
                for (ModuleButton e : p.Elements) {
                    e.extended = false;
                }
            }
        }
    }
}

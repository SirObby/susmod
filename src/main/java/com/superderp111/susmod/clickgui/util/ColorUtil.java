package com.superderp111.susmod.clickgui.util;

import com.superderp111.susmod.SusMod;

import java.awt.*;

public class ColorUtil {

    public static Color getClickGUIColor(){
        return new Color((int) SusMod.settingsManager.getSettingByName("Color Red").getValDouble(),
                (int)SusMod.settingsManager.getSettingByName("Color Green").getValDouble(),
                (int)SusMod.settingsManager.getSettingByName("Color Blue").getValDouble());
    }
}

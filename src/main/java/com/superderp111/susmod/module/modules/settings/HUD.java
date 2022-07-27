package com.superderp111.susmod.module.modules.settings;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.module.Category;
import com.superderp111.susmod.module.Module;

import java.util.ArrayList;

public class HUD extends Module {

    public HUD() {
        super("HUD", Category.SETTINGS);
    }

    @Override
    public void setup() {
        SusMod.settingsManager.rSetting(new Setting("ArrayList", this, true));
        SusMod.settingsManager.rSetting(new Setting("Name", this, true));
        SusMod.settingsManager.rSetting(new Setting("Coords", this, false));
        SusMod.settingsManager.rSetting(new Setting("FPS", this, false));
        SusMod.settingsManager.rSetting(new Setting("Ping", this, false));
        SusMod.settingsManager.rSetting(new Setting("BPS", this, false));
        SusMod.settingsManager.rSetting(new Setting("Color Red", this, 255, 0, 255, true));
        SusMod.settingsManager.rSetting(new Setting("Color Green", this, 0, 0, 255, true));
        SusMod.settingsManager.rSetting(new Setting("Color Blue", this, 255, 0, 255, true));
    }
}

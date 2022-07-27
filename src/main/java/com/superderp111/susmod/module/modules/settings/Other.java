package com.superderp111.susmod.module.modules.settings;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.module.Category;
import com.superderp111.susmod.module.Module;

import java.util.ArrayList;

public class Other extends Module {
    public Other() { super("Other", Category.SETTINGS); }

    @Override
    public void setup() {
        SusMod.settingsManager.rSetting(new Setting("ClickGUI Sound", this, false));
        SusMod.settingsManager.rSetting(new Setting("SusChat", this, true));
        SusMod.settingsManager.rSetting(new Setting("Break Chat", this, false));
    }
}

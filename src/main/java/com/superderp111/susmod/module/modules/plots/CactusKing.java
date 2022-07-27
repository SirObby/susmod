package com.superderp111.susmod.module.modules.plots;

import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.module.Category;
import com.superderp111.susmod.module.Module;

public class CactusKing extends Module {

    public CactusKing() {
        super("CactusKing", Category.PLOTS);
    }

    @Override
    public void setup() {
        SusMod.settingsManager.rSetting(new Setting("AntiDisconnect", this, false));
        SusMod.settingsManager.rSetting(new Setting("Cactus Counter", this, true));
    }
}

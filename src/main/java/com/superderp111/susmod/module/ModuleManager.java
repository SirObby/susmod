package com.superderp111.susmod.module;

import com.superderp111.susmod.module.modules.plots.CactusKing;
import com.superderp111.susmod.module.modules.settings.HUD;
import com.superderp111.susmod.module.modules.settings.Other;

import java.util.ArrayList;

public class ModuleManager {
    private static ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // PLAYER
        modules.add(new Module("Flight", Category.PLAYER));

        //PLOTS
        modules.add(new CactusKing());

        // SETTINGS
        modules.add(new HUD());
        modules.add(new Other());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public static Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

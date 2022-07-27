package com.superderp111.susmod.module;

import net.minecraft.client.MinecraftClient;

public class Module {
    protected MinecraftClient mc = MinecraftClient.getInstance();

    private String name, displayName;
    private boolean toggled;
    private Category category;

    public Module(String name, Category category) {
        this.name = name;
        this.toggled = false;
        this.category = category;
        setup();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onToggle() {}

    public void toggle() {
        if(this.category == Category.SETTINGS) {
            return;
        }
        toggled = !toggled; 
        onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggled() {
        return toggled;
    }

    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setup() {}
}

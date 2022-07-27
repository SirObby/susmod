package com.superderp111.susmod;

import com.google.gson.*;
import com.superderp111.susmod.clickgui.ClickGUI;
import com.superderp111.susmod.clickgui.Panel;
import com.superderp111.susmod.clickgui.settings.Setting;
import com.superderp111.susmod.clickgui.settings.SettingsManager;
import com.superderp111.susmod.command.CommandLoader;
import com.superderp111.susmod.module.Module;
import com.superderp111.susmod.server.Client;
import com.superderp111.susmod.server.SusModServer;
import com.superderp111.susmod.module.ModuleManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SusMod implements ModInitializer {

    public static SettingsManager settingsManager;
    public static ModuleManager moduleManager;
    public static ClickGUI clickGui;

    public static String name;

    public static List<String> otherSus;

    public static Gson gson;
    public static JsonParser jsonParser;

    public static String prefix;
    public static String chatPrefix;

    public static String configPath;

    public static CommandLoader cmdLoader;

    @Override
    public void onInitialize() {
        otherSus = new ArrayList<String>();

        name = "SusMod";

        System.out.println("SUSMOD!! SO SUSSY!!");

        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        clickGui = new ClickGUI();
        cmdLoader = new CommandLoader();

        try {
            cmdLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        gson = new GsonBuilder().setPrettyPrinting().create();
        jsonParser = new JsonParser();

        prefix = "§5[§dSUS§5] §r";
        chatPrefix = "§5[§dSusChat§5] §r";

        initKeybinds();

        Client.load();

        initConfig();
    }

    public void initKeybinds() {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "ClickGUI",      // Keybind Name
                InputUtil.Type.KEYSYM,      // Input type
                GLFW.GLFW_KEY_RIGHT_SHIFT,  // Default bind
                "Susmod"            // Category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(clickGui);
            }
        });
    }

    public void initConfig() {
        configPath = null;
        try {
            configPath = new File(".").getCanonicalPath() + "/SusMod";
            File dir = new File(configPath);
            dir.mkdir();
            File configFile = new File(configPath + "/config.json");
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadConfig(configPath);
    }

    public static void saveConfig(String path) {
        Map<String, Object> map = new HashMap<>();

        ArrayList<String> modules = new ArrayList<>();
        for (Module module : moduleManager.getModules()) {
            if (module.isToggled()) {
                modules.add(module.getName());
            }
        }
        Map<String, Object> settings = new HashMap<>();
        for (Setting setting : settingsManager.getSettings()) {
            if (setting.isSlider())
                settings.put(setting.getName(), setting.getValDouble());
            if (setting.isCombo())
                settings.put(setting.getName(), setting.getValString());
            if (setting.isCheck())
                settings.put(setting.getName(), setting.getValBoolean());
        }
        Map<String, Object> panels = new HashMap<>();
        for (Panel panel : clickGui.panels) {
            panels.put(panel.title, new Integer[] {panel.x, panel.y});
        }

        map.put("modules", modules);
        map.put("settings", settings);
        map.put("panels", panels);

        try {
            File configFile = new File(path + "/config.json");
            configFile.createNewFile();
            Writer writer = new FileWriter(configFile, false);
            gson.toJson(map, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // config code
    public void loadConfig(String path) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path + "/config.json"));
            Map<?, ?> json = gson.fromJson(reader, Map.class);

            if (json != null) {
                for (Map.Entry<?, ?> entry : json.entrySet()) {
                    if (entry.getKey().equals("modules")) {
                        ArrayList<String> enabledModules = (ArrayList<String>) entry.getValue();
                        for (Module module : SusMod.moduleManager.getModules()) {
                            if (enabledModules.contains(module.getName())) {
                                if (!module.isToggled()) {
                                    module.toggle();
                                }
                            } else {
                                if (module.isToggled()) {
                                    module.toggle();
                                }
                            }
                        }
                    }
                    if (entry.getKey().equals("settings")) {
                        Map<?, ?> settingsMap = (Map<?, ?>) entry.getValue();
                        for (Map.Entry<?, ?> settingEntry : settingsMap.entrySet()) {
                            SettingsManager setMgr = SusMod.settingsManager;
                            String setName = (String) settingEntry.getKey();
                            if (setMgr.getSettingByName(setName) != null) {
                                if (setMgr.getSettingByName(setName).isCheck())
                                    setMgr.getSettingByName(setName).setValBoolean(Boolean.parseBoolean(String.valueOf(settingEntry.getValue())));
                                if (setMgr.getSettingByName(setName).isCombo())
                                    setMgr.getSettingByName(setName).setValString((String) settingEntry.getValue());
                                if (setMgr.getSettingByName(setName).isSlider())
                                    setMgr.getSettingByName(setName).setValDouble(Double.parseDouble(String.valueOf(settingEntry.getValue())));
                            }
                            else {
                                // TODO: Remove from config
                            }
                        }
                    }
                    if (entry.getKey().equals("panels")) {
                        Map<?, ?> panelMap = (Map<?, ?>) entry.getValue();
                        for (Map.Entry<?, ?> panelEntry : panelMap.entrySet()) {
                            String panelName = (String) panelEntry.getKey();
                            ArrayList panelCoords = (ArrayList) panelEntry.getValue();
                            Panel panel = null;
                            for (Panel p : clickGui.rpanels) {
                                if (p.title.equals(panelName)) {
                                    panel = p;
                                    break;
                                }
                            }
                            panel.x = (int) Double.parseDouble(String.valueOf(panelCoords.get(0)));
                            panel.y = (int) Double.parseDouble(String.valueOf(panelCoords.get(1)));
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

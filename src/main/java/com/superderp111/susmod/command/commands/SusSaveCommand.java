package com.superderp111.susmod.command.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.clickgui.ClickGUI;
import com.superderp111.susmod.command.Command;
import com.superderp111.susmod.server.Client;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.reflect.FieldUtils;

public class SusSaveCommand extends Command {
    @Override
    public void register(MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) throws IllegalAccessException {
        cd.register(ClientCommandManager.literal("sussave").executes(ctx -> {
            SusMod.saveConfig(SusMod.configPath);
            return 0;
        })
        );
    }

}

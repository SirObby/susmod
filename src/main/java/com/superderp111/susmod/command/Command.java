package com.superderp111.susmod.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
//import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.List;

public abstract class Command {

    public static List<String> aliases = Lists.newArrayList();

    public abstract void register(MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) throws NoSuchFieldException, IllegalAccessException;
}

package com.superderp111.susmod.command.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.superderp111.susmod.SusMod;
import com.superderp111.susmod.command.Command;
import com.superderp111.susmod.server.Client;
import com.superderp111.susmod.util.ChatUtil;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class SusChatCommand extends Command {
    @Override
    public void register(MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) throws IllegalAccessException {
        this.aliases = Lists.newArrayList("sc", "schat");
        LiteralCommandNode node = registerMain(cd);
        for (String alias : aliases) {
            Object aliasCmd = cd.register(LiteralArgumentBuilder.literal(alias));
            FieldUtils.writeField(aliasCmd, "redirect", node, true);
        }
    }

    public LiteralCommandNode registerMain(CommandDispatcher cd) {
        return cd.register(ClientCommandManager.literal("suschat")
                .then(ClientCommandManager.argument("message", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String message = ctx.getArgument("message", String.class);
                            Client.sendMessage(message);
                            return 1;
                        })
        ));
    }

}

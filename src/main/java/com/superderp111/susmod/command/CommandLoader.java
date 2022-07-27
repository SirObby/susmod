package com.superderp111.susmod.command;

import com.superderp111.susmod.command.commands.SusChatCommand;
import com.superderp111.susmod.util.ILoader;
//import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class CommandLoader implements ILoader {
    @Override
    public void load() throws NoSuchFieldException, IllegalAccessException {
        register(new SusChatCommand());
        //register(new SusGiveCommand());
    }

    public static void register(Command cmd) throws NoSuchFieldException, IllegalAccessException {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            try {
                cmd.register(MinecraftClient.getInstance(), dispatcher);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void register(Command... cmds) throws NoSuchFieldException, IllegalAccessException {
        for (Command cmd : cmds) {
            register(cmd);
        }
    }
}

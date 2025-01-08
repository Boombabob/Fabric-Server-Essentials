package com.boombabob.fabricserveressentials.commands;

import com.boombabob.fabricserveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class RestartCommand implements ISECommand {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        if (Main.CONFIG.restartCommandEnabled) {
            dispatcher.register(literal("restart")
                .requires(Permissions.require("%s.restart".formatted(Main.MODID)))
                .requires(source -> source.hasPermissionLevel(4))
                .executes(context -> restart()));
        }
    }

    public static int restart() {
        Main.CONFIG.shouldRestartAutomatically = true;
        Main.getServer().stop(false);
        return Command.SINGLE_SUCCESS;
    }
}

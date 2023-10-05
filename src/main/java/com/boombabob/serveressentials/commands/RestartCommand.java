package com.boombabob.serveressentials.commands;

import com.boombabob.serveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RestartCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("restart")
            .executes(context -> restart())
            .then(literal("on shutdown")
                .then(argument("True/False", bool()))
                    .executes(context -> {
                        Main.CONFIG.shouldRestartAutomatically = getBool(context, "True/False");
                        Main.CONFIG.save();
                        return Command.SINGLE_SUCCESS;
                    })));
    }

    public static int restart() {
        Main.CONFIG.shouldRestartAutomatically = true;
        Main.getServer().stop(false);
        return Command.SINGLE_SUCCESS;
    }
}

package com.boombabob.serveressentials.commands;

import com.boombabob.serveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class InfoCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("info")
            .executes(context ->
            {
                context.getSource().sendFeedback(() -> Text.literal(Main.CONFIG.serverInfo), false);
                return Command.SINGLE_SUCCESS;
            }));
    };
}

package com.boombabob.serveressentials.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;
public class PingCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("ping")
                .requires(ServerCommandSource::isExecutedByPlayer)
                .executes(context ->
                {
                    ServerCommandSource source = context.getSource();
                    ServerPlayerEntity player = source.getPlayer();
                    source.sendFeedback(() -> Text.literal("Your ping is: " + player.pingMilliseconds + " ms"), false);
                    return Command.SINGLE_SUCCESS;
                }));
    };
}

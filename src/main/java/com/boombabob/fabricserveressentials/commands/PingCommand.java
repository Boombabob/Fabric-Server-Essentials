package com.boombabob.fabricserveressentials.commands;

import com.boombabob.fabricserveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class PingCommand implements ISECommand {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        if (Main.CONFIG.pingCommandEnabled) {
            dispatcher.register(literal("ping")
                .requires(Permissions.require("%s.ping".formatted(Main.MODID)))
                .requires(ServerCommandSource::isExecutedByPlayer)
                .executes(context ->
                {
                    ServerCommandSource source = context.getSource();
                    ServerPlayNetworkHandler playerNetworkHandler = source.getPlayer().networkHandler;
                    source.sendFeedback(() -> Text.literal("Your ping is: " + playerNetworkHandler.getLatency() + " ms"), false);
                    return Command.SINGLE_SUCCESS;
                }));
        }
    }
}

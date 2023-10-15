package com.boombabob.fabricserveressentials.commands;

import com.boombabob.fabricserveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class InfoCommand implements ISECommand{
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("info")
            .requires(Permissions.require("%s.info".formatted(Main.MODID)))
            .executes(context ->
            {
                context.getSource().sendFeedback(() -> Text.literal(Main.CONFIG.serverInfo), false);
                return Command.SINGLE_SUCCESS;
            }));
    }
}

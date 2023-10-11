package com.boombabob.serveressentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface ISECommand {
    void register(CommandDispatcher<ServerCommandSource> dispatcher);
}

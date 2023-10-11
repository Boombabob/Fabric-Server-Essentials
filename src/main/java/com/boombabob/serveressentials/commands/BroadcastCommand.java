package com.boombabob.serveressentials.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.command.argument.ColorArgumentType.color;
import static net.minecraft.command.argument.ColorArgumentType.getColor;
import static net.minecraft.server.command.CommandManager.argument;

public class BroadcastCommand implements ISECommand {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("broadcast")
            .requires(source -> source.hasPermissionLevel(2))
                .then(argument("color", color())
                    .then(argument("message", greedyString())
                        .executes(context ->
                        {
                            PlayerManager playerManager = context.getSource().getServer().getPlayerManager();
                            World world = context.getSource().getWorld();
                            playerManager.broadcast(
                                Text.literal(getString(context, "message"))
                                    .formatted(getColor(context, "color")),
                                true
                            );
                            for (ServerPlayerEntity player : playerManager.getPlayerList()) {
                                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1f, 1f);
                            }
                            return Command.SINGLE_SUCCESS;
                        }))));
    }
}

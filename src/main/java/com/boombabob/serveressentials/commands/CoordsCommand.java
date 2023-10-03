package com.boombabob.serveressentials.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

import static net.minecraft.command.argument.EntityArgumentType.getPlayers;
import static net.minecraft.command.argument.EntityArgumentType.players;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CoordsCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        final LiteralCommandNode<ServerCommandSource> coordsCommandNode = dispatcher.register(literal("coords"));
        dispatcher.register(literal("coords")
            .requires(ServerCommandSource::isExecutedByPlayer)
            .requires(source -> source.getServer().isDedicated())
            .executes(context -> sendCoords(
                    context.getSource().getPlayer(),
                    context.getSource().getServer().getPlayerManager().getPlayerList()
            ))
                .then(argument("Recipients", players())
                    .executes(context -> sendCoords(
                            context.getSource().getPlayer(),
                            getPlayers(context, "Recipients")
                    ))
                        .redirect(coordsCommandNode)));
        dispatcher.register(literal("c").redirect(coordsCommandNode));
    }

    public static int sendCoords(ServerPlayerEntity sender, Collection<ServerPlayerEntity> recipients) {
        String senderName = sender.getName().toString();
        senderName = senderName.substring(8, senderName.length() - 1);
        String senderDimension = sender.getServerWorld().getRegistryKey().getValue().toString();
        senderDimension = senderDimension.substring(senderDimension.indexOf(":") + 1);
        int senderX = (int) sender.getX();
        int senderY = (int) sender.getY();
        int senderZ = (int) sender.getZ();
        String sendingPlayerPos = "%s is at %d, %d, %d in the %s"
            .formatted(senderName, senderX, senderY, senderZ, senderDimension);
        for (ServerPlayerEntity recipient : recipients) {
            recipient.sendMessage(Text.literal(sendingPlayerPos), false);
        }
        return Command.SINGLE_SUCCESS;
    }
}

package com.boombabob.fabricserveressentials.commands;

import com.boombabob.fabricserveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

import static net.minecraft.command.argument.EntityArgumentType.getPlayers;
import static net.minecraft.command.argument.EntityArgumentType.players;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CoordsCommand implements ISECommand {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        if (Main.CONFIG.coordsCommandEnabled) {
            final LiteralCommandNode<ServerCommandSource> coordsCommandNode = dispatcher.register(literal("coords"));
            // this for loop is necessary in order for the default argument (sending to all players) to work for both aliases
            String[] coordsAliases = {"coords", "c"};
            for (String coordsAlias : coordsAliases) {
                dispatcher.register(literal(coordsAlias)
                    .requires(Permissions.require("%s.coords".formatted(Main.MODID)))
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .requires(source -> source.getServer().isDedicated())
                    .executes(context -> sendCoords(
                        context.getSource().getPlayer(),
                        context.getSource().getServer().getPlayerManager().getPlayerList()
                    ))
                    .then(argument("recipients", players())
                        .executes(context -> sendCoords(
                            context.getSource().getPlayer(),
                            getPlayers(context, "recipients")
                        ))
                        .redirect(coordsCommandNode)));
            }
        }
    }

    public static int sendCoords(ServerPlayerEntity sender, Collection<ServerPlayerEntity> recipients) {
        String senderDimension = sender.getServerWorld().getRegistryKey().getValue().toString();
        senderDimension = senderDimension.substring(senderDimension.indexOf(":") + 1);
        int senderX = (int) sender.getX();
        int senderY = (int) sender.getY();
        int senderZ = (int) sender.getZ();
        String sendingPlayerPos = "I am at %d, %d, %d in the %s"
            .formatted(senderX, senderY, senderZ, senderDimension);
        for (ServerPlayerEntity recipient : recipients) {
            if (recipient != sender) {
                recipient.sendChatMessage(SentMessage.of(
                    SignedMessage.ofUnsigned(sendingPlayerPos)),
                    false,
                    MessageType.params(MessageType.MSG_COMMAND_INCOMING, sender.getCommandSource())
                );
            }
            sender.sendChatMessage(SentMessage.of(
                SignedMessage.ofUnsigned(sendingPlayerPos)),
                false,
                MessageType.params(MessageType.MSG_COMMAND_OUTGOING, recipient.getCommandSource())
                    .withTargetName(recipient.getDisplayName())
            );
        }
        return Command.SINGLE_SUCCESS;
    }
}

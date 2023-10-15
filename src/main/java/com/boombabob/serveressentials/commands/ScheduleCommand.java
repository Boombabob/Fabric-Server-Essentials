package com.boombabob.serveressentials.commands;

import com.boombabob.serveressentials.CommandScheduler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ScheduleCommand implements ISECommand {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        final LiteralCommandNode<ServerCommandSource> scheduleCommandNode = dispatcher.register(literal("scheduleCommand")
            .requires(source -> source.hasPermissionLevel(4))
                .then(literal("set")
                    .then(argument("hour", integer(0, 23))
                        .then(argument("minute", integer(0, 59))
                            .then(argument("command", greedyString())
                                .executes(context -> CommandScheduler.scheduleNew(
                                    context,
                                    getString(context, "command"),
                                    getInteger(context, "hour"),
                                    getInteger(context, "minute")
                                ))))))
                .then(literal("list")
                        .executes(CommandScheduler::listScheduledCommands))
                .then(literal("remove")
                    .then(argument("hour", integer(0, 23))
                        .then(argument("minute", integer(0, 59))
                            .executes(context -> CommandScheduler.removeCommand(
                                context,
                                getInteger(context, "hour"),
                                getInteger(context, "minute")
                            ))
                .then(literal("reload")
                    .executes(context -> CommandScheduler.reload()))))));

        dispatcher.register(literal("schCmd").redirect(scheduleCommandNode));
    }
}

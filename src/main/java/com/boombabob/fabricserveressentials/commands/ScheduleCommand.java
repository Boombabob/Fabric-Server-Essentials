package com.boombabob.fabricserveressentials.commands;

import com.boombabob.fabricserveressentials.CommandScheduler;
import com.boombabob.fabricserveressentials.Main;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ScheduleCommand implements ISECommand {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        if (Main.CONFIG.scheduleCommandEnabled) {
            final LiteralCommandNode<ServerCommandSource> scheduleCommandNode = dispatcher.register(literal("scheduleCommand")
                .requires(Permissions.require("%s.scheduleCommand".formatted(Main.MODID)))
                .requires(source -> source.hasPermissionLevel(4))
                .then(literal("set")
                    .then(argument("repeats", bool())
                        .then(argument("hour", integer(0, 23))
                            .then(argument("minute", integer(0, 59))
                                .then(argument("command", greedyString())
                                    .executes(context -> CommandScheduler.scheduleNew(
                                        context,
                                        getString(context, "command"),
                                        getInteger(context, "hour"),
                                        getInteger(context, "minute"),
                                        getBool(context, "repeats")
                                    )))))))
                .then(literal("list")
                    .executes(CommandScheduler::listScheduledCommands))
                .then(literal("remove")
                    .then(argument("hour", integer(0, 23))
                        .then(argument("minute", integer(0, 59))
                            .executes(context -> CommandScheduler.removeCommand(
                                context,
                                getInteger(context, "hour"),
                                getInteger(context, "minute")
                            )))))
                .then(literal("reload")
                    .executes(context -> {
                        CommandScheduler.reload();
                        context.getSource().sendFeedback(() -> Text.literal("Scheduler Reloaded"), true);
                        return Command.SINGLE_SUCCESS;
                    })));
            dispatcher.register(literal("schCmd")
                    .requires(Permissions.require("%s.scheduleCommand".formatted(Main.MODID)))
                    .requires(source -> source.hasPermissionLevel(4))
                    .redirect(scheduleCommandNode));
        }
    }
}

package com.boombabob.serveressentials;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandScheduler {
    // Create scheduler
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Schedules commands saved to the configuration file specified by the SEConfig instance in Main.java
     * @throws DateTimeParseException When date specified in the config is invalid
     */
    public static void scheduleSaved() {
        for (Map.Entry<String,String> entry : Main.CONFIG.scheduledTasks.entrySet()) {
            // Makes sure the server is actually running (just in case)
            if (Main.getServer() != null) {
                try {
                    schedule(entry.getValue(), LocalTime.parse(entry.getKey()));
                } catch (DateTimeParseException dateTimeParseException) {
                    Main.LOGGER.warn("Invalid date specified in %i file, unable to schedule that command");
                }
            }
        }
    };

    /**
     * Schedules a new command that is first scheduled with the scheduler, and then saved to the config
     * @param command command to be scheduled to be executed in the future
     * @param hour hour of the day the command will be executed
     * @param minute minute of that hour the command will be executed
     * @return Command.SINGLE_SUCCESS (==1), or 0 if error occurred
     */
    public static int scheduleNew(CommandContext<ServerCommandSource> context, String command, int hour, int minute) {
        LocalTime targetTime;
        try {
            targetTime = LocalTime.of(hour, minute);
        } catch (DateTimeException dateTimeException) {
            context.getSource().sendFeedback(() ->
                Text.literal("Invalid time specified when executing schedule command, could not be scheduled"),
                true
            );
            return 0;
        }
        if (Main.CONFIG.scheduledTasks.containsKey(targetTime.toString())) {
            context.getSource().sendFeedback(() ->
                Text.literal("%s replaced on schedule".formatted(Main.CONFIG.scheduledTasks.get(targetTime.toString()))),
                true);
        }
        schedule(command, targetTime);
        Main.CONFIG.scheduledTasks.put(targetTime.toString(), command);
        Main.CONFIG.save();
        context.getSource().sendFeedback(() -> Text.literal("Command successfully scheduled"), true);
        return Command.SINGLE_SUCCESS;
    };

    /**
     * Schedules command to the scheduler at specified time
     * @param command Command to be executed
     * @param targetTime Time for the command to be executed
     */
    public static void schedule(String command, LocalTime targetTime) {
        LocalTime currentTime = LocalTime.now();
        long time_until = currentTime.until(targetTime, ChronoUnit.MINUTES);
        if (time_until < 0) {
            time_until += 1440;
        }
        scheduler.scheduleAtFixedRate(
            () -> runCommand(command),
            time_until,
            1440,
            TimeUnit.MINUTES
        );
    }

    /**
     * Runs command on the minecraft server
     * @param command Command to be executed
     */
    public static void runCommand(String command) {
        Main.getServer().getCommandManager().executeWithPrefix(Main.getServer().getCommandSource(), command);
    };

    public static int listScheduledCommands(CommandContext<ServerCommandSource> context) {
        for (Map.Entry<String,String> entry : Main.CONFIG.scheduledTasks.entrySet()) {
            context.getSource().sendFeedback(() ->
                Text.literal(entry.getKey()).formatted(Formatting.AQUA)
                    .append(Text.literal(" - " + entry.getValue()).formatted(Formatting.WHITE)),
                false
            );
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int removeCommand(CommandContext<ServerCommandSource> context, int hour, int minute) {
        String time = LocalTime.of(hour, minute).toString();
        if (Main.CONFIG.scheduledTasks.containsKey(time)) {
            context.getSource().sendFeedback(() -> Text.literal(Main.CONFIG.scheduledTasks.get(time) + " removed"), true);
            Main.CONFIG.scheduledTasks.remove(time);
            return Command.SINGLE_SUCCESS;
        }
        context.getSource().sendFeedback(() -> Text.literal("Specified time does not exist"), true);
        return 0;
    }
}

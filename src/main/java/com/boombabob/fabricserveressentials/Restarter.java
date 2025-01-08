package com.boombabob.fabricserveressentials;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;

public class Restarter {
    public static void restart() {
        if (Main.CONFIG.shouldRestartAutomatically) {
            String command = null;
            // Make sure user has not specified a different restart argument without the default path
            if (Main.CONFIG.restartArgument.contains("%s")) {
                // Look for .jar files (aka the minecraft server), and just use first one found.
                File[] jarFiles = Main.getServer().getRunDirectory().toFile().listFiles((dir1, name) -> name.endsWith("jar"));
                if (jarFiles == null || jarFiles.length == 0) {
                    Main.LOGGER.error("Jar file not found, try replacing %s in the config with the file path");
                } else {
                    command = Main.CONFIG.restartArgument.formatted(jarFiles[0]);
                }
            } else {
                command = Main.CONFIG.restartArgument;
            }
            if (command != null) {
                try {
                    Main.LOGGER.info("Running " + command);
                    if (SystemUtils.IS_OS_WINDOWS) {
                        command = "cmd.exe /c start " + command;
                    }
                    Runtime.getRuntime().exec(command);

                } catch (IOException ioException) {
                    Main.LOGGER.error("Error running jar file");
                }
            }
        }
    }
}
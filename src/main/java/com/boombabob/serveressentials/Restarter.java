package com.boombabob.serveressentials;

import java.io.File;
import java.io.IOException;

public class Restarter {
    public static void restart() {
        if (Main.CONFIG.shouldRestartAutomatically) {
            String command = null;
            if (Main.CONFIG.restartArgument.contains("%s")) {
                File[] jarFiles = Main.getServer().getRunDirectory().listFiles((dir1, name) -> name.endsWith("jar"));
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
                    Runtime.getRuntime().exec(command);
                } catch (IOException ioException) {
                    Main.LOGGER.error("Error running jar file");
                }
            }
        }
    }
}
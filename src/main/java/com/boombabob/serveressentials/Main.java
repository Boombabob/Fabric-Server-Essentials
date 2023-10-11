package com.boombabob.serveressentials;

import com.boombabob.serveressentials.commands.*;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final String MODID = "server-essentials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static MinecraftServer Server;
	public static final SEConfig CONFIG = OmegaConfig.register(SEConfig.class);

	@Override
	public void onInitialize() {
		// Gets the minecraft server instance, useful for many different purposes
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {Server = server; CommandScheduler.scheduleSaved();});
		// Register commands
		ISECommand[] commandClassList = {
			new BroadcastCommand(),
			new CoordsCommand(),
			new InfoCommand(),
			new PingCommand(),
			new RestartCommand(),
			new ScheduleCommand()
		};
		for (ISECommand command : commandClassList) {
			CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> command.register(dispatcher));
		}
		Main.LOGGER.info("Essentials Initialised");
	}

	public static MinecraftServer getServer() {
		return Server;
	}
	public static void setServer(MinecraftServer server) {
		Server = server;
    }
}
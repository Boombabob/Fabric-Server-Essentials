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
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> PingCommand.register(dispatcher));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> InfoCommand.register(dispatcher));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ScheduleCommand.register(dispatcher));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> BroadcastCommand.register(dispatcher));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CoordsCommand.register(dispatcher));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> RestartCommand.register(dispatcher));

		Main.LOGGER.info("Essentials Initialised");
	}

	public static MinecraftServer getServer() {
		return Server;
	}
	public static void setServer(MinecraftServer server) {
		Server = server;
    }
}
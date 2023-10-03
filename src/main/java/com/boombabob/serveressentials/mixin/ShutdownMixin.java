package com.boombabob.serveressentials.mixin;

import com.boombabob.serveressentials.CommandScheduler;
import com.boombabob.serveressentials.Main;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ShutdownMixin {

	@Inject(at = @At("HEAD"), method = "shutdown")
	private void init(CallbackInfo info) {
		CommandScheduler.scheduler.shutdown();
		Main.LOGGER.info("Shutting down scheduler");
	}

}
package com.boombabob.fabricserveressentials.mixin;

import com.boombabob.fabricserveressentials.CommandScheduler;
import com.boombabob.fabricserveressentials.Main;
import com.boombabob.fabricserveressentials.Restarter;
import com.boombabob.fabricserveressentials.SEConfig;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ShutdownMixin {
	@Inject(at = @At("TAIL"), method = "shutdown")
	private void init(CallbackInfo info) {
		CommandScheduler.scheduler.shutdown();
		Main.LOGGER.info("Shutting down scheduler");
		SEConfig.HANDLER.save();
		Restarter.restart();
		Main.setServer(null);
	}
}
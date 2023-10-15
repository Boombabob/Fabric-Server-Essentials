package com.boombabob.serveressentials.mixin;

import com.boombabob.serveressentials.CommandScheduler;
import com.boombabob.serveressentials.Main;
import com.boombabob.serveressentials.Restarter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServerWatchdog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServerWatchdog.class)
public class WatchdogMixin {
    @Inject(at = @At("HEAD"), method = "shutdown")
    private void init(CallbackInfo info) {
        Restarter.restart();
    }
}

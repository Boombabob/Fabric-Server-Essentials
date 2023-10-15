package com.boombabob.fabricserveressentials.mixin;

import com.boombabob.fabricserveressentials.Main;
import com.boombabob.fabricserveressentials.Restarter;
import net.minecraft.server.dedicated.DedicatedServerWatchdog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServerWatchdog.class)
public class WatchdogMixin {
    @Inject(at = @At("HEAD"), method = "shutdown")
    private void init(CallbackInfo info) {
        if (Main.CONFIG.shouldRestartAutomatically) {
            Restarter.restart();
        }
    }
}

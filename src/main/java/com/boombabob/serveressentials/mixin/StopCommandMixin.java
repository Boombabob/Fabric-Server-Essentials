package com.boombabob.serveressentials.mixin;

import com.boombabob.serveressentials.Main;
import net.minecraft.server.dedicated.command.StopCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StopCommand.class)
public class StopCommandMixin {
    @Inject(method = "register", at = @At(value = "RETURN"))
    private static void preventRestart(CallbackInfo info) {
        Main.CONFIG.shouldRestartAutomatically = false;
    }
}

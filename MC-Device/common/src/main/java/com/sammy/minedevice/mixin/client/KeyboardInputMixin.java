package com.sammy.minedevice.mixin.client;

import com.sammy.minedevice.item.MegaphoneItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
    @Inject(method = "tick", at = @At("RETURN"))
    private void minedevice$applyPhoneCameraMovement(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || minecraft.player == null) {
            return;
        }

        if (slowDown && minedevice$shouldIgnoreUseSlowdown(minecraft)) {
            this.leftImpulse = minedevice$restoreImpulse(this.leftImpulse, slowDownFactor);
            this.forwardImpulse = minedevice$restoreImpulse(this.forwardImpulse, slowDownFactor);
        }
    }

    @Unique
    private static boolean minedevice$shouldIgnoreUseSlowdown(Minecraft minecraft) {
        return minecraft.player != null && (MegaphoneItem.isUsingMegaphone(minecraft.player));
    }

    @Unique
    private static float minedevice$restoreImpulse(float impulse, float slowDownFactor) {
        if (impulse == 0.0F || slowDownFactor == 0.0F) {
            return impulse;
        }

        return impulse / slowDownFactor;
    }
}

package com.sammy.minedevice.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.sammy.minedevice.client.phone.PhoneScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyMapping;isDown()Z"
            )
    )
    private boolean minedevice$allowSprintWhilePhoneCameraMoves(KeyMapping keyMapping) {
        return keyMapping.isDown();
    }
}

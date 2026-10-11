package com.sammy.minedevice.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.sammy.minedevice.Minedevice;
import com.sammy.minedevice.ModBlockEntities;
import com.sammy.minedevice.ModBlocks;
import com.sammy.minedevice.ModItems;
import com.sammy.minedevice.block.HomePhoneBlock;
import com.sammy.minedevice.client.phone.PhoneClientHooks;
import com.sammy.minedevice.client.phone.PhoneClientCallState;
import com.sammy.minedevice.client.phone.PhoneNetworkingClient;
import com.sammy.minedevice.client.render.LabtopBlockEntityRenderer;
import com.sammy.minedevice.item.HomePhoneHandsetItem;
import com.sammy.minedevice.item.MegaphoneItem;
import com.sammy.minedevice.phone.PhoneCallState;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Method;

public final class MinedeviceClient {
    private static boolean initialized;
    private static boolean propertyRegistered;
    private static boolean homePhoneSneakUseDown;
    private static InteractionHand walkieTalkHand = InteractionHand.MAIN_HAND;
    private static Method megaphoneVoiceTickMethod;
    private static Method megaphoneVoiceShutdownMethod;
    private static boolean megaphoneVoiceAvailable;
    private static Method phoneCallVoiceTickMethod;
    private static Method phoneCallVoiceShutdownMethod;
    private static boolean phoneCallVoiceAvailable;

    private MinedeviceClient() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        registerModelProperties();
        BlockEntityRendererRegistry.register(ModBlockEntities.LABTOP.get(), LabtopBlockEntityRenderer::new);
        PhoneNetworkingClient.init();
        initOptionalVoiceHooks();
        ClientTickEvent.CLIENT_POST.register(MinedeviceClient::onClientTick);
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register((player) -> {
            homePhoneSneakUseDown = false;
            PhoneNetworkingClient.clearClientState();
            PhoneClientHooks.clearMobileCallHotbarLock();
            tickMegaphoneVoice(false);
            tickPhoneCallVoice(false);
        });
        ClientLifecycleEvent.CLIENT_STOPPING.register((client) -> {
            homePhoneSneakUseDown = false;
            PhoneNetworkingClient.clearClientState();
            PhoneClientHooks.clearMobileCallHotbarLock();
            shutdownMegaphoneVoice();
            shutdownPhoneCallVoice();
        });
    }

    private static void onClientTick(Minecraft minecraft) {
        PhoneNetworkingClient.tick(minecraft);
        PhoneClientHooks.tickMobileCallHotbarLock();
        handleHomePhoneSneakUse(minecraft);
        tickMegaphoneVoice(minecraft != null && MegaphoneItem.isUsingMegaphone(minecraft.player));
        tickPhoneCallVoice(isPhoneCallConnected());
    }

    private static boolean isKeyActuallyDown(Minecraft minecraft, KeyMapping keyMapping) {
        if (minecraft == null || keyMapping == null) {
            return false;
        }

        InputConstants.Key inputKey = InputConstants.getKey(keyMapping.saveString());
        if (inputKey.getValue() == InputConstants.UNKNOWN.getValue()) {
            return keyMapping.isDown();
        }

        long windowHandle = minecraft.getWindow().getWindow();
        if (inputKey.getType() == InputConstants.Type.MOUSE) {
            return GLFW.glfwGetMouseButton(windowHandle, inputKey.getValue()) == GLFW.GLFW_PRESS;
        }

        if (inputKey.getType() == InputConstants.Type.KEYSYM) {
            return InputConstants.isKeyDown(windowHandle, inputKey.getValue());
        }

        return keyMapping.isDown();
    }

    private static void handleHomePhoneSneakUse(Minecraft minecraft) {
        if (minecraft == null || minecraft.player == null || minecraft.level == null || minecraft.screen != null) {
            homePhoneSneakUseDown = false;
            return;
        }

        boolean useDown = minecraft.options.keyUse.isDown();
        if (!useDown || !minecraft.player.isShiftKeyDown()) {
            homePhoneSneakUseDown = false;
            return;
        }

        if (homePhoneSneakUseDown) {
            return;
        }

        homePhoneSneakUseDown = true;
        BlockPos targetPos = resolveTargetedHeldHomePhone(minecraft, minecraft.player);
        if (targetPos != null) {
            PhoneNetworkingClient.requestPutDownHomePhoneHandset(targetPos);
        }
    }

    private static BlockPos resolveTargetedHeldHomePhone(Minecraft minecraft, LocalPlayer player) {
        if (!(minecraft.hitResult instanceof BlockHitResult blockHitResult)) {
            return null;
        }

        BlockPos blockPos = blockHitResult.getBlockPos();
        if (!(minecraft.level.getBlockState(blockPos).getBlock() instanceof HomePhoneBlock)) {
            return null;
        }

        if (isHandsetBoundTo(player.getMainHandItem(), minecraft, blockPos)
                || isHandsetBoundTo(player.getOffhandItem(), minecraft, blockPos)) {
            return blockPos;
        }

        return null;
    }

    private static boolean isHandsetBoundTo(ItemStack stack, Minecraft minecraft, BlockPos blockPos) {
        var address = HomePhoneHandsetItem.getBoundAddress(stack);
        return address != null
                && minecraft.level != null
                && address.dimension().equals(minecraft.level.dimension())
                && address.blockPos().equals(blockPos);
    }

    private static void initOptionalVoiceHooks() {
        com.sammy.minedevice.voice.VoiceProvider provider = com.sammy.minedevice.voice.VoiceProviderDetector.detect();

        if (provider == com.sammy.minedevice.voice.VoiceProvider.PLASMO_VOICE) {
            try {
                Class<?> hookClass = Class.forName("com.sammy.minedevice.client.voice.MegaphoneVoiceHook");
                hookClass.getMethod("init").invoke(null);
                megaphoneVoiceTickMethod = hookClass.getMethod("tick", boolean.class);
                megaphoneVoiceShutdownMethod = hookClass.getMethod("shutdown");
                megaphoneVoiceAvailable = true;
                Minedevice.LOGGER.info("Plasmo Voice client hooks initialized.");
            } catch (Throwable throwable) {
                megaphoneVoiceTickMethod = null;
                megaphoneVoiceShutdownMethod = null;
                megaphoneVoiceAvailable = false;
                Minedevice.LOGGER.warn("Unable to initialize Plasmo Voice megaphone client hook", throwable);
            }

            try {
                Class<?> hookClass = Class.forName("com.sammy.minedevice.client.voice.PhoneCallVoiceHook");
                hookClass.getMethod("init").invoke(null);
                phoneCallVoiceTickMethod = hookClass.getMethod("tick", boolean.class);
                phoneCallVoiceShutdownMethod = hookClass.getMethod("shutdown");
                phoneCallVoiceAvailable = true;
            } catch (Throwable throwable) {
                phoneCallVoiceTickMethod = null;
                phoneCallVoiceShutdownMethod = null;
                phoneCallVoiceAvailable = false;
                Minedevice.LOGGER.warn("Unable to initialize Plasmo Voice phone call client hook", throwable);
            }
        } else if (provider == com.sammy.minedevice.voice.VoiceProvider.SIMPLE_VOICE_CHAT) {
            Minedevice.LOGGER.info("Simple Voice Chat detected. Client voice effects are not supported on SVC and will be skipped.");
            megaphoneVoiceAvailable = false;
            phoneCallVoiceAvailable = false;
        } else {
            megaphoneVoiceAvailable = false;
            phoneCallVoiceAvailable = false;
        }
    }

    private static boolean isPhoneCallConnected() {
        return PhoneClientCallState.getState() == PhoneCallState.CONNECTED
                || PhoneClientCallState.hasHomePhoneState(PhoneCallState.CONNECTED);
    }

    private static void tickMegaphoneVoice(boolean shouldEnable) {
        if (!megaphoneVoiceAvailable || megaphoneVoiceTickMethod == null) {
            return;
        }

        try {
            megaphoneVoiceTickMethod.invoke(null, shouldEnable);
        } catch (Throwable throwable) {
            Minedevice.LOGGER.debug("Unable to tick megaphone voice hook", throwable);
        }
    }

    private static void shutdownMegaphoneVoice() {
        if (!megaphoneVoiceAvailable || megaphoneVoiceShutdownMethod == null) {
            return;
        }

        try {
            megaphoneVoiceShutdownMethod.invoke(null);
        } catch (Throwable throwable) {
            Minedevice.LOGGER.debug("Unable to shutdown megaphone voice hook", throwable);
        }
    }

    private static void tickPhoneCallVoice(boolean shouldEnable) {
        if (!phoneCallVoiceAvailable || phoneCallVoiceTickMethod == null) {
            return;
        }

        try {
            phoneCallVoiceTickMethod.invoke(null, shouldEnable);
        } catch (Throwable throwable) {
            Minedevice.LOGGER.debug("Unable to tick phone call voice hook", throwable);
        }
    }

    private static void shutdownPhoneCallVoice() {
        if (!phoneCallVoiceAvailable || phoneCallVoiceShutdownMethod == null) {
            return;
        }

        try {
            phoneCallVoiceShutdownMethod.invoke(null);
        } catch (Throwable throwable) {
            Minedevice.LOGGER.debug("Unable to shutdown phone call voice hook", throwable);
        }
    }

    private static void registerModelProperties() {
        if (propertyRegistered) {
            return;
        }

        propertyRegistered = true;
        ResourceLocation phoneCallPoseProperty = new ResourceLocation(Minedevice.MOD_ID, "call_pose");
        ResourceLocation phoneOnProperty = new ResourceLocation(Minedevice.MOD_ID, "screen_on");
        ResourceLocation megaphoneTootingProperty = new ResourceLocation(Minedevice.MOD_ID, "tooting");
//        ResourceLocation stackedProperty = new ResourceLocation(Minedevice.MOD_ID, "stacked");
        ItemPropertiesRegistry.register(ModItems.PHONE.get(), phoneCallPoseProperty,
                (stack, level, entity, seed) -> entity != null && PhoneClientHooks.shouldUsePhoneCallPose(entity)
                        ? 1.0F
                        : 0.0F);
        ItemPropertiesRegistry.register(ModItems.PHONE.get(), phoneOnProperty,
                (stack, level, entity, seed) -> entity != null && PhoneClientHooks.shouldUsePhoneOnModel(entity)
                        ? 1.0F
                        : 0.0F);
//        ItemPropertiesRegistry.register(ModItems.PHONE.get(), phoneQrProperty,
//                (stack, level, entity, seed) -> entity != null && PhoneClientHooks.shouldUsePhoneQrModel(entity)
//                        ? 1.0F
//                        : 0.0F);
//        ItemPropertiesRegistry.register(ModItems.PHONE.get(), phoneChatQrProperty,
//                (stack, level, entity, seed) -> entity != null && PhoneClientHooks.shouldUseChatQrModel(entity)
//                        ? 1.0F
//                        : 0.0F);
        ItemPropertiesRegistry.register(ModItems.MEGAPHONE.get(), megaphoneTootingProperty,
                (stack, level, entity, seed) -> entity != null
                        && entity.isUsingItem()
                        && entity.getUseItem() == stack
                        ? 1.0F
                        : 0.0F);
    }
}

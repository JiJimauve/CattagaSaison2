package fr.cattaga.corruption;

import fr.cattaga.corruption.block.corruption.spreader.CorruptionSpreader;
import fr.cattaga.corruption.command.CorruptionCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Corruption implements ModInitializer {
    public static final String MOD_ID = "corruption";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in  mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Cattaga : C IS HERE, maybe life is about helping other escaping their fear and sadness ?");
        LOGGER.debug("Cattaga : C Debugging Introduction Msg");
        LOGGER.trace("Cattaga : C Trace Introduction Msg");

        // Create Blocks
        CorruptionBlock.initialize(); // Force registering of Block

        // Create Cmd
        CorruptionCommand.initialize();

        // Register Central Tick of CorruptionSpreader
        CorruptionSpreader.onInitialize();
        ServerTickEvents.END_SERVER_TICK.register(CorruptionSpreader::onServerTick);
    }


    public static ResourceLocation generateResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

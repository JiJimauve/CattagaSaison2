package fr.cattaga.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class CorruptionClient implements ClientModInitializer {

	public static final String MOD_ID = "Corruption-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitializeClient() {

        LOGGER.info(MOD_ID + " is Initialized");
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}
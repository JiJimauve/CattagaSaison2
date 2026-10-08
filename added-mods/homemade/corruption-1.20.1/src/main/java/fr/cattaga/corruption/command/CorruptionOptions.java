package fr.cattaga.corruption.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;


// TODO REGISTER DATA IN CORRUPTION_CONFIG
public class CorruptionOptions {


    public static boolean PROPAGATOR_HATCH = false;

    // Amount of Corruption Propagator calculate
    public static int PROPAGATOR_MAX_TURN = 512;

    public static int PROPAGATOR_TICK_RATE = 0;
    public static boolean canTick(MinecraftServer server) {
        // ALL TICK
        if (CorruptionOptions.PROPAGATOR_TICK_RATE == 1) {
            return true;
        }
        int currentTick = -1;
        try {
            currentTick = server.getTickCount();
        }
        catch (NullPointerException e) {
            return false;
        }

        return
                // If there is a tick Rate ( != 0 ) and the Tick rate is now
                (CorruptionOptions.PROPAGATOR_TICK_RATE != 0 && currentTick % CorruptionOptions.PROPAGATOR_TICK_RATE == 0);
    }


    public static int CORRUPTION_SPREAD_PERCENT = 100;

    public static boolean canCorrupt() {
        if (CorruptionOptions.CORRUPTION_SPREAD_PERCENT < 1 ){
            return false;
        }

        if (CorruptionOptions.CORRUPTION_SPREAD_PERCENT > 99 ){
            return true;
        }

        int chance = RandomSource.create().nextIntBetweenInclusive(0,100);

        return chance < CorruptionOptions.CORRUPTION_SPREAD_PERCENT;
    }

}

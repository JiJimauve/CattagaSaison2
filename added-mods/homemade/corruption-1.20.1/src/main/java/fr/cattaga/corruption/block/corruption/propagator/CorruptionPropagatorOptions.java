package fr.cattaga.corruption.block.corruption.propagator;

import net.minecraft.util.RandomSource;


// TODO REGISTER DATA IN CORRUPTION_CONFIG
public class CorruptionPropagatorOptions {

    public static int CORRUPTION_SPREAD_PERCENT = 100;

    public static int PROPAGATOR_TICK_RATE = 10;

    public static boolean canCorrupt() {
        if (CorruptionPropagatorOptions.CORRUPTION_SPREAD_PERCENT < 1 ){
            return false;
        }

        if (CorruptionPropagatorOptions.CORRUPTION_SPREAD_PERCENT > 99 ){
            return true;
        }

        int chance = RandomSource.create().nextIntBetweenInclusive(0,100);

        return chance < CorruptionPropagatorOptions.CORRUPTION_SPREAD_PERCENT;
    }

}

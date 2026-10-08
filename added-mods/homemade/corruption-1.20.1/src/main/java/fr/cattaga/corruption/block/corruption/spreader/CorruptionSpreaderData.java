package fr.cattaga.corruption.block.corruption.spreader;

import fr.cattaga.corruption.Corruption;
import fr.cattaga.corruption.block.corruption.propagator.CorruptionPropagatorBlockEntity;
import fr.cattaga.corruption.utils.Utils;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorruptionSpreaderData {

    private static final Map<Level, List<Utils.BlockPosState>> mapLevelEggBlock = new HashMap<>();

    protected static Map<Level, List<Utils.BlockPosState>> getCopyMapLevelEggBlock() {
        Map<Level, List<Utils.BlockPosState>> copy = new HashMap<>();

        for (Level level : mapLevelEggBlock.keySet()) {
            copy.put(level, List.copyOf(mapLevelEggBlock.get(level)));

        }
        return copy;
    }

    public static void register(Utils.BlockPosState eggBlock, Level level) {
        if (eggBlock == null) {
            Corruption.LOGGER.warn("Got a null Register ! Should not happen");
            return;
        }

        if (!CorruptionSpreaderData.mapLevelEggBlock.containsKey(level)) {
            List <Utils.BlockPosState> list = new ArrayList<>();
            list.add(eggBlock);
            CorruptionSpreaderData.mapLevelEggBlock.put(level, list);
            return;
        }

        if (!CorruptionSpreaderData.mapLevelEggBlock.get(level).contains(eggBlock)) {
            CorruptionSpreaderData.mapLevelEggBlock.get(level).add(eggBlock);
        }

    }

    public static void unregister(Utils.BlockPosState eggBlock, Level level) {

        if (!CorruptionSpreaderData.mapLevelEggBlock.containsKey(level)) {
            return;
        }
        CorruptionSpreaderData.mapLevelEggBlock.get(level).remove(eggBlock);
    }


    // --------------------------------------------------------------------------------------------------------- //

    private static final Map<Level, List<CorruptionPropagatorBlockEntity>> mapLevelPropagatorBlockEntity = new HashMap<>();

    protected static Map<Level, List<CorruptionPropagatorBlockEntity>> getCopyMapLevelPropagatorBlockEntity() {
        Map<Level, List<CorruptionPropagatorBlockEntity>> copy = new HashMap<>();

        for (Level level : mapLevelPropagatorBlockEntity.keySet()) {
            copy.put(level, List.copyOf(mapLevelPropagatorBlockEntity.get(level)));

        }
        return copy;
    }

    public static void register(CorruptionPropagatorBlockEntity propagatorBlockEntity, Level level) {
        if (propagatorBlockEntity == null) {
            Corruption.LOGGER.warn("Got a null Register ! Should not happen");
            return;
        }

        if (!CorruptionSpreaderData.mapLevelPropagatorBlockEntity.containsKey(level)) {
            List <CorruptionPropagatorBlockEntity> list = new ArrayList<>();
            list.add(propagatorBlockEntity);
            CorruptionSpreaderData.mapLevelPropagatorBlockEntity.put(level, list);
            return;
        }

        if (!CorruptionSpreaderData.mapLevelPropagatorBlockEntity.get(level).contains(propagatorBlockEntity)) {
            CorruptionSpreaderData.mapLevelPropagatorBlockEntity.get(level).add(propagatorBlockEntity);
        }

    }

    public static void unregister(CorruptionPropagatorBlockEntity propagatorBlockEntity, Level level) {

        if (!CorruptionSpreaderData.mapLevelPropagatorBlockEntity.containsKey(level)) {
            return;
        }
        CorruptionSpreaderData.mapLevelPropagatorBlockEntity.get(level).remove(propagatorBlockEntity);
    }

    // --------------------------------------------------------------------------------------------------------- //

    public static void onInitialize() {
         CorruptionSpreaderData.mapLevelPropagatorBlockEntity.clear();
        // CorruptionSpreaderData.mapLevelEggBlock.clear(); // The Egg register on place & unregister on remove
    }

}

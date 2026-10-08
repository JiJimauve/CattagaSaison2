package fr.cattaga.corruption.block.corruption.spreader;


import fr.cattaga.corruption.Corruption;
import fr.cattaga.corruption.CorruptionBlock;
import fr.cattaga.corruption.block.corruption.propagator.CorruptionPropagatorBlock;
import fr.cattaga.corruption.block.corruption.propagator.CorruptionPropagatorBlockEntity;
import fr.cattaga.corruption.command.CorruptionOptions;
import fr.cattaga.corruption.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgumentIndexOutOfBoundsException;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Class used to spread Corruption accross the level
 *
 */
public class CorruptionSpreader {


    /**
     * Un/Register data
     */

    public static void register(CorruptionPropagatorBlockEntity propagatorBlockEntity, Level level) {
        CorruptionSpreaderData.register(propagatorBlockEntity,level);
    }
    public static void unregister(CorruptionPropagatorBlockEntity propagatorBlockEntity, Level level) {
        CorruptionSpreaderData.unregister(propagatorBlockEntity,level);
    }

    public static void registerEgg(Utils.BlockPosState eggBlock, Level level) {
        CorruptionSpreaderData.register(eggBlock,level);
    }
    public static void unregisterEgg(Utils.BlockPosState eggBlock, Level level) {
        CorruptionSpreaderData.unregister(eggBlock,level);
    }

    public static void onInitialize() {
        CorruptionSpreaderData.onInitialize();
    }

    public static void onServerTick(MinecraftServer server){

        if (!CorruptionOptions.canTick(server)) {
            return;
        }

        // We Copy the current state of Spread Data so that if it move it won't make a null pointer
        Map<Level, List<CorruptionPropagatorBlockEntity>>  mapLevelPropagatorBlockEntity = CorruptionSpreaderData.getCopyMapLevelPropagatorBlockEntity();
        Map<Level, List<Utils.BlockPosState>> mapLevelEggBlock = CorruptionSpreaderData.getCopyMapLevelEggBlock();


        int totalPlayer = server.getPlayerList().getPlayers().size();

        // No player = No Corruption
        if (totalPlayer == 0) {
            return;
        }

        Map<Level, Integer> levelMaxProcess = new HashMap<>();

        // We stock the amount of player per Level
        for (ServerPlayer p : server.getPlayerList().getPlayers()) {

            Level playerLevel = p.level();
            if (levelMaxProcess.containsKey(playerLevel)) {
                levelMaxProcess.put(playerLevel, levelMaxProcess.get(playerLevel) + 1);
            } else {
                levelMaxProcess.put(playerLevel, 1);
            }

        }

        // Turn Number of player into % of all player per level into Max BlockEntity to process in each level
        for (Level level : levelMaxProcess.keySet()) {
            int nbPlayerOnThisLevel = levelMaxProcess.get(level);
            int maxEntityPerLevel = nbPlayerOnThisLevel / totalPlayer * CorruptionOptions.PROPAGATOR_MAX_TURN;
            levelMaxProcess.put(level,maxEntityPerLevel);
        }

        // For Each Level with Player in it
        for (Level level : levelMaxProcess.keySet()) {

            StringBuilder sb = new StringBuilder(CorruptionOptions.PROPAGATOR_TICK_RATE).append(" TICKRATE >  SPREADER LEVEL  > ");
            sb.append(level).append(" > Propagator ");


            if (mapLevelPropagatorBlockEntity.containsKey(level)) {
                sb.append(mapLevelPropagatorBlockEntity.get(level).size());
            } else {
                sb.append("0");
            }

            sb.append(" > Egg ");
            if (mapLevelEggBlock.containsKey(level)) {
                sb.append(mapLevelEggBlock.get(level).size());
            } else {
                sb.append("0");
            }

            Corruption.LOGGER.debug(sb.toString());

            int processed = 0;
            int maxToProceed = levelMaxProcess.get(level);

            // Process the CorruptionPropagatorBlockEntity
            // If we don't have corruption in the level where player are we skip the level
            if (mapLevelPropagatorBlockEntity.containsKey(level)) {
                List<CorruptionPropagatorBlockEntity> listEntity = mapLevelPropagatorBlockEntity.get(level);
                Corruption.LOGGER.debug(" SPREADER PROPAGATOR > "+ listEntity.size() + " PROPAGATOR(S) " );

                for (CorruptionPropagatorBlockEntity propagatorBlockEntity : listEntity) {

                    //Corruption.LOGGER.debug(" SPREADER PROCESS PROPAGATOR > " + propagatorBlockEntity.getBlockPos());

                    // TODO Check if chunk loaded
                    // Todo Ordonate to closer players
                    if ( processCorruptionPropagatorBlockEntity(propagatorBlockEntity,level) ) {
                        processed++;
                    }

                    // Mesuring amount of processed
                    if (processed >= maxToProceed) {
                        break;
                    }
                }

            }

            // Process the CorruptionEggBlock (turn into Propagator)
            if (CorruptionOptions.PROPAGATOR_HATCH && processed < maxToProceed && mapLevelEggBlock.containsKey(level)) {
                List<Utils.BlockPosState> listEgg = mapLevelEggBlock.get(level);
                Corruption.LOGGER.debug(" SPREADER EGG > "+ listEgg.size() + " EGGS(S) // PROCESSED " + processed + " / " + maxToProceed );

                if (listEgg.isEmpty()) {
                    continue;
                }

                int index = 0;
                while (processed < maxToProceed && index < listEgg.size()) {
                    Utils.BlockPosState egg = listEgg.get(index);
                    if (level.setBlock(egg.pos, CorruptionBlock.CORRUPTION_PROPAGATOR_BLOCK.defaultBlockState(),512)) {
                        level.playSound(null, egg.pos, SoundEvents.SCULK_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        CorruptionSpreader.unregisterEgg(egg, level);
                    }
                    index++;
                    processed++;
                }

            }

            Corruption.LOGGER.debug(" SPREADER LEVEL > "+ level + " DONE : PROCESSED " + processed + " / " + maxToProceed +"\n-------------------------------------------------------------------");

        }
    }

    /**
     * Process the Spread of Corruption from CorruptionPropagatorBlock entity
     * @param corruptionPropagatorBlockEntity
     * @param level
     * @return True => Corruption Worked, False => No more Corruption
     */
    private static boolean processCorruptionPropagatorBlockEntity(CorruptionPropagatorBlockEntity corruptionPropagatorBlockEntity, Level level) {

        BlockPos blockPos = corruptionPropagatorBlockEntity.getBlockPos();
        LevelChunk chunk = level.getChunkAt(blockPos);
        BlockState blockState = chunk.getBlockState(blockPos);


        if (!blockState.hasProperty(CorruptionPropagatorBlock.ENERGY)) {
            Corruption.LOGGER.warn("CorruptionSpreader received an Blockstate ( at " + blockPos + " ) that has no Property " + CorruptionPropagatorBlock.ENERGY + " in it, Cannot PROCESS !");

            CorruptionSpreader.unregister(corruptionPropagatorBlockEntity,level);
            return false;
        }

        if (!blockState.hasProperty(CorruptionPropagatorBlock.CHARGE)) {
            Corruption.LOGGER.warn("CorruptionSpreader received an Blockstate  ( at " + blockPos + " ) t that has no Property " + CorruptionPropagatorBlock.CHARGE + " in it, Cannot PROCESS !");
            CorruptionSpreader.unregister(corruptionPropagatorBlockEntity,level);
            return false;
        }

        int energy = blockState.getValue(CorruptionPropagatorBlock.ENERGY);
        int charge = blockState.getValue(CorruptionPropagatorBlock.CHARGE);

        // We had a lot of try already, when charge go to zero it turn the propagator into an egg (entityless block)
        if (energy == CorruptionPropagatorBlock.MAX_ENERGY && charge > 0) {

            // Could Not Create more propagator
            // We go random Dir and give the rest of charge
            List<Integer> randomDirection = getRandomDirection();

                while (!randomDirection.isEmpty()) {
                    int randomDir = randomDirection.remove(0); // Random Direction
                    BlockPos randomFinalPos = getNearPos(blockPos, randomDir, CorruptionPropagatorBlockEntity.ENTITY_RANGE * 2);
                    BlockState randomFinalBs = level.getBlockState(randomFinalPos);

                    if (randomFinalBs.is(CorruptionBlock.CORRUPTED)) {
                        continue;
                    }

                    for (int i = 1; i < CorruptionPropagatorBlockEntity.ENTITY_RANGE * 2; i++) {
                        BlockPos nextPos = getNearPos(blockPos, randomDir, i);
                        BlockState nextBs = level.getBlockState(nextPos);
                        if(!nextBs.is(CorruptionBlock.CORRUPTED)) {
                            if (level.setBlock(nextPos, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState(),3)) {
                                level.playSound(null, nextPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                            }
                        }
                    }

                    BlockState newPropagator = CorruptionBlock.CORRUPTION_PROPAGATOR_BLOCK.defaultBlockState();
                    newPropagator = newPropagator.setValue(CorruptionPropagatorBlock.CHARGE, charge - 1);

                    if (level.setBlock(randomFinalPos, newPropagator,3)) {
                        level.playSound(null, randomFinalPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);

                        blockState = blockState.setValue(CorruptionPropagatorBlock.CHARGE, 0);// Kill all charge here
                        chunk.setBlockState(blockPos,blockState, true);

                        break;
                    }

            }

            if (randomDirection.isEmpty()) {
                blockState = blockState.setValue(CorruptionPropagatorBlock.CHARGE, 0);
                blockState = blockState.setValue(CorruptionPropagatorBlock.ENERGY, 0);
            }

            chunk.setBlockState(blockPos,blockState, true);
            return true;





        }
        // Turn AnyBlock in listening area into an Corrupted Block
        else if (energy > CorruptionPropagatorBlock.MAX_ENERGY/2) {

            if (!CorruptionOptions.canCorrupt()) {
                // Did not pass the test
                return false;
            }

            // check all coordonate from the cube to turn something in a corrupted
            // Do a 3 for ( X Y Z ) starting from Origin Pos(X,Y,Z) - ( ENTITY_RANGE, ENTITY_RANGE , ENTITY_RANGE ) looking for something to currupt

            BlockPos posToTurn = corruptionPropagatorBlockEntity.getRandomPosToConvertInListeningArea(level);

            if (posToTurn != null) {
                if (level.setBlockAndUpdate(posToTurn, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState())) {
                    blockState = blockState.setValue(CorruptionPropagatorBlock.ENERGY, energy - 16);

                    level.playSound(null, posToTurn, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

            }

        }
        // Turn Next Corruptible block into Corrupted
        else if (energy > 1 ) {

            if (!CorruptionOptions.canCorrupt()) {
                // Did not pass the test
                return false;
            }


            int forbittenDirection = level.getRandom().nextInt(5);

            // Seek an block Candidate to Corrupt
            BlockPos originPos = corruptionPropagatorBlockEntity.getBlockPos();

            Utils.BlockPosStateDir corruptibleBPS = foundCandidateToCorrupt(originPos,forbittenDirection, level, corruptionPropagatorBlockEntity,
                    corruptionPropagatorBlockEntity::isBlockValidForCorruption
            );

            if (corruptibleBPS != null ) {

                BlockPos corruptibleBlockPos = corruptibleBPS.pos;

                if (level.setBlockAndUpdate(corruptibleBlockPos, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState())) {
                    blockState = blockState.setValue(CorruptionPropagatorBlock.ENERGY, energy - 1);
                    level.playSound(null, corruptibleBlockPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);

                    // If we got charge and we corrupt at limit block
                    if (charge > 0 && ( isOnRangeLimit(originPos,corruptibleBlockPos, CorruptionPropagatorBlockEntity.ENTITY_RANGE) || isOnRangeLimit(originPos,corruptibleBlockPos, CorruptionPropagatorBlockEntity.ENTITY_RANGE-1)) ) {


                        // Confirm that we can go forward
                        int airBlock = 0;
                        boolean canSpread = true;
                        List<Utils.BlockPosState> allBlockState = new ArrayList<>(CorruptionPropagatorBlockEntity.ENTITY_RANGE);
                        // We want to corrupt for Entity_range distance toward corruptibleBPS.direction and then create an new Propagator
                        for(int i = 0; i <= CorruptionPropagatorBlockEntity.ENTITY_RANGE-1; i++) {
                            BlockPos blockCandidatePos = getNearPos(corruptibleBlockPos, corruptibleBPS.direction, i);
                            BlockState blockCandidateState = level.getBlockState(blockCandidatePos);



                            if (blockCandidateState.isAir()) {
                                airBlock ++;
                                if (airBlock > CorruptionPropagatorBlockEntity.ENTITY_RANGE / 4) { // Too much Air
                                    canSpread = false;
                                    break;
                                }
                            } else if (blockCandidateState.getBlock().defaultDestroyTime() <= 0) {// If it cannot be destroy (not  Bedrock)
                                canSpread = false;
                                break;
                            }

                            allBlockState.add(new Utils.BlockPosState(blockCandidatePos,blockCandidateState));
                        }

                        if (canSpread) {
                            for (Utils.BlockPosState bsp : allBlockState) {
                                if(!bsp.state.is(CorruptionBlock.CORRUPTED)) {
                                    if (level.setBlock(bsp.pos, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState(),3)) {
                                        level.playSound(null, bsp.pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                                    }
                                }
                            }

                            BlockPos newPropagatorPos = getNearPos(corruptibleBlockPos, corruptibleBPS.direction, CorruptionPropagatorBlockEntity.ENTITY_RANGE);

                            if (level.setBlock(newPropagatorPos, CorruptionBlock.CORRUPTION_EGG_BLOCK.defaultBlockState(),3)) {
                                level.playSound(null, newPropagatorPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                                blockState = blockState.setValue(CorruptionPropagatorBlock.CHARGE, charge - 1);
                                chunk.setBlockState(blockPos,blockState, true);

                            }
                        }



                    }



                }


                return true;
            }

        }


        return false;
    }
    /**
     * Look for an candidate link via Corrupted Block to OriginPos
     * @param originPos Original Block Position that seek to Corrupt
     * @param forbittenDirection An Forbitten direction (will not come back thoward this direction)
     * @param level Utils
     * @param corruptionPropagatorBlockEntity Origin Block that seek to Corrupt
     * @param blockCanBeCorrupted Function to tell if candidate is valable
     * @return
     */
    public static Utils.BlockPosStateDir foundCandidateToCorrupt(BlockPos originPos, int forbittenDirection, Level level , CorruptionPropagatorBlockEntity corruptionPropagatorBlockEntity, BiFunction<BlockState, BlockPos, Boolean> blockCanBeCorrupted) {

        if (originPos == null) {
            Corruption.LOGGER.warn("Got a null Block originPos for a foundCandidateToCorrupt");

            return null;
        }

        BlockPos nearPos = null;
        BlockState nearState = null;
        List<BlockPos> alreadyTestedPos = new ArrayList<>();
        int currentDirection = -1;
        // Looking for near block from the Corrupted source
        do {

            // We look for any direction except one (the "back" direction)
            List<Integer> randomDirections = getRandomDirectionExcept(forbittenDirection);

            currentDirection = randomDirections.remove(0);
            // Turn Number ( 0 - 5 ) to Near Pos ( originpos.north() / south() [...])
            nearPos = getNearPos(originPos , currentDirection); // Turn Directionnal Int into a next Pos

            if (nearPos == null) {
                Corruption.LOGGER.warn("Got a null nearPos pos for a foundCandidateToCorrupt ( originPos " + originPos + " forbittenDirection  " + forbittenDirection + " ) ");

                return null;
            }


            // Get the block
            nearState = level.getBlockState(nearPos);

            // Found Corrupted, now seek from Corrupted Bloc
            if (nearState.is(CorruptionBlock.CORRUPTED)) {
                originPos = nearPos;

                alreadyTestedPos.add(nearPos); // Keep in mind where the path we came from
            } else {
                // If Block is not corruptible, we switch direction
                while (alreadyTestedPos.contains(nearPos) || !blockCanBeCorrupted.apply(nearState,nearPos) ) { // alreadyTestedPos.contains(nearPos) || (!corruptionPropagatorBlockEntity.isBlockValidForCorruption(nearState,nearPos) && !nearState.is(CorruptionBlock.CORRUPTED)) ) { // While we cannot Corrupt Or We didn't found Corrupted one we keep seeking

                    // All direction gave a non corruptible Block
                    if (randomDirections.isEmpty()) {
                        nearPos = null;
                        break;
                    }

                    // Next Direction might give an corruptible block
                    currentDirection = randomDirections.remove(0);
                    nearPos = getNearPos(nearPos , currentDirection);
                    nearState = level.getBlockState(nearPos);
                }
            }



            if (nearPos == null) {
                break;
            }

        } while (nearState.is(CorruptionBlock.CORRUPTED));


        if (nearPos != null ) {
            // Found a block
            return new Utils.BlockPosStateDir(nearPos, nearState,currentDirection);
        } else {
            // Didn't Found a block
            return null;
        }

    }


    public static boolean isPosInListeningRange(BlockPos pos, BlockPos originPos, int range) {
        boolean inRange = true;
        inRange = pos.getX() >= originPos.getX() - range && pos.getX() <= originPos.getX() + range;
        if (inRange) {
            inRange = pos.getY() >= originPos.getY() - range && pos.getY() <= originPos.getY() + range;
        }

        if (inRange) {
            inRange = pos.getZ() >= originPos.getZ() - range && pos.getZ() <= originPos.getZ() + range;
        }

        return inRange;
    }

    // Utility function for random In Next block
    public static List<Integer> getRandomDirection() {
        ArrayList<Integer> ar = new ArrayList<Integer>(6);
        ar.addAll(Arrays.asList(0,1,2,3,4,5));

        Collections.shuffle(ar);

        return ar;
    }

    /**
     * Give an Direction Array Random except one value
     * @param exception the value (  0 - 5 )
     * @return
     */
    public static List<Integer> getRandomDirectionExcept(int exception) {
        if (exception > 5 || exception < 0) {
            throw new ArgumentIndexOutOfBoundsException(exception);
        }
        ArrayList<Integer> ar = new ArrayList<Integer>(5);

        for (int i = 0; i <= 5; i++) {
            if (i != exception) {
                ar.add(i);
            }
        }
        Collections.shuffle(ar);

        return ar;
    }



    public static BlockPos getNearPos(BlockPos bp, int direction) {
        return getNearPos(bp, direction , 1);
    }

    public static boolean isOnRangeLimit(BlockPos origin,BlockPos compare, int distance ) {
        return
                origin.getX() + distance == compare.getX() ||
                        origin.getX() - distance == compare.getX() ||
                        origin.getY() + distance == compare.getY() ||
                        origin.getY() - distance == compare.getY() ||
                        origin.getZ() + distance == compare.getZ() ||
                        origin.getZ() - distance == compare.getZ() ;

    }


    public static BlockPos getNearPos(BlockPos bp, int direction, int distance) {

        if (bp == null) {
            Corruption.LOGGER.warn("Got a null Block pos for a getNearPos");
            return null;
        }

        return switch (direction % 6) {
            case 0 -> bp.below(distance);
            case 1 -> bp.above(distance);
            case 2 -> bp.west(distance);
            case 3 -> bp.east(distance);
            case 4 -> bp.north(distance);
            default -> bp.south(distance); // Or 5
        };
    }

    public static BlockPos getRandomNearPos(BlockPos bp) {

        if (bp == null) {
            return null;
        }

        return getNearPos(bp, RandomSource.create().nextInt(8));

    }
}

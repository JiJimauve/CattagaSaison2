package fr.cattaga.corruption.block.corruption.propagator;

import fr.cattaga.corruption.Corruption;
import fr.cattaga.corruption.CorruptionBlock;
import fr.cattaga.corruption.block.corruption.vein.CorruptionVeinBlock;
import fr.cattaga.corruption.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

import java.security.InvalidParameterException;
import java.util.*;

public class CorruptionPropagatorBlockEntity extends BlockEntity implements GameEventListener.Holder<CorruptionPropagatorBlockEntity.CorruptionPropagator> {
    private final CorruptionPropagator corruptionPropagator; // This class is defined in this file bellow

    CorruptionVeinBlock corruptionVeinBlock = null;

    private List<BlockPos> spreadAims = new ArrayList<>();

    public CorruptionPropagatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CorruptionBlock.CORRUPTION_PROPAGATOR_BLOCK_ENTITY, blockPos, blockState);

        this.corruptionPropagator = new CorruptionPropagator(blockState, new BlockPositionSource(blockPos), this);
    }

    // Called Every Tick
    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, CorruptionPropagatorBlockEntity corruptionPropagatorBlockEntity) {

        if (!blockState.hasProperty(CorruptionPropagatorBlock.ENERGY)) {
            Corruption.LOGGER.error("CorruptionPropagatorBlockEntity received an Blockstate that has no Property "+CorruptionPropagatorBlock.ENERGY+" in it, Cannot PROCESS !");
            throw new InvalidParameterException("CorruptionPropagatorBlockEntity received an Blockstate that has no ENERGY in it, Cannot PROCESS !");
        }

        int energy = blockState.getValue(CorruptionPropagatorBlock.ENERGY);

        // Turn AnyBlock in listening area into an Corrupted Block
        if (energy > 100) {
            BlockPos posToTurn = corruptionPropagatorBlockEntity.getRandomPosToConvertInListeningArea(level);

            if (posToTurn != null) {
                if ( level.destroyBlock(posToTurn, true)) {
                    if (level.setBlockAndUpdate(posToTurn, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState())) {
                        energy -= 75;
                        level.playSound(null, posToTurn, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }

        }
        // Turn Next Corruptible block into Corrupted
        else if (energy > 50) {

            // Do an chain where we look random near origin,
            // => If corrupt look next random
            // => If air / BedRock : look next location
            //      => if no location cancel
            // => If Block corruptible => corrupt it

            BlockPos originPos = blockPos;
            BlockPos nearPos = null;
            BlockState nearBlock = null;
            do {

                List<Integer> firstDirection = getRandomDirection(); // Give Array [ 1 2 3 4 5 6 ]----> but shuffled

                // Turn Number ( 1 - 6 ) to Near Pos
                nearPos = corruptionPropagatorBlockEntity.getRandomNearPos(originPos , firstDirection.remove(0)); // Turn Directionnal Int into a next Pos

                //
                nearBlock = level.getBlockState(nearPos);

                while (!corruptionPropagatorBlockEntity.isBlockValidForCorruption(nearBlock) && nearBlock.is(CorruptionBlock.CORRUPTED)) {
                    if (firstDirection.isEmpty()) {
                        nearPos = null;
                        break;
                    }


                    nearPos = corruptionPropagatorBlockEntity.getRandomNearPos(blockPos , firstDirection.remove(0));
                    nearBlock = level.getBlockState(nearPos);
                }

                // Found Corrupted, now seak from Corrupted Bloc
                if (nearBlock.is(CorruptionBlock.CORRUPTED)) {
                    originPos = nearPos;
                }
            } while (nearBlock.is(CorruptionBlock.CORRUPTED));


            // Found a block
            if (level.destroyBlock(nearPos, true)) {
                if (level.setBlockAndUpdate(nearPos, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState())) {
                    energy -= 25;
                    level.playSound(null, nearPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }

        }

        if (energy < CorruptionPropagatorBlock.MAX_ENERGY) {

            blockState = blockState.setValue(CorruptionPropagatorBlock.ENERGY, energy  +1);
            level.setBlock(blockPos, blockState, 3); // Save New Energy Level

        }

        // Begin Work Of Vein
        /*
        BlockPos upperOriginPos = blockPos.atY(blockPos.getY() + 1);

        BlockState upperBlockState = level.getBlockState(upperOriginPos);
        //Corruption.LOGGER.info("corruptionPropagatorBlockEntity.energy : " + corruptionPropagatorBlockEntity.energy);
        if (corruptionPropagatorBlockEntity.energy >= 60) {
            if (upperBlockState.isAir()) { // If block is air
                if (level.setBlockAndUpdate(upperOriginPos, CorruptionBlock.CORRUPTION_VEIN_BLOCK.defaultBlockState())) {

                    BlockState veinBlockState = level.getBlockState(upperOriginPos);
                    CorruptionVeinBlock.setUpFace(veinBlockState);


                    corruptionPropagatorBlockEntity.corruptionVeinBlock = (CorruptionVeinBlock) veinBlockState.getBlock();

                    corruptionPropagatorBlockEntity.energy = 0;
                }
            }
        }

        corruptionPropagatorBlockEntity.energy++;


        if (corruptionPropagatorBlockEntity.corruptionVeinBlock != null) {
            while(corruptionPropagatorBlockEntity.energy > 10) {
                corruptionPropagatorBlockEntity.energy -= 10;

                // spreadFromFaceTowardRandomDirection(
                //		BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, RandomSource randomSource, boolean bl
                //	) {
                Corruption.LOGGER.info("SPREAD : " + corruptionPropagatorBlockEntity.energy);

                corruptionPropagatorBlockEntity.corruptionVeinBlock.getSpreader().spreadFromFaceTowardRandomDirection(blockState,level,blockPos,Direction.getRandom(level.getRandom()),level.getRandom(),false);
            }

        }

            /*
            if ( )) {
                BlockState newBlockState = serverLevel.getBlockState(upperPos);
                CorruptionVeinBlock.setUpFace(newBlockState);

                if (!(newBlockState.getBlock() instanceof CorruptionVeinBlock)) {
                    Corruption.LOGGER.warn("Generated a new CORRUPTION_VEIN_BLOCK at " + upperPos + " But didn't get an CorruptionVeinBlock.class");
                } else {
                    CorruptionVeinBlock newVeinBlock = (CorruptionVeinBlock) newBlockState.getBlock();


                }

            }


            BlockState blockState = level.getBlockState(this.pos);
            SculkBehaviour sculkBehaviour = getBlockBehaviour(blockState);
            if (sculkBehaviour.attemptSpreadVein(level, this.pos, blockState, this.facings, sculkSpreader.isWorldGeneration())) {
                if (sculkBehaviour.canChangeBlockStateOnSpread()) {
                    blockState = level.getBlockState(this.pos);
                    sculkBehaviour = getBlockBehaviour(blockState);
                }

                level.playSound(null, blockPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            */

    }



    public boolean isPosInListeningRange(BlockPos pos) {
        boolean inRange = true;
        BlockPos originPos = this.getBlockPos();
        int radius = this.corruptionPropagator.getListenerRadius() / 2;


        inRange = pos.getX() > originPos.getX() - radius && pos.getX() < originPos.getX() + radius;
        if (inRange) {
            inRange = pos.getY() > originPos.getY() - radius && pos.getY() < originPos.getY() + radius;
        }

        if (inRange) {
            inRange = pos.getZ() > originPos.getZ() - radius && pos.getZ() < originPos.getZ() + radius;
        }


        return inRange;

    }

    // Utility function for random In Next block
    public static List<Integer> getRandomDirection() {
        ArrayList<Integer> ar = new ArrayList<Integer>(6);
        ar.addAll(Arrays.asList(1,2,3,4,5,6));

        Collections.shuffle(ar);

        return ar;
    }


        public BlockPos getRandomNearPos(BlockPos bp, int i) {
        switch (i % 6) {
            case 1 :
                return bp.below();
            case 2:
                return bp.above();
            case 3 :
                return bp.west();
            case 4 :
                return bp.east();
            case 5 :
                return bp.north();
            case 6 :
                return bp.south();
        }

        return bp.north(); // Should not happen
    }

    public BlockPos getRandomNearPos(BlockPos bp) {

        if (bp == null) {
            return null;
        }

        return getRandomNearPos(bp, RandomSource.create().nextInt(8));

    }

    // Utility function for random In Listening Sphere

    public boolean isBlockValidForCorruption(BlockState tryBlockState) {
        // tryBlockState.getBlock().properties.destroyTime == -1 <= means not destruct
        return tryBlockState != null && !tryBlockState.isAir() & !tryBlockState.is(CorruptionBlock.CORRUPTED) && tryBlockState.getBlock().defaultDestroyTime() > 0;
    }

    public BlockPos getRandomPosInListeningSphere(RandomSource r) {

        BlockPos originPos = this.getBlockPos();
        BlockPos newPos = null;

        int radius = this.corruptionPropagator.getListenerRadius() / 2;

        int randomX = originPos.getX() + r.nextIntBetweenInclusive(-radius, radius);
        int randomY = originPos.getY() + r.nextIntBetweenInclusive(-radius, radius);
        int randomZ = originPos.getZ() + r.nextIntBetweenInclusive(-radius, radius);

        newPos = new BlockPos(randomX, randomY, randomZ);
        int nbTry = 0;
        while (newPos.equals(originPos)) {
            randomX = originPos.getX() + r.nextIntBetweenInclusive(-radius, radius);
            randomY = originPos.getY() + r.nextIntBetweenInclusive(-radius, radius);
            randomZ = originPos.getZ() + r.nextIntBetweenInclusive(-radius, radius);

            newPos = new BlockPos(randomX, randomY, randomZ);


            nbTry++;
            if (nbTry > 16) {
                return null;
            }
        }
        return newPos;
    }

    public BlockPos getRandomPosToConvertInListeningArea(Level l) {

        BlockPos tryBlockPos = null;
        BlockState tryBlockState = null;
        int nbTry = 0;
        do {
            tryBlockPos = this.getRandomPosInListeningSphere(l.getRandom());
            if (tryBlockPos == null) {
                Corruption.LOGGER.info("Could Not Found any Pos to Corrupt");
                return null;
            }

            tryBlockState = l.getBlockState(tryBlockPos);

            nbTry++;
            if (nbTry > 16) {
                Corruption.LOGGER.info("Could Not Found any block to Corrupt");
                return null;
            }
        } while( !this.isBlockValidForCorruption(tryBlockState) );
        Utils.DisplayBlocStateProperties("Block to Corrupt will be  " + tryBlockState.getBlock().getName() + " " + " PROPS : ", tryBlockState);

        return tryBlockPos;
    }

    public void addAim(BlockPos blockSpreadTo) {
        if (!this.spreadAims.contains(blockSpreadTo))
            this.spreadAims.add(blockSpreadTo);
    }

    public CorruptionPropagator getListener() {
        return this.corruptionPropagator;
    }

    // ----------------------------------------------------------------------------------------------------------------
    public static class CorruptionPropagator implements GameEventListener {
        public static final int PULSE_TICKS = 8;

        private CorruptionPropagatorBlockEntity origin;
        private final BlockState blockState;
        private final PositionSource positionSource;

        // final SculkSpreader sculkSpreader;// Todo Change it to corruption Spreader

        public CorruptionPropagator(BlockState blockState, PositionSource positionSource, CorruptionPropagatorBlockEntity origin) {
            this.blockState = blockState;
            this.positionSource = positionSource;
            this.origin = origin;
        }

        @Override
        public PositionSource getListenerSource() {
            return this.positionSource;
        }

        @Override
        public int getListenerRadius() {
            return 16;
        } // Where event are listened like death of PNG

        @Override
        public DeliveryMode getDeliveryMode() {
            return DeliveryMode.BY_DISTANCE;
        }

        public boolean generateVein() {
            return true;
        }

        @Override
        public boolean handleGameEvent(ServerLevel serverLevel, GameEvent gameEvent, GameEvent.Context context, Vec3 vec3) {

            if (gameEvent == GameEvent.HIT_GROUND && context.sourceEntity() instanceof LivingEntity livingEntity) {

                Optional<Vec3> maybePos = this.positionSource.getPosition(serverLevel);
                if (this.blockState.hasProperty(CorruptionPropagatorBlock.ENERGY) && maybePos.isPresent() && this.blockState.getValue(CorruptionPropagatorBlock.ENERGY) > 10) {

                    Vec3 vec = maybePos.get();
                    int x = (int) vec.x - 1; // X are miss placed ?
                    int y = (int) vec.y - 1 ;
                    int z = (int) vec.z - 1; // Y are miss placed ?


                    BlockPos pos = Utils.firstAirBlock(serverLevel, new BlockPos(x,y,z));

                    if (pos != null) {
                        serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5 , 5, 0.2, 0.5, 0.2, 0.01);
                        Corruption.LOGGER.info("Block " + pos + " ENERGY : " + this.blockState.getValue(CorruptionPropagatorBlock.ENERGY) );
                    }

                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, vec3.x, vec3.y+0.5, vec3.z ,3, 0.2, 0.2, 0.2, 0.01);



                } else {
                    Corruption.LOGGER.info("Block " + this.positionSource.getPosition(serverLevel)+ " has no ENERGY" + this.positionSource );
                }

            }

            // An Entity DIE in the getListenerRadius
            if (gameEvent == GameEvent.ENTITY_DIE && context.sourceEntity() instanceof LivingEntity livingEntity) {
                if (!livingEntity.wasExperienceConsumed()) {
                    int i = livingEntity.getExperienceReward();
                    if (livingEntity.shouldDropExperience() && i > 0) {
                        //this.sculkSpreader.addCursors(BlockPos.containing(vec3.relative(Direction.UP, 0.5)), i); // Used to store "energy"
                    }


                    livingEntity.skipDropExperience();
                    this.positionSource
                            .getPosition(serverLevel)
                            .ifPresent(vec3x -> this.bloom(serverLevel, BlockPos.containing(vec3x), this.blockState, serverLevel.getRandom()));

                    // Try to add new Corruption Vein
                    //Vec3 deathPosition =
                   //Vec3i deathPosiotionI = new Vec3i(livingEntity.getBlockX(), livingEntity.getBlockY(), livingEntity.getBlockZ());
                    Corruption.LOGGER.info("Entity died on : " + livingEntity.getOnPos()  );

                    BlockPos blockPosDiedOn = livingEntity.getOnPos();
                    BlockState blockDiedOn = livingEntity.getBlockStateOn();

                    Corruption.LOGGER.info("Entity getBlockStateOn : " + blockDiedOn );
                    // -------------------- //
                    // // Block Selection // //
                    // -------------------- //
                    // If die on bedrock -> Skip

                    // If Die in Fluid pile up corruption from bottom

                    BlockState upperBlock = null;
                    // If die in air change floor to corrupted
                    while (blockDiedOn.isAir() || blockDiedOn.is(CorruptionBlock.CORRUPTED)) {
                        Corruption.LOGGER.info( diplayBlockInformation(blockDiedOn,blockPosDiedOn) + " Is not getting corrupted" );

                        blockPosDiedOn = blockPosDiedOn.atY( blockPosDiedOn.getY() - 1);

                        if (blockPosDiedOn.getY() < -64)
                        {
                            return false; // Did nothing
                        }

                        upperBlock = blockDiedOn;
                        blockDiedOn = serverLevel.getBlockState(blockPosDiedOn);


                    }


                    Corruption.LOGGER.info( diplayBlockInformation(blockDiedOn,blockPosDiedOn) + " Is GETTING corrupted" );

                    Corruption.LOGGER.info("Entity died Floor : " + blockPosDiedOn );
                    Corruption.LOGGER.info("Entity died Floor : " + blockDiedOn );


                    // ---------------------- //
                    // // Creating Blocks // //
                    // -------------------- //
                    serverLevel.setBlock(blockPosDiedOn, CorruptionBlock.CORRUPTION_BLOCK.defaultBlockState(), 3);

                    //origin.addAim(blockPosDiedOn);
                    if (false && upperBlock != null && upperBlock.is(Blocks.AIR))
                    {
                        BlockPos upperPos = blockPosDiedOn.atY(blockPosDiedOn.getY() + 1);


                        if ( serverLevel.setBlockAndUpdate(upperPos, CorruptionBlock.CORRUPTION_VEIN_BLOCK.defaultBlockState())) {
                            BlockState newBlockState = serverLevel.getBlockState(upperPos);
                            CorruptionVeinBlock.setUpFace(newBlockState);

                            if (!(newBlockState.getBlock() instanceof CorruptionVeinBlock)) {
                                Corruption.LOGGER.warn("Generated a new CORRUPTION_VEIN_BLOCK at " + upperPos + " But didn't get an CorruptionVeinBlock.class");
                            } else {
                                CorruptionVeinBlock newVeinBlock = (CorruptionVeinBlock) newBlockState.getBlock();


                            }

                        }



                        /*BlockPos originUpBlockPos = BlockPos.containing(vec3.relative(Direction.UP, 1));
                        BlockState originUpBlock = serverLevel.getBlockState(originUpBlockPos);
                        if (originUpBlock.is(CorruptionBlock.CORRUPTION_VEIN_BLOCK)) {
                            Block veinBlock = originUpBlock.getBlock();

                            if (SculkVeinBlock.class.isInstance(veinBlock)) {
                                SculkVeinBlock multi = (SculkVeinBlock) veinBlock;

                                // spreadAll(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, boolean bl) {
                                // multi.getSpreader().spreadAll(blockState3, serverLevel.get, upperPos);
                            }
                        }

                         */
                    }

                }

                return true;
            } else {
                return false;
            }

        }

        /*@VisibleForTesting
        public SculkSpreader getSculkSpreader() {
            return null;
        }
        */

        private void bloom(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
            serverLevel.setBlock(blockPos, blockState.setValue(SculkCatalystBlock.PULSE, true), 3);
            serverLevel.scheduleTick(blockPos, blockState.getBlock(), 8);
            serverLevel.sendParticles(ParticleTypes.ASH, blockPos.getX() + 0.5, blockPos.getY() + 6, blockPos.getZ() + 0.5, 25, 0.2, 0.0, 0.2, 0.0);
            serverLevel.playSound((Player)null, blockPos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 2.0F, 0.6F + randomSource.nextFloat() * 0.4F);
        }

        private static String diplayBlockInformation(BlockState blockState, BlockPos pos) {

            StringBuilder msg = new StringBuilder();

            msg.append(pos.getX()).append(",").append(pos.getY()).append(",").append(pos.getZ());

            msg.append(" : ").append(blockState.getBlock().getName());
            msg.append(" Tags > ");

            for (TagKey<Block> tag : blockState.getTags().toList() ) {
                msg.append(" ").append(tag).append(" ");
            }
            return msg.toString();
        }

    }
}

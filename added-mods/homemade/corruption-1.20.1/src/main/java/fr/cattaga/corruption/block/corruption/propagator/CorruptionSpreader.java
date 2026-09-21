package fr.cattaga.corruption.block.corruption.propagator;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;

/**
 * Class used to spread Corruption accross the level
 */
public class CorruptionSpreader {
    public static final int MAX_GROWTH_RATE_RADIUS = 24;
    public static final int MAX_CHARGE = 1000;
    public static final float MAX_DECAY_FACTOR = 0.5F;
    private static final int MAX_CURSORS = 32;
    public static final int SHRIEKER_PLACEMENT_RATE = 11;
    final boolean isWorldGeneration;
    private final TagKey<Block> replaceableBlocks;
    private final int growthSpawnCost;
    private final int noGrowthRadius;
    private final int chargeDecayRate;
    private final int additionalDecayRate;
    private List<net.minecraft.world.level.block.SculkSpreader.ChargeCursor> cursors = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public CorruptionSpreader(boolean bl, TagKey<Block> tagKey, int i, int j, int k, int l) {
        this.isWorldGeneration = bl;
        this.replaceableBlocks = tagKey;
        this.growthSpawnCost = i;
        this.noGrowthRadius = j;
        this.chargeDecayRate = k;
        this.additionalDecayRate = l;
    }

    public static CorruptionSpreader createLevelSpreader() {
        return new CorruptionSpreader(false, BlockTags.SCULK_REPLACEABLE, 10, 4, 10, 5);
    }

    public static CorruptionSpreader createWorldGenSpreader() {
        return new CorruptionSpreader(true, BlockTags.SCULK_REPLACEABLE_WORLD_GEN, 50, 1, 5, 10);
    }

    public TagKey<Block> replaceableBlocks() {
        return this.replaceableBlocks;
    }

    public int growthSpawnCost() {
        return this.growthSpawnCost;
    }

    public int noGrowthRadius() {
        return this.noGrowthRadius;
    }

    public int chargeDecayRate() {
        return this.chargeDecayRate;
    }

    public int additionalDecayRate() {
        return this.additionalDecayRate;
    }

    public boolean isWorldGeneration() {
        return this.isWorldGeneration;
    }

    @VisibleForTesting
    public List<net.minecraft.world.level.block.SculkSpreader.ChargeCursor> getCursors() {
        return this.cursors;
    }

    public void clear() {
        this.cursors.clear();
    }

    public void load(CompoundTag compoundTag) {
        if (compoundTag.contains("cursors", 9)) {
            this.cursors.clear();
            List<net.minecraft.world.level.block.SculkSpreader.ChargeCursor> list = net.minecraft.world.level.block.SculkSpreader.ChargeCursor.CODEC
                    .listOf()
                    .parse(new Dynamic<>(NbtOps.INSTANCE, compoundTag.getList("cursors", 10)))
                    .resultOrPartial(LOGGER::error)
                    .orElseGet(ArrayList::new);
            int i = Math.min(list.size(), 32);

            for (int j = 0; j < i; j++) {
                this.addCursor(list.get(j));
            }
        }
    }

    public void save(CompoundTag compoundTag) {
        net.minecraft.world.level.block.SculkSpreader.ChargeCursor.CODEC
                .listOf()
                .encodeStart(NbtOps.INSTANCE, this.cursors)
                .resultOrPartial(LOGGER::error)
                .ifPresent(tag -> compoundTag.put("cursors", tag));
    }

    public void addCursors(BlockPos blockPos, int i) {
        while (i > 0) {
            int j = Math.min(i, 1000);
            this.addCursor(new net.minecraft.world.level.block.SculkSpreader.ChargeCursor(blockPos, j));
            i -= j;
        }
    }

    private void addCursor(net.minecraft.world.level.block.SculkSpreader.ChargeCursor chargeCursor) {
        if (this.cursors.size() < 32) {
            this.cursors.add(chargeCursor);
        }
    }

    public void updateCursors(LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource, boolean bl) {
        /*if (!this.cursors.isEmpty()) {
            List<net.minecraft.world.level.block.SculkSpreader.ChargeCursor> list = new ArrayList<>();
            Map<BlockPos, net.minecraft.world.level.block.SculkSpreader.ChargeCursor> map = new HashMap<>();
            Object2IntMap<BlockPos> object2IntMap = new Object2IntOpenHashMap<>();

            for (net.minecraft.world.level.block.SculkSpreader.ChargeCursor chargeCursor : this.cursors) {
                chargeCursor.update(levelAccessor, blockPos, randomSource, this, bl);
                if (chargeCursor.charge <= 0) {
                    levelAccessor.levelEvent(3006, chargeCursor.getPos(), 0);
                } else {
                    BlockPos blockPos2 = chargeCursor.getPos();
                    object2IntMap.computeInt(blockPos2, (blockPosx, integer) -> (integer == null ? 0 : integer) + chargeCursor.charge);
                    net.minecraft.world.level.block.SculkSpreader.ChargeCursor chargeCursor2 = map.get(blockPos2);
                    if (chargeCursor2 == null) {
                        map.put(blockPos2, chargeCursor);
                        list.add(chargeCursor);
                    } else if (!this.isWorldGeneration() && chargeCursor.charge + chargeCursor2.charge <= 1000) {
                        chargeCursor2.mergeWith(chargeCursor);
                    } else {
                        list.add(chargeCursor);
                        if (chargeCursor.charge < chargeCursor2.charge) {
                            map.put(blockPos2, chargeCursor);
                        }
                    }
                }
            }

            for (Object2IntMap.Entry<BlockPos> entry : object2IntMap.object2IntEntrySet()) {
                BlockPos blockPos2 = entry.getKey();
                int i = entry.getIntValue();
                net.minecraft.world.level.block.SculkSpreader.ChargeCursor chargeCursor3 = map.get(blockPos2);
                Collection<Direction> collection = chargeCursor3 == null ? null : chargeCursor3.getFacingData();
                if (i > 0 && collection != null) {
                    int j = (int)(Math.log1p(i) / 2.3F) + 1;
                    int k = (j << 6) + MultifaceBlock.pack(collection);
                    levelAccessor.levelEvent(3006, blockPos2, k);
                }
            }

            this.cursors = list;
        }
         */
    }

    public static class ChargeCursor {
        private static final ObjectArrayList<Vec3i> NON_CORNER_NEIGHBOURS = Util.make(
                new ObjectArrayList<>(18),
                objectArrayList -> BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1))
                        .filter(blockPos -> (blockPos.getX() == 0 || blockPos.getY() == 0 || blockPos.getZ() == 0) && !blockPos.equals(BlockPos.ZERO))
                        .map(BlockPos::immutable)
                        .forEach(objectArrayList::add)
        );
        public static final int MAX_CURSOR_DECAY_DELAY = 1;
        private BlockPos pos;
        int charge;
        private int updateDelay;
        private int decayDelay;
        @Nullable
        private Set<Direction> facings;
        private static final Codec<Set<Direction>> DIRECTION_SET = Direction.CODEC.listOf().xmap(list -> Sets.newEnumSet(list, Direction.class), Lists::newArrayList);
       /* public static final Codec<net.minecraft.world.level.block.SculkSpreader.ChargeCursor> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                BlockPos.CODEC.fieldOf("pos").forGetter(net.minecraft.world.level.block.SculkSpreader.ChargeCursor::getPos),
                                Codec.intRange(0, 1000).fieldOf("charge").orElse(0).forGetter(net.minecraft.world.level.block.SculkSpreader.ChargeCursor::getCharge),
                                Codec.intRange(0, 1).fieldOf("decay_delay").orElse(1).forGetter(net.minecraft.world.level.block.SculkSpreader.ChargeCursor::getDecayDelay),
                                //Codec.intRange(0, Integer.MAX_VALUE).fieldOf("update_delay").orElse(0).forGetter(ChargeCursor -> ChargeCursor.updateDelay),
                                DIRECTION_SET.optionalFieldOf("facings").forGetter(chargeCursor -> Optional.ofNullable(chargeCursor.getFacingData()))
                        )
                        .apply(instance, net.minecraft.world.level.block.SculkSpreader.ChargeCursor::new)
        );*/

        private ChargeCursor(BlockPos blockPos, int i, int j, int k, Optional<Set<Direction>> optional) {
            this.pos = blockPos;
            this.charge = i;
            this.decayDelay = j;
            this.updateDelay = k;
            this.facings = optional.orElse(null);
        }

        public ChargeCursor(BlockPos blockPos, int i) {
            this(blockPos, i, 1, 0, Optional.empty());
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public int getCharge() {
            return this.charge;
        }

        public int getDecayDelay() {
            return this.decayDelay;
        }

        @Nullable
        public Set<Direction> getFacingData() {
            return this.facings;
        }

        private boolean shouldUpdate(LevelAccessor levelAccessor, BlockPos blockPos, boolean bl) {
            if (this.charge <= 0) {
                return false;
            } else if (bl) {
                return true;
            } else {
                return levelAccessor instanceof ServerLevel serverLevel ? serverLevel.shouldTickBlocksAt(blockPos) : false;
            }
        }

        public void update(LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource, net.minecraft.world.level.block.SculkSpreader sculkSpreader, boolean bl) {
           /* if (this.shouldUpdate(levelAccessor, blockPos, sculkSpreader.isWorldGeneration)) {
                if (this.updateDelay > 0) {
                    this.updateDelay--;
                } else {
                    BlockState blockState = levelAccessor.getBlockState(this.pos);
                    SculkBehaviour sculkBehaviour = getBlockBehaviour(blockState);
                    if (bl && sculkBehaviour.attemptSpreadVein(levelAccessor, this.pos, blockState, this.facings, sculkSpreader.isWorldGeneration())) {
                        if (sculkBehaviour.canChangeBlockStateOnSpread()) {
                            blockState = levelAccessor.getBlockState(this.pos);
                            sculkBehaviour = getBlockBehaviour(blockState);
                        }

                        levelAccessor.playSound(null, this.pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }

                    this.charge = sculkBehaviour.attemptUseCharge(this, levelAccessor, blockPos, randomSource, sculkSpreader, bl);
                    if (this.charge <= 0) {
                        sculkBehaviour.onDischarged(levelAccessor, blockState, this.pos, randomSource);
                    } else {
                        BlockPos blockPos2 = getValidMovementPos(levelAccessor, this.pos, randomSource);
                        if (blockPos2 != null) {
                            sculkBehaviour.onDischarged(levelAccessor, blockState, this.pos, randomSource);
                            this.pos = blockPos2.immutable();
                            if (sculkSpreader.isWorldGeneration() && !this.pos.closerThan(new Vec3i(blockPos.getX(), this.pos.getY(), blockPos.getZ()), 15.0)) {
                                this.charge = 0;
                                return;
                            }

                            blockState = levelAccessor.getBlockState(blockPos2);
                        }

                        if (blockState.getBlock() instanceof SculkBehaviour) {
                            this.facings = MultifaceBlock.availableFaces(blockState);
                        }

                        this.decayDelay = sculkBehaviour.updateDecayDelay(this.decayDelay);
                        this.updateDelay = sculkBehaviour.getSculkSpreadDelay();
                    }
                }
            }

            */
        }

        void mergeWith(net.minecraft.world.level.block.SculkSpreader.ChargeCursor chargeCursor) {
            /*this.charge = this.charge + chargeCursor.charge;
            chargeCursor.charge = 0;
            this.updateDelay = Math.min(this.updateDelay, chargeCursor.updateDelay);

             */
        }

        private static SculkBehaviour getBlockBehaviour(BlockState blockState) {
            return blockState.getBlock() instanceof SculkBehaviour sculkBehaviour ? sculkBehaviour : SculkBehaviour.DEFAULT;
        }

        private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource randomSource) {
            return Util.shuffledCopy(NON_CORNER_NEIGHBOURS, randomSource);
        }

        @Nullable
        private static BlockPos getValidMovementPos(LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource) {
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
            BlockPos.MutableBlockPos mutableBlockPos2 = blockPos.mutable();

            for (Vec3i vec3i : getRandomizedNonCornerNeighbourOffsets(randomSource)) {
                mutableBlockPos2.setWithOffset(blockPos, vec3i);
                BlockState blockState = levelAccessor.getBlockState(mutableBlockPos2);
                if (blockState.getBlock() instanceof SculkBehaviour && isMovementUnobstructed(levelAccessor, blockPos, mutableBlockPos2)) {
                    mutableBlockPos.set(mutableBlockPos2);
                    if (SculkVeinBlock.hasSubstrateAccess(levelAccessor, blockState, mutableBlockPos2)) {
                        break;
                    }
                }
            }

            return mutableBlockPos.equals(blockPos) ? null : mutableBlockPos;
        }

        private static boolean isMovementUnobstructed(LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
            if (blockPos.distManhattan(blockPos2) == 1) {
                return true;
            } else {
                BlockPos blockPos3 = blockPos2.subtract(blockPos);
                Direction direction = Direction.fromAxisAndDirection(
                        Direction.Axis.X, blockPos3.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE
                );
                Direction direction2 = Direction.fromAxisAndDirection(
                        Direction.Axis.Y, blockPos3.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE
                );
                Direction direction3 = Direction.fromAxisAndDirection(
                        Direction.Axis.Z, blockPos3.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE
                );
                if (blockPos3.getX() == 0) {
                    return isUnobstructed(levelAccessor, blockPos, direction2) || isUnobstructed(levelAccessor, blockPos, direction3);
                } else {
                    return blockPos3.getY() == 0
                            ? isUnobstructed(levelAccessor, blockPos, direction) || isUnobstructed(levelAccessor, blockPos, direction3)
                            : isUnobstructed(levelAccessor, blockPos, direction) || isUnobstructed(levelAccessor, blockPos, direction2);
                }
            }
        }

        private static boolean isUnobstructed(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction) {
            BlockPos blockPos2 = blockPos.relative(direction);
            return !levelAccessor.getBlockState(blockPos2).isFaceSturdy(levelAccessor, blockPos2, direction.getOpposite());
        }
    }
}

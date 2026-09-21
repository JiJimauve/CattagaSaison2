package fr.cattaga.corruption.block.corruption.vein;

import fr.cattaga.corruption.CorruptionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;


public class CorruptionVeinBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final MultifaceSpreader veinSpreader = new MultifaceSpreader(new SculkVeinSpreaderConfig(MultifaceSpreader.DEFAULT_SPREAD_ORDER));
    private final MultifaceSpreader sameSpaceSpreader = new MultifaceSpreader(
            new SculkVeinSpreaderConfig(MultifaceSpreader.SpreadType.SAME_POSITION)
    );

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    public CorruptionVeinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return veinSpreader;
    }

    public MultifaceSpreader getSameSpaceSpreader() {
        return this.sameSpaceSpreader;
    }


    //
    class SculkVeinSpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {
        private final MultifaceSpreader.SpreadType[] spreadTypes;

        public SculkVeinSpreaderConfig(MultifaceSpreader.SpreadType... spreadTypes) {
            super(CorruptionVeinBlock.this);
            this.spreadTypes = spreadTypes;
        }

        @Override
        public boolean stateCanBeReplaced(BlockGetter blockGetter, BlockPos blockPos, BlockPos blockPos2, Direction direction, BlockState blockState) {
            BlockState blockState2 = blockGetter.getBlockState(blockPos2.relative(direction));
            if (!blockState2.is(CorruptionBlock.CORRUPTED) && !blockState2.is(Blocks.MOVING_PISTON)) {
                if (blockPos.distManhattan(blockPos2) == 2) {
                    BlockPos blockPos3 = blockPos.relative(direction.getOpposite());
                    if (blockGetter.getBlockState(blockPos3).isFaceSturdy(blockGetter, blockPos3, direction)) {
                        return false;
                    }
                }

                FluidState fluidState = blockState.getFluidState();
                if (!fluidState.isEmpty() && !fluidState.is(Fluids.WATER)) {
                    return false;
                } else {
                    return blockState.is(BlockTags.FIRE)
                            ? false
                            : blockState.canBeReplaced() || super.stateCanBeReplaced(blockGetter, blockPos, blockPos2, direction, blockState);
                }
            } else {
                return false;
            }
        }

        @Override
        public MultifaceSpreader.SpreadType @NotNull [] getSpreadTypes() {
            return this.spreadTypes;
        }

        @Override
        public boolean isOtherBlockValidAsSource(BlockState blockState) {
            return !blockState.is(CorruptionBlock.CORRUPTION_VEIN_BLOCK);
        }
    }

    /*
    public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty DOWN = BooleanProperty.create("down");
	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
     */


    public static boolean setUpFace(BlockState blockState) {
        boolean hasUpdate = false;

        if (blockState.hasProperty(BlockStateProperties.UP)) {
            hasUpdate = true;

            blockState.setValue(BlockStateProperties.UP, false);
        }

        if (blockState.hasProperty(BlockStateProperties.DOWN)){
            hasUpdate = true;
            blockState.setValue(BlockStateProperties.DOWN, true);
        }

        if (blockState.hasProperty(BlockStateProperties.NORTH)){
            hasUpdate = true;

            blockState.setValue(BlockStateProperties.NORTH, true);
        }

        if (blockState.hasProperty(BlockStateProperties.EAST)){
            hasUpdate = true;
            blockState.setValue(BlockStateProperties.EAST, true);
        }

        if (blockState.hasProperty(BlockStateProperties.SOUTH)){
            hasUpdate = true;
            blockState.setValue(BlockStateProperties.SOUTH, true);
        }

        if (blockState.hasProperty(BlockStateProperties.WEST)) {
            hasUpdate = true;
            blockState.setValue(BlockStateProperties.WEST, true);
        }


        return hasUpdate;
    }
}

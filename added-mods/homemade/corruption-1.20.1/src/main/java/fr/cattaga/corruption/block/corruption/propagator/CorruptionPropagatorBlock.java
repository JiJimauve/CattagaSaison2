package fr.cattaga.corruption.block.corruption.propagator;

import fr.cattaga.corruption.CorruptionBlock;
import fr.cattaga.corruption.block.corruption.spreader.CorruptionSpreader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Specific Block used for the Propagator
 * It has an Entity in it that is used to deal with the ticking and modifying the level
 * Be carefull though, the data register are stored in the Block not in the Entity
 * CF CorruptionPropagatorBlockEntity
 *
 */
public class CorruptionPropagatorBlock extends BaseEntityBlock {

    public static final int MAX_ENERGY = 128;
    public static final IntegerProperty ENERGY = IntegerProperty.create("energy", 0, MAX_ENERGY);

    public static final int MAX_CHARGE = 6;
    public static final IntegerProperty CHARGE = IntegerProperty.create("charge", 0, MAX_CHARGE);

    public static final BooleanProperty PULSE = BlockStateProperties.BLOOM;

    private CorruptionPropagatorBlockEntity blockEntity;

    public CorruptionPropagatorBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any().setValue(PULSE, false)
                .setValue(ENERGY, 0).setValue(CHARGE, 2));

    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {

        if (this.blockEntity != null) {
            CorruptionSpreader.register(blockEntity, level);
        }


        super.onPlace(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (this.blockEntity != null) {
            CorruptionSpreader.unregister(blockEntity, level);
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PULSE,ENERGY,CHARGE);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(PULSE)) { // TODO CHECK IF CALL
            serverLevel.setBlock(blockPos, blockState.setValue(PULSE, false), 3);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        blockEntity = new CorruptionPropagatorBlockEntity(blockPos, blockState);
        return blockEntity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null :
                createTickerHelper(blockEntityType,
                CorruptionBlock.CORRUPTION_PROPAGATOR_BLOCK_ENTITY,
                CorruptionPropagatorBlockEntity::serverTick
                );

        // OK
        // return level.isClientSide ? null : createTickerHelper(blockEntityType, BlockEntityType.SCULK_CATALYST, SculkCatalystBlockEntity::serverTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

}


package fr.cattaga.corruption.block.corruption.egg;

import fr.cattaga.corruption.block.corruption.spreader.CorruptionSpreader;
import fr.cattaga.corruption.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Specific block used to Register
 *
 */
public class CorruptionEggBlock extends Block {

    public CorruptionEggBlock(Properties properties) {
        super(properties);
    }

    // TODO : ON TICK NEVER CALLED ON PLACE ON REMOVE NEITHER HOW DO WE DO IT
    // Maybe look for bed block ?

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        CorruptionSpreader.registerEgg(new Utils.BlockPosState(blockPos,blockState), level);
        super.onPlace(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        CorruptionSpreader.unregisterEgg(new Utils.BlockPosState(blockPos,blockState), level);
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

}


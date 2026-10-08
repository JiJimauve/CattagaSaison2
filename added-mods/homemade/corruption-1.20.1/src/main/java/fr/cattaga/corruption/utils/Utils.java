package fr.cattaga.corruption.utils;

import fr.cattaga.corruption.Corruption;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class Utils {

    /**
     * Simple Holder
     */
    public static class BlockPosState {
        public final BlockPos pos;
        public final BlockState state;
        public BlockPosState(BlockPos p, BlockState s) {
            pos = p;
            state = s;
        }
    }


    /**
     * Simple Holder
     */
    public static class BlockPosStateDir extends BlockPosState{
        public final int direction;
        public BlockPosStateDir(BlockPos p, BlockState s, int dir) {
            super(p,s);
            direction = dir;
        }
    }

    /**
     * Return the first above block of InitialPos that is an Air Block (null if not found)
     * @param serverLevel To get the Block
     * @param intialPos The Initial Pos
     * @return Pos of Air Block // Null if not found
     */
    public static BlockPos firstAirBlock(ServerLevel serverLevel, Vec3 intialPos) {
        int x = (int) intialPos.x;
        int y = (int) intialPos.y;
        int z = (int) intialPos.z;
        return firstAirBlock(serverLevel, new BlockPos(x,y,z));
    }

    /**
     * Return the first above block of InitialPos that is an Air Block (null if not found)
     * @param serverLevel To get the Block
     * @param intialPos The Initial Pos
     * @return Pos of Air Block // Null if not found
     */
    public static BlockPos firstAirBlock(ServerLevel serverLevel, Vec3i intialPos) {
        return firstAirBlock(serverLevel, new BlockPos(intialPos.getX(), intialPos.getY(), intialPos.getZ()));
    }

    /**
     * Return the first above block of InitialPos that is an Air Block (null if not found)
     * @param serverLevel To get the Block
     * @param intialPos The Initial Pos
     * @return Pos of Air Block // Null if not found
     */
    public static BlockPos firstAirBlock(ServerLevel serverLevel, BlockPos intialPos) {
        int y = intialPos.getY() + 1;
        BlockPos finalPos = null ; //intialPos.atY(y);

        BlockState bs = null;
        while( (bs == null || !bs.isAir()) && y < 64){
            y = y + 1;
            finalPos = intialPos.atY(y);
            bs = serverLevel.getBlockState(finalPos);
        }

        if (y > 64) {
            return null;
        }
        Corruption.LOGGER.debug("firstAirBlock of " + intialPos + " is " +finalPos );
        return finalPos;

    }

    /**
     * Display in Logger Corruption Debug properties of an BlockState (  firstMsg will be display on debug at first )
     * @param firstMsg Msg to display at first
     * @param blockState Block State where properties will be look into
     */
    public static void DisplayBlocStateProperties(String firstMsg, BlockState blockState) {
        Corruption.LOGGER.debug(firstMsg);
        DisplayBlocStateProperties(blockState);
    }

    /**
     * Display in Logger Corruption Debug properties of an BlockState
     * @param blockState Block State where properties will be look into
     */
    public static void DisplayBlocStateProperties(BlockState blockState) {

        for (Property p : blockState.getProperties()) {
            Corruption.LOGGER.debug(blockState.getClass().getName() + " :: " + p.getName() + " : " + blockState.getValue(p));
        }

    }

}

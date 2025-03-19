package train.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class WorldHelper
{
    public static IBlockState getBlockState(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos);
    }

    public static void markBlockForUpdate(World world, BlockPos pos)
    {
        if (world != null)
        {
            IBlockState state = WorldHelper.getBlockState(world, pos);
            markBlockForUpdate(state, world, pos);
        }
    }

    public static void markBlockForUpdate(IBlockState state, World world, BlockPos pos)
    {
        if (world != null)
        {
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }
}

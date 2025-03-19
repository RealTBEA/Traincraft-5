package train.common.tile.switchStand;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import train.common.api.blocks.TileSwitch;
import train.common.blocks.WorldHelper;
import train.common.library.BlockIDs;

import java.util.Random;

public class TilesignalSpanish extends TileSwitch {

    public int state = 1;// 0=red 1=green
    private int updateTicks = 0;
    private static Random rand = new Random();


    public void setState(int st)
    {
        state = st;
        WorldHelper.markBlockForUpdate(world, this.getPos());
    }

    public int getState(){
       return state;
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setInteger("state", state);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        state = tag.getInteger("state");
    }





    @Override
    public void updateEntity() {
        super.updateEntity();
        updateTicks++;

        /**
         * Remove any block on top of the wind mill
         */
        if (!world.isRemote) {
            if (updateTicks % 20 == 0) {
                BlockPos tempBlockPos = new BlockPos(this.getPos().getX(), this.getPos().getY() + 1,  this.getPos().getZ());
                if (!this.world.isAirBlock(tempBlockPos)) {
                    Block block = this.world.getBlockState(tempBlockPos).getBlock();
                    if (block != null) {
                        EntityItem entityitem = new EntityItem(world, this.getPos().getX(), this.getPos().getY() + 1,  this.getPos().getZ(), new ItemStack(Item.getItemFromBlock(BlockIDs.signalSpanish.block), 1));
                        float f3 = 0.05F;
                        entityitem.motionX = (float) rand.nextGaussian() * f3;
                        entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) rand.nextGaussian() * f3;
                        world.spawnEntity(entityitem);
                    }
                    this.world.setBlockToAir(this.getPos());
                }
                syncTileEntity();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.getPos().getX() - 1, this.getPos().getY() - 1, this.getPos().getZ() - 1, this.getPos().getX() + 2, this.getPos().getY() + 2, this.getPos().getZ() + 2);
    }
}

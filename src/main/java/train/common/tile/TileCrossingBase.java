package train.common.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import tmt.Tessellator;
import train.common.library.BlockIDs;
import train.common.library.Info;
import train.common.tile.tileSwitch.TileMFPBWigWag;

import java.util.Random;

public class TileCrossingBase extends TileMFPBWigWag {

    private long updateTicks = 0;
    private static Random rand = new Random();
    public float rotation =0;
    public boolean powered =false;
    public boolean flip = true;
    private static final ResourceLocation textureOn = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "Crossings/StandardCrossingBase/CrossingNoLightsOn1.png");
    private static final ResourceLocation textureOn1 = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "Crossings/StandardCrossingBase/CrossingNoLightsOn2.png");
    private static final ResourceLocation textureOff = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "Crossings/StandardCrossingBase/CrossingNoLightsOff.png");


    EntityItem entity = new EntityItem(worldObj, this.xCoord, this.yCoord + 1, this.zCoord, new ItemStack(Item.getItemFromBlock(BlockIDs.CrossingBase.block), 1));

    // private net.minecraft.client.audio.ISound bell = new PositionedSound(new ResourceLocation(Info.modID,"bell"))
    /*
    @SideOnly(Side.CLIENT)//be sure sound is only created on client
    private net.minecraft.client.audio.PositionedSound bell = new PositionedSound(new ResourceLocation(Info.modID,"bell")){

        @Override
        public float getXPosF() {return xCoord;}
        @Override
        public float getYPosF() {return yCoord+1;}
        @Override
        public float getZPosF() {return zCoord;}


    };

     */
    @Override
    public void updateEntity() {
        updateTicks++;
        if (worldObj.isRemote) {
            if (rotation > -85) {
                if (powered = getWorldObj().isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                    if (updateTicks % 10 == 0) {
                        flip = !flip;
                        if(flip) {
                            Tessellator.bindTexture(textureOn);
                        } else {
                            Tessellator.bindTexture(textureOn1);
                        }
                    }
                }
            } else {
                rotation = -85 + 1.75f;
                if (powered = getWorldObj().isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                    if (updateTicks % 10 == 0) {
                        flip = !flip;
                        if(flip) {
                            Tessellator.bindTexture(textureOn);
                        } else {
                            Tessellator.bindTexture(textureOn1);
                        }
                    }
                }
            }

            if (powered = getWorldObj().isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                rotation -= 1.75f;
            } else {
                if (rotation < 0) {
                    rotation += 1.75;
                    if (updateTicks % 10 == 0) {
                        flip = !flip;
                        //worldObj.playSound(xCoord, yCoord, zCoord, Info.resourceLocation + ":" + "bell", 1f, 1f, true);
                        if(flip) {
                            Tessellator.bindTexture(textureOn);
                        } else {
                            Tessellator.bindTexture(textureOn1);
                        }
                    }
                } else {
                    Tessellator.bindTexture(textureOff);
                }
            }
        }
        /**
         * Remove any block on top of this wind mill
         */
/*        else {
            if (updateTicks % 20 == 0) {
                if (!this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord)) {
                    Block block = this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
                    if (block != null) {
                        EntityItem entityitem = new EntityItem(worldObj, this.xCoord, this.yCoord + 1, this.zCoord, new ItemStack(Item.getItemFromBlock(BlockIDs.CrossingBase.block), 1));
                        float f3 = 0.05F;
                        entityitem.motionX = (float) rand.nextGaussian() * f3;
                        entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) rand.nextGaussian() * f3;
                        worldObj.spawnEntityInWorld(entityitem);
                    }
                    this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
                }
            }
        }*/
    }


/*    public void setFacing(ForgeDirection face) {

        if (facing != face)
            this.facing = face;
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord-1, yCoord-1, zCoord-1, xCoord + 2, yCoord + 4, zCoord + 2);
    }

}
package com.jcirmodelsquad.tcjcir.features.containers.render;

import com.jcirmodelsquad.tcjcir.features.containers.BlockFortyFootContainer;
import com.jcirmodelsquad.tcjcir.features.containers.TileFortyFootContainer;
import com.jcirmodelsquad.tcjcir.features.eti.TileTrainMonitor;
import com.jcirmodelsquad.tcjcir.models.containers.ModelISO_40FT_Block;
import com.jcirmodelsquad.tcjcir.models.containers.ModelISO_40FT_Item;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FortyFootContainerRender extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {

        //Tessellator.bindTexture(new ResourceLocation("tc:textures/trains/container56_Yellow.png"));
        //This will make your block brightness dependent from surroundings lighting.
        Tessellator tessellator = Tessellator.instance;
        TileFortyFootContainer theTileEntity = (TileFortyFootContainer)tileEntity;
        Block two = tileEntity.getWorldObj().getBlock(tileEntity.xCoord,tileEntity.yCoord - 1,tileEntity.zCoord);
        Block three = tileEntity.getWorldObj().getBlock(tileEntity.xCoord,tileEntity.yCoord - 2,tileEntity.zCoord);
        Block four = tileEntity.getWorldObj().getBlock(tileEntity.xCoord,tileEntity.yCoord - 3,tileEntity.zCoord);
               /* int skyLight = tileEntity.getWorldObj().getSkyBlockTypeBrightness(EnumSkyBlock.Block, (int)x,(int)y,(int)z);

               // skyLight= tileEntity.getWorldObj().getSkyBlockTypeBrightness(EnumSkyBlock.Sky, (int)x, (int)y, (int)z) << 20 | (skyLight<0?0:skyLight) << 4; OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  skyLight % 65536,  skyLight * 0.00001525878f);
                GL11.glColor4f(1,1,1,1);
                tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                skyLight=tileEntity.getWorldObj().getSkyBlockTypeBrightness(EnumSkyBlock.Sky, (int)x, (int)y, (int)z) << 20 | (Math.max(skyLight, 0)) << 4;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  skyLight % 65536,  skyLight * 0.00001525878f);*/
        GL11.glColor4f(1,1,1,1);
        tmt.Tessellator.bindTexture(new ResourceLocation("tc:textures/trains/ISO_40FT_" + theTileEntity.currentColor + ".png"));
        GL11.glColor4f(1,1,1,1);
        //tessellator.setBrightness(theBlock.getMixedBrightnessForBlock(tileEntity.getWorldObj(), (int)x, (int)y, (int)z));
        tessellator.setBrightness(15);

        GL11.glPushMatrix();

       //GL11.glRotatef(180,0,10,1);

        if (two instanceof BlockFortyFootContainer) {
            GL11.glTranslated(x + 0.5, y + 0.8, z + 0.44F);
        } else if (three instanceof BlockFortyFootContainer) {
            GL11.glTranslated(x + 0.5, y + 1, z + 0.44F);
        } else {
            GL11.glTranslated(x, y + 1, z);
        }

        int dir = tileEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

        switch(dir) {
            case 0: {
                GL11.glRotatef(180F, 1F, 0F, 1F);
                break;
            }
            case 1: {
                GL11.glRotatef(180F, 0, 0F, 1F);
                break;
            }
            case 2: {
                GL11.glRotatef(180F, 1F, 0F, -1F);
                break;
            }
            case 3: {
                GL11.glRotatef(180F, 1F, 0F, 0F);
                break;
            }
        }


        ModelISO_40FT_Block theContainer = new ModelISO_40FT_Block();
        theContainer.render(null,0, 0, 0,0, 0, .0625f);
        GL11.glPopMatrix();
    }

    @Override
    protected void bindTexture(ResourceLocation p_147499_1_){
    }
}

package train.common.entity.stock.freight;

import fexcraft.tmt.slim.ModelBase;
import ebf.tim.api.SkinRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.api.*;
import train.common.items.ItemRollingStock;
import train.common.library.Info;
import train.common.library.ItemIDs;

public class EntityTenderMILW extends Freight {

    public static final Item thisItem = new ItemRollingStock(Info.modID+":+train_milw_tender", Traincraft.tcTab); 
    public EntityTenderMILW(World world, double x, double y, double z) {
    super(world, x, y, z); }
    public EntityTenderMILW(World world) {
    super (world); } 
    //main stats
    @Override
    public String transportName(){return "Tender MILW";}
    @Override
    public String transportcountry(){return "";}
    @Override
    public String transportYear(){return "";}
    @Override
    public boolean isFictional(){return false;}

    @Override
    public void registerSkins(){
    }

    @Override
    public float transportTopSpeed(){return 0;}

    @Override
    public int getInventoryRows(){return 0;}

	@Override
	public float getPlayerScale(){ 	return 0.65f;}

	@Override
	public float transportMetricHorsePower(){return 0;}

	@Override
	public String[] additionalItemText() { return new String[] {"Water capacity: 16000mb"};}

	@Override
	public float weightKg(){ return 1.5f;}

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Items.coal, 2), 
new ItemStack(ItemIDs.bogie.item, 2), 
new ItemStack(ItemIDs.steelframe.item, 1), 
null, 
null,
 null, 
null, 
null, 
null,
new ItemStack(thisItem)
        };
    }


@Override
public int getTier(){
return 3;
}
    //Model stuff
    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new train.client.render.models.ModelMILWTender()};}
    @Override
    public float[][] modelOffsets(){return new float[][]{{0.15f, -0.055f, 0.025f}};}
    @Override
    public float[][] modelRotations(){return new float[][]{{0.0f, 0.0f, 0.0f}};}
    //these are separated for being fiddly.
    @Override
    public float[][] getRiderOffsets(){return new float[][]{{0,1.2f, 0f}};}
    @Override
    public float[] getHitboxSize(){return new float[]{3.799999952316284f,2.1f,1.1f};}
    @Override
    public int[] getTankCapacity(){return new int[]{16000};}
}


    //these only change in very specific use cases.

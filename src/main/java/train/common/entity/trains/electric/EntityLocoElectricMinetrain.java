package train.common.entity.trains.electric;

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

public class EntityLocoElectricMinetrain extends ElectricTrain {

    public static final Item thisItem = new ItemRollingStock(Info.modID+":+train_mine_train", Traincraft.tcTab); 
    public EntityLocoElectricMinetrain(World world, double x, double y, double z) {
    super(world, x, y, z); }
    public EntityLocoElectricMinetrain(World world) {
    super (world); } 
    //main stats
    @Override
    public String transportName(){return "Loco Electric Minetrain";}
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
    public float transportTopSpeed(){return 40;}

    @Override
    public int getInventoryRows(){return 0;}

	@Override
	public float getPlayerScale(){ 	return 0.65f;}

	@Override
	public float transportMetricHorsePower(){return 500;}

	@Override
	public String[] additionalItemText() { return new String[] {""};}

	@Override
	public float weightKg(){ return 0.0f;}

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Items.iron_ingot, 2), 
new ItemStack(ItemIDs.ironBogie.item, 2), 
new ItemStack(ItemIDs.ironFrame.item, 1), 
new ItemStack(Items.iron_ingot, 1), 
null,
 new ItemStack(ItemIDs.controls.item, 1), 
new ItemStack(ItemIDs.transformer.item, 1), 
new ItemStack(ItemIDs.electmotor.item, 2), 
new ItemStack(Items.redstone, 1),
new ItemStack(thisItem)
        };
    }


@Override
public int getTier(){
return 1;
}
    //Model stuff
    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new train.client.render.models.ModelLocoMinetrain()};}
    @Override
    public float[][] modelOffsets(){return new float[][]{{-0.8f, 0.47f, 0.0f}};}
@Override
    public float[][] modelRotations(){return new float[][]{{0f,180f,180f}};}
    //these are separated for being fiddly.
    @Override
    public float[][] getRiderOffsets(){return new float[][]{{0,1.2f, 0f}};}
    @Override
    public float[] getHitboxSize(){return new float[]{2.059999942779541f,2.1f,1.1f};}
    //Train specific stuff
    @Override
    public String transportFuelType(){return "electric";}
    public int[] getTankCapacity(){return new int[]{2250};}

}


    //these only change in very specific use cases.

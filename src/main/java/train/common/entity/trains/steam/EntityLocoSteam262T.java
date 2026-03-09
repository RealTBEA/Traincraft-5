package train.common.entity.trains.steam;

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

public class EntityLocoSteam262T extends SteamTrain {

    public static final Item thisItem = new ItemRollingStock(Info.modID+":+train_262t", Traincraft.tcTab); 
    public EntityLocoSteam262T(World world, double x, double y, double z) {
    super(world, x, y, z); }
    public EntityLocoSteam262T(World world) {
    super (world); } 
    //main stats
    @Override
    public String transportName(){return "Loco Steam 262T";}
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
    public float transportTopSpeed(){return 70;}

    @Override
    public int getInventoryRows(){return 0;}

	@Override
	public float getPlayerScale(){ 	return 0.65f;}

	@Override
	public float transportMetricHorsePower(){return 300;}

	@Override
	public String[] additionalItemText() { return new String[] {""};}

	@Override
	public float weightKg(){ return 0.0f;}

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                null, 
new ItemStack(ItemIDs.ironBogie.item, 4), 
new ItemStack(ItemIDs.woodenFrame.item, 1), 
null, 
new ItemStack(ItemIDs.ironChimney.item, 1), 
new ItemStack(ItemIDs.ironCab.item, 1), 
new ItemStack(ItemIDs.boiler.item, 1), 
new ItemStack(ItemIDs.firebox.item, 1), 
null,
new ItemStack(thisItem)
        };
    }


@Override
public int getTier(){
return 1;
}
    //Model stuff
    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new train.client.render.models.ModelT262()};}
    @Override
    public float[][] modelOffsets(){return new float[][]{{0.0f, -0.15f, 0.0f}};}
    @Override
    public float[][] modelRotations(){return new float[][]{{0.0f, 0.0f, 0.0f}};}
    //these are separated for being fiddly.
    @Override
    public float[][] getRiderOffsets(){return new float[][]{{0,1.2f, 0f}};}
    @Override
    public float[] getHitboxSize(){return new float[]{4.200000047683716f,2.1f,1.1f};}
    //Train specific stuff
    @Override
    public String transportFuelType(){return "steam";}
    @Override
    public int[] getTankCapacity(){return new int[]{4250, 850};}

}


    //these only change in very specific use cases.

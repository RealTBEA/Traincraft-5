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

public class EntityFreightMinetrain extends Freight {

    public static final Item thisItem = new ItemRollingStock(Info.modID+":+train_mine_cart", Traincraft.tcTab); 
    public EntityFreightMinetrain(World world, double x, double y, double z) {
    super(world, x, y, z); }
    public EntityFreightMinetrain(World world) {
    super (world); } 
    //main stats
    @Override
    public String transportName(){return "Freight Minecart Yellow";}
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
    public int getInventoryRows(){return 2;}

	@Override
	public float getPlayerScale(){ 	return 0.65f;}

	@Override
	public float transportMetricHorsePower(){return 0;}

	@Override
	public String[] additionalItemText() { return new String[] {"Cargo: opaque blocks"};}

	@Override
	public float weightKg(){ return 0.5f;}

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Items.iron_ingot, 2), 
new ItemStack(ItemIDs.ironBogie.item, 2), 
new ItemStack(ItemIDs.ironFrame.item, 1), 
new ItemStack(Items.iron_ingot, 2), 
null,
 null, 
null, 
null, 
new ItemStack(Blocks.chest, 1),
new ItemStack(thisItem)
        };
    }


@Override
public int getTier(){
return 1;
}
    //Model stuff
    @Override
    public ModelBase[] getModel(){return new ModelBase[]{new train.client.render.models.ModelMinetrain()};}
    @Override
    public float[][] modelOffsets(){return new float[][]{{0.0f, 0.47f, 0.0f}};}
@Override
    public float[][] modelRotations(){return new float[][]{{0f,180f,180f}};}
    //these are separated for being fiddly.
    @Override
    public float[][] getRiderOffsets(){return new float[][]{{0,1.2f, 0f}};}
    @Override
    public float[] getHitboxSize(){return new float[]{1.399999976158142f,2.1f,1.1f};}

}


    //these only change in very specific use cases.

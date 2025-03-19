package train.common.tile.switchStand;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import train.common.api.blocks.TileRenderFacing;
import train.common.blocks.WorldHelper;
import train.common.blocks.blockSwitch.BlockSpeedSign;

public class TileSpeedSign extends TileRenderFacing {

	private int skinstate;

	public TileSpeedSign(){
	}
	public TileSpeedSign(BlockSpeedSign block){
		host = block;
	}
	public void setSkinstate(int skinstate) {
		this.skinstate = skinstate;
		WorldHelper.markBlockForUpdate(world, getPos());

	}

	public int getSkinstate() {
		return skinstate;
	}

	public void increaseSkinState(){
		if (skinstate >= 4){
			skinstate = 0;
		} else {
			skinstate++;
		}

		WorldHelper.markBlockForUpdate(world, getPos());
	}



	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		if(nbtTag.hasKey("skinstate")){
			skinstate = nbtTag.getInteger("skinstate");
		}

		else {
			System.out.println("No Skins");
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		nbtTag.setInteger("skinstate", this.skinstate);


	}

	public SPacketUpdateTileEntity getDescriptionPacket() {

		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new SPacketUpdateTileEntity(getPos(), 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.getPos().getX() - 1, this.getPos().getY() - 1, this.getPos().getZ() - 1, this.getPos().getX() + 2, this.getPos().getY() + 2, this.getPos().getZ() + 2);
	}
}

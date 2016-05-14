package src.train.common.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileLantern extends TileEntity {
	protected Random rand = new Random();
	public int randomColor = (rand.nextInt()*0xFFFFFF<<0);
	protected Side side;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		randomColor = nbt.getInteger("randomColor");
		super.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("randomColor", randomColor);
		super.writeToNBT(nbt);
	}

	//TODO Packets
	/*
	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.getTEPClient(this);
	}
	*/
	
	public void handlePacketDataFromServer(int color) {
		side = FMLCommonHandler.instance().getEffectiveSide();
		randomColor = color;
		/**
		 * if the color is received server side then send it to the client. This situation happens when the color is set in the GUI
		 * Color has to pass through the server to be registered
		 */
		if(side== Side.SERVER) {
			//TODO Packets
			// PacketHandler.sendPacketToClients(PacketHandler.getTEPClient(this), worldObj, this.xCoord, this.yCoord, this.zCoord, 12D);
		}
	}
	
	public String getColor() {
		return String.format("#%06X", (0xFFFFFF & randomColor));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
}
package fr.unwired.nrj.objects.blocks.energyexchanger;

import fr.unwired.nrj.network.Network;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityLinkableBlock extends TileEntity {
	
	protected Network network;

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return super.writeToNBT(compound);
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
	
	public Network getNetwork() {
		return network;
	}
	

}

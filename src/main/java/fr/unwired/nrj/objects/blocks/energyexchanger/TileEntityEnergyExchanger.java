package fr.unwired.nrj.objects.blocks.energyexchanger;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class TileEntityEnergyExchanger extends TileEntityLinkableBlock implements ITickable {
	
	protected EnergyStorageEnergyExchanger energyStorage;
	private Boolean isConnected;

	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("energyStored", getEnergyStored());
		compound.setBoolean("isConnected", isConnected());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		energyStorage.setEnergyStored(compound.getInteger("energyStored"));
		isConnected = compound.getBoolean("isConnected");
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(energyStorage) : null;
	}
	
	public int getEnergyStored() {
		return energyStorage.getEnergyStored();
	}
	
	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}
	
	public float getEnergyStoredPercentage() {
		return energyStorage.getEnergyStoredPercentage();
	}
	

	public void setEnergyStored(int es) {
		energyStorage.setEnergyStored(es);
	}

	public String getSidesCapability() {
		String str = ""; 
		for (EnumFacing side : EnumFacing.VALUES) {
			str += "[" + side.toString() + "]";
			TileEntity te = world.getTileEntity(pos.offset(side));
			if (te == null) continue;
			if (te.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())) {
				str += te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).toString();
			}
		}
		return str;
	}
	
	public void setConnected(Boolean isConnected) {
		this.isConnected = isConnected;
		markDirty();
	}
	
	public Boolean isConnected() {
		return this.isConnected == null ? false : this.isConnected;
	}
	
	public abstract boolean canSendToNetwork();

	public abstract boolean canReceiveFromNetwork();
	
	public abstract void update();
}
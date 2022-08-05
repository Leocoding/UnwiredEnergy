package fr.unwired.nrj.objects.blocks.energyexchanger;

import net.minecraftforge.energy.IEnergyStorage;

public abstract class EnergyStorageEnergyExchanger implements IEnergyStorage {
	
	protected int energyStored;
	protected int maxEnergyStored;
	
	public EnergyStorageEnergyExchanger(int max) {
		energyStored = 0;
		maxEnergyStored = max;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = energyStored + maxReceive > maxEnergyStored ? maxEnergyStored - energyStored : maxReceive;
		if (!simulate) energyStored += energyReceived;
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = energyStored - maxExtract < 0 ? energyStored : maxExtract;
		if (!simulate) energyStored -= energyExtracted;
		return energyExtracted;
	}
	
	public void setEnergyStored(int value) {
		energyStored = value;
	}

	@Override
	public int getEnergyStored() {
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored() {
		return maxEnergyStored;
	}
	
	public float getEnergyStoredPercentage() {
		return (float)energyStored / (float)maxEnergyStored;
	}

	@Override
	public abstract boolean canExtract();

	@Override
	public abstract boolean canReceive();
	
	public abstract boolean canExtractToNetwork();
	
	public abstract boolean canReceiveFromNetwork();
	
}

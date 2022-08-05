package fr.unwired.nrj.objects.blocks.energizer;

import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageMachine implements IEnergyStorage {
	
	private int energyStored;
	private int maxEnergyStored;

	public EnergyStorageMachine(int max) {
		this(max, 0);
	}
	
	public EnergyStorageMachine(int max, int value) {
		if (value > max) value = max;
		energyStored = value;
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

	@Override
	public int getEnergyStored() {
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored() {
		return maxEnergyStored;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return energyStored < maxEnergyStored;
	}

	public void setEnergyStored(int e) {
		if (e > 0 && e <= maxEnergyStored) {
			energyStored= e;
		}
	}

	public float getEnergyStoredPercentage() {
		return (float)energyStored / (float)maxEnergyStored;
	}

}

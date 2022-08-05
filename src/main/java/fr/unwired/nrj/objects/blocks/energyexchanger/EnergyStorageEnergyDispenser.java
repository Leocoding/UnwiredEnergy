package fr.unwired.nrj.objects.blocks.energyexchanger;

// Envoie dans le Network
// Reçoit des cables
public class EnergyStorageEnergyDispenser extends EnergyStorageEnergyExchanger {

	public EnergyStorageEnergyDispenser(int max) {
		super(max);
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return energyStored < maxEnergyStored;
	}

	@Override
	public boolean canExtractToNetwork() {
		return true;
	}

	@Override
	public boolean canReceiveFromNetwork() {
		return false;
	}
	
}
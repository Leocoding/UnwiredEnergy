package fr.unwired.nrj.objects.blocks.energyexchanger;

//	Reçoit du Network
//	Envoie dans les cables
public class EnergyStorageEnergyAcceptor extends EnergyStorageEnergyExchanger {

	public EnergyStorageEnergyAcceptor(int max) {
		super(max);
	}

	@Override
	public boolean canExtract() {
		return energyStored > 0;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

	public boolean canExtractToNetwork() {
		return false;
	}

	public boolean canReceiveFromNetwork() {
		return true;
	}
	
}
package fr.unwired.nrj.objects.blocks.energyexchanger;

import fr.unwired.nrj.network.Network;

public class TileEntityEnergyDispenser extends TileEntityEnergyExchanger {
	
	public TileEntityEnergyDispenser() {
		energyStorage = new EnergyStorageEnergyDispenser(100000);
		network = new Network(this);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if(isConnected()) {
				network.receiveEnergy(energyStorage.extractEnergy(network.getEnergyToExtractFromDisp(), false), false);
			}
		}
	}

	@Override
	public boolean canSendToNetwork() {
		return false;
	}

	@Override
	public boolean canReceiveFromNetwork() {
		return energyStorage.canReceive();
	}
	
	
}

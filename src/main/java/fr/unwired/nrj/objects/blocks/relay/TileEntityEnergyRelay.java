package fr.unwired.nrj.objects.blocks.relay;

import fr.unwired.nrj.network.Network;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityLinkableBlock;

public class TileEntityEnergyRelay extends TileEntityLinkableBlock {
	
	private boolean isLinked;
	
	private Network network;
	
	public TileEntityEnergyRelay() {
		isLinked = false;
	}
	
	public boolean isLinked() {
		return isLinked;
	}
	
	public void setNetwork(Network n) {
		if (n != null) network = n;
	}
	
	public void removePathFromRelay() {
		if (network == null) return;
		network.removePathFromRelay(this);
	}
	
}

package fr.unwired.nrj.network;

import java.util.ArrayList;
import java.util.List;

import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyAcceptor;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyDispenser;
import fr.unwired.nrj.objects.blocks.relay.TileEntityEnergyRelay;
import net.minecraftforge.energy.IEnergyStorage;

public class Network implements IEnergyStorage {
	
	private int energyStored;
	
	
	private List<Path> paths;
	
	private int energyToExtractFromDisp;
	
	public Network(TileEntityEnergyDispenser tileEntityEnergyDispenser) {
		energyStored = 0;
		energyToExtractFromDisp = 0;
		paths = new ArrayList<Path>();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = maxReceive;
		if (!simulate) energyStored += energyReceived;
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = maxExtract;
		if (!simulate) energyStored -= energyExtracted;
		return energyExtracted;
	}
	
	public int extractFromNetwork(int maxExtract) {
		return getValidAcceptors().size() > 0 ? extractEnergy(Math.min(maxExtract, energyStored / getValidAcceptors().size()), false):0;
	}

	private List<TileEntityEnergyAcceptor> getValidAcceptors() {
		List<TileEntityEnergyAcceptor> validAcceptors = new ArrayList<TileEntityEnergyAcceptor>();
		energyToExtractFromDisp = 0;
		for (TileEntityEnergyAcceptor ted : getAcceptors()) {
			if (ted.isValid()) { 
				validAcceptors.add(ted);
				energyToExtractFromDisp += ted.getMaxEnergyStored() - ted.getEnergyStored();
			}

		}
		return validAcceptors;
	}

	public List<TileEntityEnergyAcceptor> getAcceptors() {
		List<TileEntityEnergyAcceptor> acceptors = new ArrayList<TileEntityEnergyAcceptor>();
		for (Path p : paths) {
			if (!acceptors.contains(p.getDestination())) {
				acceptors.add(p.getDestination());
			}
		}
		return acceptors;
	}

	public void addPath(Path p) {
		paths.add(p);
	}
	
	public void removePathFromAcceptor(TileEntityEnergyAcceptor tile) {
		Path path = null;
		for (Path p : paths) {
			if (tile == p.getDestination()) {
				path = p;
				p.getDestination().setNetwork(null);
			}
		}
		if (path != null) paths.remove(path);
	}

	public void removePathFromRelay(TileEntityEnergyRelay tile) {
		List<Path> newPaths = new ArrayList<Path>(paths);
		for (Path p : newPaths) {
			if (p.isRelayIn(tile)) removePathFromAcceptor(p.getDestination()); paths.remove(p);
		}
		paths = newPaths;
	}
	
	public boolean isIn(TileEntityEnergyAcceptor tile) {
		return getAcceptors().contains(tile);
	}
	
	@Override
	public int getEnergyStored() {
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored() {
		return 0;
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}
	
	public int getEnergyToExtractFromDisp() {
		return this.energyToExtractFromDisp;
	}
	
}

package fr.unwired.nrj.network;

import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyAcceptor;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyDispenser;
import fr.unwired.nrj.objects.blocks.relay.TileEntityEnergyRelay;

import java.util.ArrayList;
import java.util.List;

public class Path {

	TileEntityEnergyDispenser source;
	TileEntityEnergyAcceptor destination;
	List<TileEntityEnergyRelay> relays;
	
	public Path() {
		relays = new ArrayList<TileEntityEnergyRelay>();
	}
	
	public Path(TileEntityEnergyDispenser src, TileEntityEnergyAcceptor dest, List<TileEntityEnergyRelay> r) {
		source = src;
		destination = dest;
		relays = r;
	}

	public void setSource(TileEntityEnergyDispenser src) {
		source = src;
	}

	public void setDestination(TileEntityEnergyAcceptor dest) {
		destination = dest;
	}

	public TileEntityEnergyDispenser getSource() {
		return source;
	}

	public TileEntityEnergyAcceptor getDestination() {
		return destination;
	}
	
	public void addRelay(TileEntityEnergyRelay r) {
		relays.add(r);
	}
	
	public boolean isRelayIn(TileEntityEnergyRelay relay) {
		for (TileEntityEnergyRelay r : relays) {
			if (r == relay) return true;
		}
		return false;
	}

	public List<TileEntityEnergyRelay> getRelays() {
		return relays;
	}
		
	@Override
	public String toString() {
		String str = "";
		str += "[" + source.toString() + "]\n(";
		for (TileEntityEnergyRelay tile : relays) {
			str += tile.toString() + ",";
		}
		str += ")\n[" + destination.toString() + "]";
		return str;
	}
}

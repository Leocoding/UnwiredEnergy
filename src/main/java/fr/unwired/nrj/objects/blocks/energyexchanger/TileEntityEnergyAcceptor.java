package fr.unwired.nrj.objects.blocks.energyexchanger;

import fr.unwired.nrj.network.Network;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergyAcceptor extends TileEntityEnergyExchanger {
	
	private static final int MAX_EXTRACT = 100;
	
	
	public TileEntityEnergyAcceptor() {
		energyStorage = new EnergyStorageEnergyAcceptor(100000);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			for (EnumFacing side : EnumFacing.VALUES) {
				TileEntity te = world.getTileEntity(pos.offset(side));
				if (te == null) continue;
				if (te.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())) {
					IEnergyStorage handler = te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());
					if (handler == null) continue;
					if (energyStorage.canExtract() && handler.canReceive()) {
						energyStorage.extractEnergy(handler.receiveEnergy(MAX_EXTRACT, false), false);
					}
				}
			}
			if (network != null) {
				energyStorage.receiveEnergy(network.extractFromNetwork(energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored()), false);
			}
		}
	}

	@Override
	public boolean canSendToNetwork() {
		return false;
	}

	@Override
	public boolean canReceiveFromNetwork() {
		return isConnected() && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored();
	}

	public boolean isValid() {
//		System.out.print(Integer.toString(energyStorage.getEnergyStored()) + "<" + Integer.toString(energyStorage.getMaxEnergyStored()));
		return canReceiveFromNetwork();
	}
	
	public void setNetwork(Network n) {
		network = n;
	}
	
}

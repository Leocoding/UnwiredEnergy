package fr.unwired.nrj.util.handlers;

import fr.unwired.nrj.objects.blocks.energizer.TileEntityEnergizer;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyAcceptor;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyDispenser;
import fr.unwired.nrj.objects.blocks.relay.TileEntityEnergyRelay;
import fr.unwired.nrj.util.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityEnergyDispenser.class, new ResourceLocation(References.MODID + ":energy_dispenser"));
		GameRegistry.registerTileEntity(TileEntityEnergyAcceptor.class, new ResourceLocation(References.MODID + ":energy_acceptor"));
		GameRegistry.registerTileEntity(TileEntityEnergizer.class, new ResourceLocation(References.MODID + ":energizer_block"));
		GameRegistry.registerTileEntity(TileEntityEnergyRelay.class, new ResourceLocation(References.MODID + ":energy_relay"));
	}
	
}

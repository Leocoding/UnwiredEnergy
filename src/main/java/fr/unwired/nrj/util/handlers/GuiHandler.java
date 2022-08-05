package fr.unwired.nrj.util.handlers;

import fr.unwired.nrj.gui.GuiEnergizer;
import fr.unwired.nrj.gui.GuiEnergyExchanger;
import fr.unwired.nrj.objects.blocks.energizer.ContainerEnergizer;
import fr.unwired.nrj.objects.blocks.energizer.TileEntityEnergizer;
import fr.unwired.nrj.objects.blocks.energyexchanger.ContainerEnergyExchanger;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyExchanger;
//import fr.unwired.nrj.objects.blocks.machine.energizer.ContainerEnergizer;
import fr.unwired.nrj.util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		System.out.println("GuiHandler init");
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == References.GUI_ENERGY_EXCHANGER) {
			return new ContainerEnergyExchanger(player.inventory, (TileEntityEnergyExchanger)world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == References.GUI_ENERGIZER) {
			return new ContainerEnergizer(player.inventory, (TileEntityEnergizer)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == References.GUI_ENERGY_EXCHANGER) {
			return new GuiEnergyExchanger(player.inventory, (TileEntityEnergyExchanger)world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == References.GUI_ENERGIZER) {
			return new GuiEnergizer(player.inventory, (TileEntityEnergizer)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}

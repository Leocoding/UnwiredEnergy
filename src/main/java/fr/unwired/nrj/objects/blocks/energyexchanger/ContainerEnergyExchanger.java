package fr.unwired.nrj.objects.blocks.energyexchanger;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.packets.PacketGuiShortFix;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEnergyExchanger extends Container {

	private final static int ENERGY_ID = 0;
	private final static int CONNECTION_ID = 1;
	
	private TileEntityEnergyExchanger tile;
	
	public ContainerEnergyExchanger(TileEntityEnergyExchanger te) {
		tile = te;
	}
	
	public ContainerEnergyExchanger(InventoryPlayer inv, TileEntityEnergyExchanger te) {
		this(te);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		System.out.println(tile.getEnergyStored());
		listener.sendWindowProperty(this, ENERGY_ID, tile.getEnergyStored());
		listener.sendWindowProperty(this, CONNECTION_ID, tile.isConnected()?1:0);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (IContainerListener icl : listeners) {
			if ((this.tile.getEnergyStored() > Short.MAX_VALUE
					|| this.tile.getEnergyStored() < Short.MIN_VALUE)
					&& icl instanceof EntityPlayerMP) {
				UnwiredMod.network.sendTo(
						new PacketGuiShortFix(ENERGY_ID,
								this.tile.getEnergyStored()),
						((EntityPlayerMP) icl));
			} else {

				icl.sendWindowProperty(this, ENERGY_ID, tile.getEnergyStored());
			}
			icl.sendWindowProperty(this, CONNECTION_ID, tile.isConnected()?1:0);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
		case ENERGY_ID:
			tile.setEnergyStored(data);
			break;
		case CONNECTION_ID:
			tile.setConnected(data == 1 ? true : false);
		default:
			break;
		}
	}
}

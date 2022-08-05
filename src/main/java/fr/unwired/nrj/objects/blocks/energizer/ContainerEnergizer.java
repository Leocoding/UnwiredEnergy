package fr.unwired.nrj.objects.blocks.energizer;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.objects.blocks.energizer.slot.EnergizerOutSlot;
import fr.unwired.nrj.objects.blocks.energizer.slot.EnergizerSlot;
import fr.unwired.nrj.packets.PacketGuiShortFix;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEnergizer extends Container {
	
	private final static int ENERGY_ID = 0;
	private final static int PROGRESS_ID = 1;
	private final static int COOK_ID = 2;


	public final static int SLOT1_ID = 0;
	public final static int SLOT2_ID = 1;

	private final TileEntityEnergizer tile;
	
	public ContainerEnergizer(InventoryPlayer player, TileEntityEnergizer tileentity) 
	{
		tile = tileentity;
		
		// Slot machine
		
		addSlotToContainer(new EnergizerSlot(tile, 0, 32, 32));
		addSlotToContainer(new EnergizerOutSlot(tile, 1, 96, 32));
		
		// Ajout des slots d'inventaire
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, ENERGY_ID, tile.getEnergyStored());
		listener.sendWindowProperty(this, PROGRESS_ID, tile.getProgress());
		listener.sendWindowProperty(this, COOK_ID, tile.getCookTime());
	}

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
			icl.sendWindowProperty(this, PROGRESS_ID, tile.getProgress());
			icl.sendWindowProperty(this, COOK_ID, tile.getCookTime());
		}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stackToReturn = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
        	
        	ItemStack stack = slot.getStack();
        	stackToReturn = stack.copy();
        	
            if (index < 2) {
                if (!this.mergeItemStack(stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        
        return stackToReturn;
	
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
			case PROGRESS_ID:
				tile.setProgress(data);
				break;
			case ENERGY_ID:
				tile.setEnergyStored(data);
				break;
			case COOK_ID:
				tile.setCookTime(data);
			default:
				break;
		}
	}
	
	

}

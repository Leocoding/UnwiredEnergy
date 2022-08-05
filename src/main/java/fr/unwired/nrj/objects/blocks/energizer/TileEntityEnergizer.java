package fr.unwired.nrj.objects.blocks.energizer;

import fr.unwired.nrj.sounds.SoundSource;
import fr.unwired.nrj.sounds.SoundTE;
import fr.unwired.nrj.sounds.UnwiredSound;
import fr.unwired.nrj.util.Config;
import fr.unwired.nrj.util.References;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityEnergizer extends TileEntityLockableLoot implements ITickable, ISidedInventory, SoundSource {
	
	int[] slots = {ContainerEnergizer.SLOT1_ID, ContainerEnergizer.SLOT2_ID};
	
	private final static int MAX_ENERGY = 100000;
	private final static int STACK_LIMIT = 64;
	private final static int ENERGY_STEP = Config.energizerConso;
	private final static String NAME = "gui_energizer";
	
	
	protected EnergyStorageMachine energyStorage;
	protected int tick, cookTime;
	
	private boolean doitPlaySound;
	
	@SideOnly(Side.CLIENT)
	private SoundTE sound;
	
	private NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

	public TileEntityEnergizer() {
		energyStorage = new EnergyStorageMachine(MAX_ENERGY);
		tick = 0;
		cookTime = Integer.MAX_VALUE;
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("energyStored", getEnergyStored());
		if (!checkLootAndWrite(compound)) ItemStackHelper.saveAllItems(compound, items);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		if (!checkLootAndRead(compound)) ItemStackHelper.loadAllItems(compound, items);
		energyStorage.setEnergyStored(compound.getInteger("energyStored"));
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {	
			return true;
		}
		if(capability == CapabilityEnergy.ENERGY) {	
			return true;
		}
		return super.hasCapability(capability, facing);
	}

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new SidedInvWrapper(this, EnumFacing.UP));
		if(capability == CapabilityEnergy.ENERGY) { 
			return CapabilityEnergy.ENERGY.cast(energyStorage);
		}
        return super.getCapability(capability, facing);
    }
	
	public int getEnergyStored() {
		return energyStorage.getEnergyStored();
	}
	
	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}
	
	public float getEnergyStoredPercentage() {
		return energyStorage.getEnergyStoredPercentage();
	}

	public void setEnergyStored(int es) {
		energyStorage.setEnergyStored(es);
	}
	
	public void setCookTime(int time) {
		cookTime = time;
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	public int getProgress() {
		return tick;
	}
	
	public float getProgressPercentage() {
		return (float) tick / (float) cookTime;
	}
	
	public void setProgress(int p) {
		tick = p;
	}
	
	public void setItem(ItemStack is, int idx) {
		items.set(idx, is);
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			if(isWorking()) {
				if(!doitPlaySound) {
					startSound();
				}
				EnergizerBlock.setState(true, world, pos);
				if(tick == 0) {
					cookTime = RecipesEnergizer.getNbTicks(items.get(0));
				}
				if(tick % cookTime == 0 && tick != 0) {
					smeltItem();
					tick = 0;
				}
				energyStorage.extractEnergy(ENERGY_STEP, false);
				++tick;
			} else {
				tick = 0;
				EnergizerBlock.setState(false, world, pos);
				if(doitPlaySound) {
					stopSound();
				}

			}
			if (tick == Integer.MAX_VALUE) {
				tick = 0;
			}
			markDirty();
		} else {
			if(hasSound()) {
				updateSound();
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : items) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return STACK_LIMIT;
	}

	@Override
	public String getName() {
		return "container." + NAME;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerEnergizer(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return References.MODID + ":gui_energizer";
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}
	
	// OUTILS
	
    public void smeltItem() {
        ItemStack itemstack = items.get(ContainerEnergizer.SLOT1_ID);
        ItemStack itemstack1 = RecipesEnergizer.getRecipeResult(itemstack);
        ItemStack itemstack2 = items.get(ContainerEnergizer.SLOT2_ID);

        if (itemstack2.isEmpty())
        {
            items.set(ContainerEnergizer.SLOT2_ID, itemstack1.copy());
        }
        else if (itemstack2.getItem() == itemstack1.getItem())
        {
            itemstack2.grow(itemstack1.getCount());
        }
        itemstack.shrink(1);
    }
	
	public boolean isWorking() {
		ItemStack stack = items.get(ContainerEnergizer.SLOT2_ID);
		return energyStorage.getEnergyStored() >= ENERGY_STEP && !getStackInSlot(0).isEmpty() && stack.getCount() < stack.getMaxStackSize();
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn,
			EnumFacing direction) {
		return (index == ContainerEnergizer.SLOT1_ID && RecipesEnergizer.hasItemRecipe(itemStackIn));
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		return (index == ContainerEnergizer.SLOT2_ID);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return slots;
	}
	
	/* SOUND */
	@SideOnly (Side.CLIENT)
	private SoundTE getSound() {

		return new SoundTE(getSoundEvent(), getVolume(), 1.0F, true, 0, pos);
	}
	
	@Override
	public float getVolume() {

		return 1.05F;
	}

	private SoundEvent getSoundEvent() {
		return UnwiredSound.ENERGIZER_WORKING;
	}

	@Override
	public boolean doitPlaySound() {
		return doitPlaySound && !isInvalid();
	}
	
	@Override
	public boolean hasSound() {
		return getSound() != null;
	}
	
	@SideOnly(Side.CLIENT)
	private void updateSound() {
		if(hasSound()) {
			final SoundEvent soundEvent = getSoundEvent();
			if (doitPlaySound() && soundEvent != null) {
				if(sound == null) {
					FMLClientHandler.instance().getClient().getSoundHandler().playSound(sound = new SoundTE(getSoundEvent(), getVolume(), 1.0F, true, 0, pos));
				}
			} else if(sound != null) {
				sound = null;
			}
		}
	}
		
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("doitPlaySound", doitPlaySound);
		return new SPacketUpdateTileEntity(pos, 1, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		doitPlaySound = pkt.getNbtCompound().getBoolean("doitPlaySound");
		markDirty();
		super.onDataPacket(net, pkt);
	}

	@Override
	public void stopSound() {
		doitPlaySound = false;
		notifySoundChange();
	}
	
	@Override
	public void startSound() {
		doitPlaySound = true;
		notifySoundChange();
	}
	
	private void notifySoundChange() {
		IBlockState bs = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, bs, bs, 4);
	}


	

}

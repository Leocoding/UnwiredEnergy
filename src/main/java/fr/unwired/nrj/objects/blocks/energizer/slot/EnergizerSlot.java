package fr.unwired.nrj.objects.blocks.energizer.slot;

import fr.unwired.nrj.objects.blocks.energizer.RecipesEnergizer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class EnergizerSlot extends Slot {

	public EnergizerSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return RecipesEnergizer.hasItemRecipe(stack);
	}

}

package fr.unwired.nrj.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesInit {

	public static void init() {
		GameRegistry.addSmelting(new ItemStack(BlocksInit.energystone_ore_block), new ItemStack(ItemsInit.energystone_ingot) , 1.5F);
	}
}

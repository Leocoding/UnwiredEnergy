/**
 * 
 */
package fr.unwired.nrj.objects.blocks.energizer;

import java.util.HashMap;
import java.util.Map;

import fr.unwired.nrj.init.ItemsInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Adrien
 *
 */
public class RecipesEnergizer {
	public static final Map<Item, Map<String, Object>> recettes = new HashMap<>();
	static {
		addRecipe(ItemsInit.energystone_ingot, ItemsInit.energized_energystone_ingot, 1000, 200);
	}
	
	@SuppressWarnings("unused")
	private static void addRecipe(Block block, Item resultat, int energyConsumed, int nbTicks) {
		addRecipe(Item.getItemFromBlock(block), resultat, energyConsumed, nbTicks);
	}
	
	private static void addRecipe(Item ingredient, Item resultat, int energyConsumed, int nbTicks) {
		Map<String, Object> res = new HashMap<>();
		res.put("energy", energyConsumed);
		res.put("temps", nbTicks);
		res.put("resultat", new ItemStack(resultat));
	    recettes.put(ingredient, res);
	}
	
	public static ItemStack getRecipeResult(ItemStack ingredient) {
		Item item = ingredient.getItem();
		if(recettes.containsKey(item)) {
			return (ItemStack) recettes.get(item).get("resultat");
		} else {
			return null;
		}
	}
	
	public static int getEnergyConsumed(ItemStack ingredient) {
		Item item = ingredient.getItem();
		if(recettes.containsKey(item)) {
			return (int) recettes.get(item).get("energy");
		} else {
			return 0;
		}
	}
	
	public static int getNbTicks(ItemStack ingredient) {
		Item item = ingredient.getItem();
		if(recettes.containsKey(item)) {
			return (int) recettes.get(item).get("temps");
		} else {
			return Integer.MAX_VALUE;
		}
	}
	
	public static boolean hasItemRecipe(ItemStack i) {
		return recettes.containsKey(i.getItem());
	}
	 
}

/**
 * 
 */
package fr.unwired.nrj.plugins.jei;

import java.util.List;

import com.google.common.collect.Lists;

import fr.unwired.nrj.objects.blocks.energizer.RecipesEnergizer;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Adrien
 *
 */
public class EnergizerRecipeMaker {
	public static List<EnergizerRecipe> getRecipes(IJeiHelpers helpers){
		
		List<EnergizerRecipe> jeiRecipes = Lists.newArrayList();
		
		for (Item input : RecipesEnergizer.recettes.keySet()) {
			ItemStack stack = new ItemStack(input);
			EnergizerRecipe recipe = new EnergizerRecipe(stack, RecipesEnergizer.getRecipeResult(stack));
			jeiRecipes.add(recipe);
		}
		
		return jeiRecipes;
	}
}

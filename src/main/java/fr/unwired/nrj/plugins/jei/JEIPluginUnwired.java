/**
 * 
 */
package fr.unwired.nrj.plugins.jei;

import fr.unwired.nrj.gui.GuiEnergizer;
import fr.unwired.nrj.objects.blocks.energizer.ContainerEnergizer;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;

/**
 * @author Adrien
 *
 */

@JEIPlugin
public class JEIPluginUnwired implements IModPlugin {
	
	public static IJeiRuntime jeiRuntime;

	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		final IJeiHelpers helpers = registry.getJeiHelpers();
		final IGuiHelper gui = helpers.getGuiHelper();
		
		registry.addRecipeCategories(new EnergizerRecipeCategory(gui));

		IModPlugin.super.registerCategories(registry);
	}
	
	
	
	@Override
	public void register(IModRegistry registry) {
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();
		
		registry.addRecipes(EnergizerRecipeMaker.getRecipes(jeiHelpers), RecipeCategories.ENERGIZER);
		registry.addRecipeClickArea(GuiEnergizer.class, 141, 68, 22, 14, RecipeCategories.ENERGIZER);
		
		recipeTransfer.addRecipeTransferHandler(ContainerEnergizer.class, RecipeCategories.ENERGIZER, 0, 1, 2, 36);

	}
	
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

		JEIPluginUnwired.jeiRuntime = jeiRuntime;
	}

}

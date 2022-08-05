/**
 * 
 */
package fr.unwired.nrj.plugins.jei;

import fr.unwired.nrj.util.References;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

/**
 * @author Adrien
 *
 */
public class EnergizerRecipeCategory extends AbstractEnergizerRecipeCategory<IRecipeWrapper>{

	private final IDrawable background;
	private final String name;
	
	/**
	 * @param helper
	 */
	public EnergizerRecipeCategory(IGuiHelper helper) {
		super(helper);
		background = helper.createDrawable(TEXTURES, 20, 0, 150, 82);
		name = "Energizer";
	}
	
	
	@Override
	public IDrawable getBackground() {
		return this.background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedProgress.draw(minecraft, 36, 36);
		staticEnergyBackGround.draw(minecraft, 124, 16);
		animatedEnergy.draw(minecraft, 124, 16);
	}
	
	@Override
	public String getTitle() {
		return name;
	}
	
	@Override
	public String getModName() {
		return References.NAME;
	}
	
	@Override
	public String getUid() {
		return RecipeCategories.ENERGIZER;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout,
			IRecipeWrapper recipeWrapper, IIngredients ingredients) {

		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input, true, 11, 31);
		stacks.init(output, false, 75, 31);
		stacks.set(ingredients);
	}
	
	@Override
	public IDrawable getIcon() {
		return null;
	}
	

}

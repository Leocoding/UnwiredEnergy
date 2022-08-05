/**
 * 
 */
package fr.unwired.nrj.plugins.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.unwired.nrj.objects.blocks.energizer.RecipesEnergizer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

/**
 * @author Adrien
 *
 */
public class EnergizerRecipe implements IRecipeWrapper {

	private final ItemStack input;
	private final ItemStack output;
	
	/**
	 * 
	 */
	public EnergizerRecipe(ItemStack input, ItemStack output) {

		this.input = input;
		this.output = output;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY) {
		String rf = RecipesEnergizer.getEnergyConsumed(input) + " rf/item";
		FontRenderer render = minecraft.fontRenderer;
		int strWidthRf = render.getStringWidth(rf);
		render.drawString(rf, recipeWidth/2 - strWidthRf/2, 0, Color.gray.getRGB());
		
		String ticks = RecipesEnergizer.getNbTicks(input) + " ticks/item";
		int strWidthTicks = render.getStringWidth(ticks);
		render.drawString(ticks, recipeWidth/2 - strWidthTicks/2, 8, Color.gray.getRGB());
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltip = new ArrayList<>();

		if (mouseX > 124 && mouseX < 124+16 && mouseY > 16 && mouseY < 16+48) {
			tooltip.add("Energy : " + RecipesEnergizer.getEnergyConsumed(input) + " RF");
		} else if (mouseX > 36 && mouseX < 36+32 && mouseY > 36 && mouseY < 36+8) {
			tooltip.add("Ticks : " + RecipesEnergizer.getNbTicks(input) + " ticks");
		}
		return tooltip;
	}
	

}

/**
 * 
 */
package fr.unwired.nrj.plugins.jei;

import fr.unwired.nrj.util.References;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

/**
 * @author Adrien
 *
 */
public abstract class AbstractEnergizerRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	public static final ResourceLocation TEXTURES = new ResourceLocation(References.MODID + ":textures/gui/jei/gui_energizer_jei.png");
	
	protected static final int input = 0;
	protected static final int output = 1;
	
	protected final IDrawableAnimated animatedProgress;
	protected final IDrawableAnimated animatedEnergy;
	protected final IDrawableStatic staticEnergyBackGround;

	
	/**
	 * 
	 */
	public AbstractEnergizerRecipeCategory(IGuiHelper helper) {
		
		IDrawableStatic staticProgress = helper.createDrawable(TEXTURES, 176, 96, 32, 8);
		animatedProgress = helper.createAnimatedDrawable(staticProgress, 100, IDrawableAnimated.StartDirection.LEFT, false);
		
		staticEnergyBackGround = helper.createDrawable(TEXTURES, 176, 0, 16, 48);
		
		IDrawableStatic staticEnergy = helper.createDrawable(TEXTURES, 176, 48, 16, 48);
		animatedEnergy = helper.createAnimatedDrawable(staticEnergy, 100, IDrawableAnimated.StartDirection.TOP, true);

	}
	
}

/**
 * 
 */
package fr.unwired.nrj.util;

import fr.unwired.nrj.init.ItemsInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * @author Adrien
 *
 */
public class UnwiredCreativeTab extends CreativeTabs {

	/**
	 * @param label
	 */
	public UnwiredCreativeTab(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemsInit.energystone_ingot);
	}

}

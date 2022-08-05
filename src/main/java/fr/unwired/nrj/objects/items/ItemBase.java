package fr.unwired.nrj.objects.items;


import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.init.ItemsInit;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(UnwiredMod.UNWIRED_TAB);
		ItemsInit.ITEMS.add(this);
	}

}
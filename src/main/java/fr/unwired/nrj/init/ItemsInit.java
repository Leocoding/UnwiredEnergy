package fr.unwired.nrj.init;



import java.util.ArrayList;
import java.util.List;

import fr.unwired.nrj.objects.items.ItemBase;
import fr.unwired.nrj.objects.items.Linker;
import fr.unwired.nrj.util.References;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=References.MODID)
public class ItemsInit
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item energystone = new ItemBase("energystone");
	public static final Item energystone_ingot = new ItemBase("energystone_ingot");
	public static final Item energized_energystone_ingot = new ItemBase("energized_energystone_ingot");
	public static final Item linker = new Linker("linker");
}
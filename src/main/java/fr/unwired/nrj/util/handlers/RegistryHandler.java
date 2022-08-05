package fr.unwired.nrj.util.handlers;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.init.BlocksInit;
import fr.unwired.nrj.init.ItemsInit;
import fr.unwired.nrj.sounds.UnwiredSound;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemsInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlocksInit.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		
		for(Item i : ItemsInit.ITEMS) {
			registerItemRendered(i, 0, "inventory");
		}
		
		for(Block b : BlocksInit.BLOCKS) {
			registerItemRendered(Item.getItemFromBlock(b), 0, "inventory");
		}
	}
	
	public static void onSoundRegister() {
		UnwiredSound.registerSounds();
	}
	
	public static void onGuiRegister() {
		System.out.println("OnGuiRegister");
		NetworkRegistry.INSTANCE.registerGuiHandler(UnwiredMod.instance, new GuiHandler());
	}
	
	private static void registerItemRendered(Item item, int meta, String id) {	
		ModelLoader.setCustomModelResourceLocation(item, meta, 
				new ModelResourceLocation(item.getRegistryName(), id));
	}

}

package fr.unwired.nrj;


import org.apache.logging.log4j.Logger;

//import fr.unwired.nrj.common.TileEntityEnergizer;
import fr.unwired.nrj.init.RecipesInit;
import fr.unwired.nrj.proxy.CommonProxy;
import fr.unwired.nrj.util.Config;
import fr.unwired.nrj.util.References;
import fr.unwired.nrj.util.UnwiredCreativeTab;
import fr.unwired.nrj.util.handlers.PacketHandler;
import fr.unwired.nrj.util.handlers.RegistryHandler;
import fr.unwired.nrj.world.WorldGeneration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION, acceptedMinecraftVersions = References.MINECRAFT_VERSION, guiFactory = References.GUI_FACTORY)
public class UnwiredMod {
	
	@Instance(References.MODID)
	public static UnwiredMod instance;
	
	public static Logger logger;
	
	public final static UnwiredCreativeTab UNWIRED_TAB = new UnwiredCreativeTab(References.MODID + ".unwired_creative_tab");
	
	@SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY , modId = References.MODID)
	public static CommonProxy proxy;
	WorldGeneration worldgeneration = new WorldGeneration(); 
	
	public static SimpleNetworkWrapper network;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.preInit();
		GameRegistry.registerWorldGenerator(worldgeneration, 0); 
		logger = event.getModLog();	
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		RecipesInit.init();
		RegistryHandler.onGuiRegister();
		RegistryHandler.onSoundRegister();
		PacketHandler.init();
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {}
	
}

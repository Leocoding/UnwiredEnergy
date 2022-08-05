package fr.unwired.nrj.util;


import java.io.File;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The main class for your configuration.
 * Get all of your customized values here

 *
 */
public class Config {
	
	/**
	 * The object which holds the actual config file
	 */
	private static Configuration config = null;
	
	/**
	 * The name of the category for our network
	 */
	public static final String CATEGORY_NAME_NETWORK = "network";
	public static final String CATEGORY_NAME_ENERGIZERCONSO = "energizer";
	
	/**
	 * The values which are loaded from the config file
	 */
	public static int networkDistance;
	public static int energizerConso;
	
	/**
	 * Called on the server and the client setting up the config file
	 */
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "unwired-energy.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	/**
	 * Receive the config object for use in the gui factory
	 * @return the config element
	 */
	public static Configuration getConfig() {
		return config;
	}
	
	/**
	 * Register our event which handles the gui factory saving the config
	 */
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	/**
	 * Sync the config from a change in the file
	 */
	public static void syncFromFiles() {
		syncConfig(true, true);
	}
	
	/**
	 * Sync the config from a change in the gui
	 */
	public static void syncFromGui() {
		syncConfig(false, true);
	}
	
	/**
	 * Sync the config from a change in the fields (i.e changing the machineCooldownBasic property using BoeConfig.machineCooldownBasic = ...)
	 */
	public static void syncFromFields() {
		syncConfig(false, false);
	}
	
	/**
	 * Used internally to sync all of our properties and fields
	 * @param loadFromConfigFile Should load the actual config file
	 * @param readFieldsFromConfig Should read the values from the config
	 */
	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if(loadFromConfigFile)
			config.load();
		
		Property propertyNetworkDistance = config.get(CATEGORY_NAME_NETWORK, "network_distance", 10);
		propertyNetworkDistance.setLanguageKey("gui.config.blocks.network_distance.name");
		propertyNetworkDistance.setComment("The distance between two blocks of the network");
		propertyNetworkDistance.setMinValue(1);
		propertyNetworkDistance.setMaxValue(50);
		
		Property propertyEnergizerConso = config.get(CATEGORY_NAME_ENERGIZERCONSO, "energizer_conso", 5);
		propertyEnergizerConso.setLanguageKey("gui.config.blocks.energizer_conso.name");
		propertyEnergizerConso.setComment("Consumption of the energizer per tick");
		propertyEnergizerConso.setMinValue(1);
		propertyEnergizerConso.setMaxValue(Integer.MAX_VALUE);

		
		if(readFieldsFromConfig) {
			networkDistance = propertyNetworkDistance.getInt();
			energizerConso = propertyEnergizerConso.getInt();
			
			
		}
		
		propertyNetworkDistance.set(networkDistance);
		propertyEnergizerConso.set(energizerConso);
	
		
		if(config.hasChanged())
			config.save();
	}
	
	/**
	 * Handles the updating of the config from the gui factory
	 * 
	 *
	 */
	public static class ConfigEventHandler {
		
		/**
		 * Syncs the update from the gui factory
		 */
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(References.MODID)) {
				syncFromGui();
			}
		}
		
	}
}





package fr.unwired.nrj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;


public class ConfigGui implements IModGuiFactory {

	/**
	 * Used to initialize values from the user's minecraft instance
	 * We don't use this
	 */
	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	/**
	 * The actual class which is the gui
	 */
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ConfigGuiFactory.class;
	}

	/**
	 * Ggets the runtime gui categories which change in game
	 */
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	/**
	 * The gui for the config
	 * @author  
	 *
	 */
	public static class ConfigGuiFactory extends GuiConfig {
		
		/**
		 * Settting up the screen
		 * @param parentScreen The prior screen
		 */
		public ConfigGuiFactory(GuiScreen parentScreen) {
			super(parentScreen, getConfigElements(), References.MODID, false, false, I18n.format("gui.config.main_title"));
		}

		/**
		 * Get all of the different categories
		 * @return a list of the different categories
		 */
		private static List<IConfigElement> getConfigElements() {
			List<IConfigElement> list = new ArrayList<IConfigElement>();
			list.add(new DummyCategoryElement(I18n.format("gui.config.category.network"), "gui.config.category.network", CategoryEntryNetwork.class)); //Add another one of these for any other categories
			list.add(new DummyCategoryElement(I18n.format("gui.config.category.energizer"), "gui.config.category.energizer", CategoryEntryEnergizer.class)); //Add another one of these for any other categories
			return list;
		}
		
		/**
		 * The category for the blocks
		 * @author  
		 *
		 */
		public static class CategoryEntryNetwork extends CategoryEntry {

			/**
			 * Default constructor
			 */
			public CategoryEntryNetwork(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement) {
				super(owningScreen, owningEntryList, configElement);
			}
			
			/**
			 * Puts all of the properties on the screen from the category
			 */
			@Override
			protected GuiScreen buildChildScreen() {
				Configuration config = Config.getConfig();
				ConfigElement categoryNetwork = new ConfigElement(config.getCategory(Config.CATEGORY_NAME_NETWORK));
				List<IConfigElement> propertiesOnScreen = categoryNetwork.getChildElements();
				String windowTitle = I18n.format("gui.config.category.network");
				return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
			}
			
		}
		
		/**
		 * The category for the blocks
		 * @author  
		 *
		 */
		public static class CategoryEntryEnergizer extends CategoryEntry {

			/**
			 * Default constructor
			 */
			public CategoryEntryEnergizer(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement) {
				super(owningScreen, owningEntryList, configElement);
			}
			
			/**
			 * Puts all of the properties on the screen from the category
			 */
			@Override
			protected GuiScreen buildChildScreen() {
				Configuration config = Config.getConfig();
				ConfigElement categoryEnergizer = new ConfigElement(config.getCategory(Config.CATEGORY_NAME_ENERGIZERCONSO));
				List<IConfigElement> propertiesOnScreen = categoryEnergizer.getChildElements();
				String windowTitle = I18n.format("gui.config.category.energizer");
				return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
			}
			
		}
		
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new ConfigGuiFactory(parentScreen);
	}

}

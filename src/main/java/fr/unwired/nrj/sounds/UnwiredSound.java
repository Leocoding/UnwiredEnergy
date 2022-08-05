/**
 * 
 */
package fr.unwired.nrj.sounds;

import fr.unwired.nrj.util.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @author Adrien
 *
 */
public class UnwiredSound {
	
	public static SoundEvent ENERGIZER_WORKING;
	
	public static void registerSounds() {
		ENERGIZER_WORKING = registerSound("block.energizer.working");
	}
	
	private static SoundEvent registerSound(String name) {
		ResourceLocation loc = new ResourceLocation(References.MODID, name);
		SoundEvent event = new SoundEvent(loc);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
	
}

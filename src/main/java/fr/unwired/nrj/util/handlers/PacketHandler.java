/**
 * 
 */
package fr.unwired.nrj.util.handlers;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.packets.Message;
import fr.unwired.nrj.packets.PacketGuiShortFix;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Adrien
 *
 */
public class PacketHandler {
	
	/**
	 * 
	 */
	public static void init() {
		UnwiredMod.network = NetworkRegistry.INSTANCE.newSimpleChannel("unwired-packet");
		UnwiredMod.network.registerMessage(Message.Handler.class, Message.class, 0, Side.SERVER);
		UnwiredMod.network.registerMessage(PacketGuiShortFix.class, PacketGuiShortFix.class, 1, Side.CLIENT);
	}

}

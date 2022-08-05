/**
 * 
 */
package fr.unwired.nrj.packets;

import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyExchanger;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author Adrien
 *
 */
public class Message implements IMessage {
	
	private String text;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message(String text) {
		this.text = text;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, text);
	}
	
    public static class Handler implements IMessageHandler <Message, IMessage> {
    	 
        @Override
        public IMessage onMessage(Message message, MessageContext ctx) {
        	String[] command = parseMsg(message.text);
        	if(command.length > 0) {
        		if(command[0].equals("connection")) {
	        		int x = Integer.valueOf(command[1]);
	        		int y = Integer.valueOf(command[2]);
	        		int z = Integer.valueOf(command[3]);
	                TileEntity tile = ctx.getServerHandler().player.world.getTileEntity(new BlockPos(x,y,z));
	                if(tile instanceof TileEntityEnergyExchanger) {
	                	TileEntityEnergyExchanger tileEEE = (TileEntityEnergyExchanger)tile;
	                	tileEEE.setConnected(!tileEEE.isConnected());
	                }
        		}
        	}
            return null; // no response in this case
        }
        
        private String[] parseMsg(String msg) {
        	return msg.split(";");
        }
    }

}

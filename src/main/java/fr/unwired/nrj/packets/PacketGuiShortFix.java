package fr.unwired.nrj.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiShortFix implements IMessage, IMessageHandler<PacketGuiShortFix, IMessage> {

  public int id, data;

  public PacketGuiShortFix() {}

  public PacketGuiShortFix(int id, int data) {
    this.id = id;
    this.data = data;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    id = buf.readInt();
    data = buf.readInt();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(id);
    buf.writeInt(data);
  }

  @Override
  public IMessage onMessage(PacketGuiShortFix message, MessageContext ctx) {
    final IThreadListener mainThread = Minecraft.getMinecraft();
    mainThread.addScheduledTask(new Runnable() {

      @Override
      public void run() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player.openContainer != null) {
          player.openContainer.updateProgressBar(message.id, message.data);
        }
      }
    });
    return null;
  }
}
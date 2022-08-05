package fr.unwired.nrj.gui;

import java.io.IOException;

import fr.unwired.nrj.UnwiredMod;
import fr.unwired.nrj.objects.blocks.energyexchanger.ContainerEnergyExchanger;
import fr.unwired.nrj.objects.blocks.energyexchanger.TileEntityEnergyExchanger;
import fr.unwired.nrj.packets.Message;
import fr.unwired.nrj.util.IntParser;
import fr.unwired.nrj.util.References;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiEnergyExchanger extends GuiContainer {
	
	private final static int WINDOW_X_SIZE = 176;
	private final static int WINDOW_Y_SIZE = 101;
	private final static int JAUGE_X_OFFSET = 80;
	private final static int JAUGE_Y_OFFSET = 32;
	private final static int JAUGE_X_SIZE = 16;
	private final static int JAUGE_Y_SIZE = 32;
	private final static int JAUGE_BACKGROUND_TEXTURE_X_OFFSET = 176;
	private final static int JAUGE_BACKGROUND_TEXTURE_Y_OFFSET = 0;
	private final static int JAUGE_TEXTURE_X_OFFSET = 176;
	private final static int JAUGE_TEXTURE_Y_OFFSET = 32;
	
	
	public static final ResourceLocation TEXTURES = new ResourceLocation(References.MODID + ":textures/gui/gui_energy_exchanger.png");
	private final TileEntityEnergyExchanger tile;
	
	private GuiButton button;

	public GuiEnergyExchanger(InventoryPlayer inv, TileEntityEnergyExchanger tileEntity) {
		super(new ContainerEnergyExchanger(tileEntity));
		tile = tileEntity; 

		xSize = WINDOW_X_SIZE; ySize = WINDOW_Y_SIZE;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		button = new GuiButton(0, this.width / 2 - 75, this.height / 2 + 5, 50, 20, "");
		this.buttonList.add(button);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		switch (button.id) {
		case 0:
		    UnwiredMod.network.sendToServer(new Message("connection"+";"
		    		+ String.valueOf(tile.getPos().getX()+";"
		    		+ String.valueOf(tile.getPos().getY()+";"
		    		+ String.valueOf(tile.getPos().getZ())))
		    ));
		    
			break;

		default:
			break;
		}
	}

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEXTURES);
		drawTexturedModalRect((width / 2) - (xSize / 2), (height / 2) - (ySize/ 2), 0, 0, xSize, ySize);
		drawTexturedModalRect((width / 2) - (xSize / 2) + JAUGE_X_OFFSET, (height / 2) - (ySize/ 2) + JAUGE_Y_OFFSET, JAUGE_BACKGROUND_TEXTURE_X_OFFSET, JAUGE_BACKGROUND_TEXTURE_Y_OFFSET, JAUGE_X_SIZE,JAUGE_Y_SIZE);
	}
		
	private int getJaugeYExtraOffset() {
		return ((int)Math.ceil(JAUGE_Y_SIZE * (1. - tile.getEnergyStoredPercentage())));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEXTURES);
		drawTexturedModalRect(JAUGE_X_OFFSET, JAUGE_Y_OFFSET + getJaugeYExtraOffset(), JAUGE_TEXTURE_X_OFFSET, JAUGE_TEXTURE_Y_OFFSET + getJaugeYExtraOffset(), JAUGE_X_SIZE, JAUGE_Y_SIZE - getJaugeYExtraOffset());
		
		if (mouseX > (width / 2) - (xSize / 2) + JAUGE_X_OFFSET && mouseX <= (width / 2) - (xSize / 2) + JAUGE_X_OFFSET + JAUGE_X_SIZE && mouseY >= (height / 2) - (ySize/ 2) + JAUGE_Y_OFFSET && mouseY <= (height / 2) - (ySize/ 2) + JAUGE_Y_OFFSET + JAUGE_Y_SIZE) {
			String parsedString = IntParser.getParsedValue(tile.getEnergyStored()) + "/" + IntParser.getParsedValue(tile.getMaxEnergyStored());
			String shortenedparsedString = IntParser.getShortenedValue(tile.getEnergyStored(),0) + "/" + IntParser.getShortenedValue(tile.getMaxEnergyStored(),0);
			drawHoveringText(isShiftKeyDown()?parsedString:shortenedparsedString, mouseX - ((width / 2) - (xSize / 2)), mouseY - ((height / 2) - (ySize/ 2)));
		}
		button.displayString = tile.isConnected() ? "stop" : "start";
	}
	
}
//
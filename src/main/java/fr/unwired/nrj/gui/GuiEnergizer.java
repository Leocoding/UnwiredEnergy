package fr.unwired.nrj.gui;

import fr.unwired.nrj.objects.blocks.energizer.ContainerEnergizer;
import fr.unwired.nrj.objects.blocks.energizer.TileEntityEnergizer;
import fr.unwired.nrj.util.IntParser;
import fr.unwired.nrj.util.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiEnergizer extends GuiContainer {
	
	private final static int WINDOW_X_SIZE = 176;
	private final static int WINDOW_Y_SIZE = 166;
	private final static int JAUGE_X_OFFSET = 144;
	private final static int JAUGE_Y_OFFSET = 16;
	private final static int JAUGE_X_SIZE = 16;
	private final static int JAUGE_Y_SIZE = 48;
	private final static int JAUGE_BACKGROUND_TEXTURE_X_OFFSET = 176;
	private final static int JAUGE_BACKGROUND_TEXTURE_Y_OFFSET = 0;
	private final static int JAUGE_TEXTURE_X_OFFSET = 176;
	private final static int JAUGE_TEXTURE_Y_OFFSET = 48;
	private final static int PROGRESS_BAR_X_OFFSET = 56;
	private final static int PROGRESS_BAR_Y_OFFSET = 36;
	private final static int PROGRESS_BAR_TEXTURE_X_OFFSET = 176;
	private final static int PROGRESS_BAR_TEXTURE_Y_OFFSET = 96;
	private final static int PROGRESS_BAR_X_SIZE = 32;
	private final static int PROGRESS_BAR_Y_SIZE = 8;
	
	public static final ResourceLocation TEXTURES = new ResourceLocation(References.MODID + ":textures/gui/gui_energizer.png");
	private final TileEntityEnergizer tile;

	public GuiEnergizer(InventoryPlayer inv, TileEntityEnergizer tileEntity) {
		super(new ContainerEnergizer(inv, tileEntity));
		tile = tileEntity; 

		xSize = WINDOW_X_SIZE; ySize = WINDOW_Y_SIZE;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
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
	
	private int getProgressBarXSize() {
		return ((int)Math.ceil(PROGRESS_BAR_X_SIZE * tile.getProgressPercentage()));
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEXTURES);
		drawTexturedModalRect(JAUGE_X_OFFSET, JAUGE_Y_OFFSET + getJaugeYExtraOffset(), JAUGE_TEXTURE_X_OFFSET, JAUGE_TEXTURE_Y_OFFSET + getJaugeYExtraOffset(), JAUGE_X_SIZE, JAUGE_Y_SIZE - getJaugeYExtraOffset());
		drawTexturedModalRect(PROGRESS_BAR_X_OFFSET, PROGRESS_BAR_Y_OFFSET, PROGRESS_BAR_TEXTURE_X_OFFSET, PROGRESS_BAR_TEXTURE_Y_OFFSET, getProgressBarXSize(), PROGRESS_BAR_Y_SIZE);
		if (mouseX > (width / 2) - (xSize / 2) + JAUGE_X_OFFSET && mouseX <= (width / 2) - (xSize / 2) + JAUGE_X_OFFSET + JAUGE_X_SIZE && mouseY >= (height / 2) - (ySize/ 2) + JAUGE_Y_OFFSET && mouseY <= (height / 2) - (ySize/ 2) + JAUGE_Y_OFFSET + JAUGE_Y_SIZE) {
			String parsedString = IntParser.getParsedValue(tile.getEnergyStored()) + "/" + IntParser.getParsedValue(tile.getMaxEnergyStored());
			String shortenedparsedString = IntParser.getShortenedValue(tile.getEnergyStored(),0) + "/" + IntParser.getShortenedValue(tile.getMaxEnergyStored(),0);
			drawHoveringText(isShiftKeyDown()?parsedString:shortenedparsedString, mouseX - ((width / 2) - (xSize / 2)), mouseY - ((height / 2) - (ySize/ 2)));
		}
		if (tile.isWorking()) {
			if (mouseX > (width / 2) - (xSize / 2) + PROGRESS_BAR_X_OFFSET && mouseX <= (width / 2) - (xSize / 2) + PROGRESS_BAR_X_OFFSET + PROGRESS_BAR_X_SIZE && mouseY >= (height / 2) - (ySize/ 2) + PROGRESS_BAR_Y_OFFSET && mouseY <= (height / 2) - (ySize/ 2) + PROGRESS_BAR_Y_OFFSET + PROGRESS_BAR_Y_SIZE) {
				drawHoveringText( Integer.toString((int)Math.round(tile.getProgressPercentage()*100)) + "%", mouseX - ((width / 2) - (xSize / 2)), mouseY - ((height / 2) - (ySize/ 2)));
			}
		}
	}
	
	
}

/**
 * 
 */
package fr.unwired.nrj.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

/**
 * @author Adrien
 *
 */
public class SoundTE extends PositionedSound implements ITickableSound {
	
	private float baseVolume;
	private boolean donePlaying;
	private boolean beginFadeOut;
	private int ticks = 0;
	private int fadeIn = 50;
	private int fadeOut = 50;
	
	public SoundTE(SoundEvent sound, float volume, float pitch, boolean repeat, int repeatDelay, BlockPos pos) {
		this(sound, volume, pitch, repeat, repeatDelay, pos, AttenuationType.LINEAR);
	}

	public SoundTE(SoundEvent sound, float volume, float pitch, boolean repeat, int repeatDelay, BlockPos pos, AttenuationType attenuation) {

		super(sound, SoundCategory.AMBIENT);
		this.xPosF = (float) pos.getX() + 0.5f;
		this.yPosF = (float) pos.getY() + 0.5f;
		this.zPosF = (float) pos.getZ() + 0.5f;
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
		this.repeatDelay = repeatDelay;
		this.attenuationType = attenuation;
		this.baseVolume = volume;
	}

	@Override
	public void update() {
		if (!beginFadeOut) {
			if (ticks < fadeIn) {
				ticks++;
			}
			
			BlockPos pos = new BlockPos(xPosF, yPosF, zPosF);
			SoundSource te = (SoundSource) Minecraft.getMinecraft().player.world.getTileEntity(pos);
			if (te == null || !te.doitPlaySound()) {
				beginFadeOut = true;
				ticks  = 0;
			}
		} else {
			ticks++;
		}
		float multiplier = beginFadeOut ? getFadeOutMultiplier() : getFadeInMultiplier();
		volume = baseVolume * multiplier;
		
		if (multiplier <= 0) {
			this.repeat = false;
			donePlaying = true;
		}		
	}

	@Override
	public boolean isDonePlaying() {
		return donePlaying;
	}
	
	public SoundTE setFadeIn(int fadeIn) {

		this.fadeIn = Math.min(0, fadeIn);
		return this;
	}

	public SoundTE setFadeOut(int fadeOut) {

		this.fadeOut = Math.min(0, fadeOut);
		return this;
	}

	public float getFadeInMultiplier() {

		return ticks >= fadeIn ? 1 : ticks / (float) fadeIn;
	}

	public float getFadeOutMultiplier() {

		return ticks >= fadeOut ? 0 : (fadeOut - ticks) / (float) fadeOut;
	}

}

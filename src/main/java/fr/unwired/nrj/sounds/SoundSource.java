/**
 * 
 */
package fr.unwired.nrj.sounds;

/**
 * @author Adrien
 *
 */
public interface SoundSource {
	
	boolean doitPlaySound();
	void startSound();
	void stopSound();
	boolean hasSound();
	float getVolume();

}

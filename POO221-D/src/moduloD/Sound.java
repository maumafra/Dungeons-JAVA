package moduloD;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	GamePanel gp;
	
	public Sound(GamePanel gp) {
		soundURL[0] = getClass().getResource("/sounds/Aria - 8bits.wav");
		//https://www.youtube.com/watch?v=D_7f8TY5j2g
		soundURL[1] = getClass().getResource("/sounds/BehelitSong.wav");
		this.gp = gp;
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch (IllegalArgumentException e){
			gp.sysHasAudio = false;
		} catch (java.lang.NullPointerException e){
			gp.sysHasAudio = false;
		} catch (Exception e) {
			gp.sysHasAudio = false;
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void checkVolume() {
		switch(volumeScale) {
		case 0:
			volume = -80f;
			break;
		case 1:
			volume = -20f;
			break;
		case 2:
			volume = -12f;
			break;
		case 3:
			volume = -5f;
			break;
		case 4:
			volume = 1f;
			break;
		case 5:
			volume = 6f;
			break;
		}
		fc.setValue(volume);
	}
}

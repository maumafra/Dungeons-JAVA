package moduloD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Config {
	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		Path saveData = Path.of("gameConfig.txt");
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveData.toFile()));
			
			//Activation
			bw.write(String.valueOf(gp.activations));
			bw.newLine();
			//Different Players
			//Music Volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			//SE Volume
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		Path saveData = Path.of("gameConfig.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveData.toFile()));
			
			//Activations
			String s = br.readLine();
			gp.activations = Integer.parseInt(s);
			
			//Different Players
			
			//Music volume
			s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			
			//SE volume
			s = br.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

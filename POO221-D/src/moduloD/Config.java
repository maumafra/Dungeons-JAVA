package moduloD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.HashMap;

public class Config {
	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		Path saveDataTXT = Path.of("gameConfig.txt");
		Path saveDataBIN = Path.of("gameConfig.bin");
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveDataTXT.toFile()));
			
			//Activation
			bw.write(String.valueOf(gp.activations));
			bw.newLine();
			
			//Music Volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			//SE Volume
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();
			
			bw.close();
			
			FileOutputStream fos = new FileOutputStream(saveDataBIN.toFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//HashMap players
			oos.writeObject(gp.players);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		Path saveDataTXT = Path.of("gameConfig.txt");
		Path saveDataBIN = Path.of("gameConfig.bin");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveDataTXT.toFile()));
			
			//Activations
			String s = br.readLine();
			gp.activations = Integer.parseInt(s);
			
			//Music volume
			s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			
			//SE volume
			s = br.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
			
			br.close();
			
			FileInputStream fis = new FileInputStream(saveDataBIN.toFile());
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			//HashMap players
			gp.players = (HashMap<String, Integer>) ois.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

package moduleD;

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
		Path saveAchiBIN = Path.of("gameAchiev.bin");
		
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
			
			oos.close();
			
			fos = new FileOutputStream(saveAchiBIN.toFile());
			oos = new ObjectOutputStream(fos);
			
			//HashMap achiev
			oos.writeObject(gp.pAchiev);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		Path saveDataTXT = Path.of("gameConfig.txt");
		Path saveDataBIN = Path.of("gameConfig.bin");
		Path saveAchiBIN = Path.of("gameAchiev.bin");
		
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
			
			ois.close();
			
			fis = new FileInputStream(saveAchiBIN.toFile());
			ois = new ObjectInputStream(fis);
			//HashMap achiev
			gp.pAchiev = (HashMap<String, Boolean[]>) ois.readObject();
			System.out.println("LOAD: "+gp.pAchiev);
			
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAchievement() {
		Path saveAchiBIN = Path.of("gameAchiev.bin");
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(saveAchiBIN.toFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			System.out.println(gp.pAchiev);
			//HashMap achiev
			oos.writeObject(gp.pAchiev);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

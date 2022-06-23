package moduloD;

import javax.swing.JFrame;

import gameShared.Console;
import gameShared.Player;

public class Main {
	
	public static void main(String[] args) {
		
		GameD game = new GameD();
		game.start(null);
		
		/*JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("DUNGEON");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.gameConfig.loadConfig();
		gamePanel.incActivations();
		gamePanel.gameConfig.saveConfig();
		
		window.pack(); //window vai receber o tamanho de GamePanel
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();*/
	}
}

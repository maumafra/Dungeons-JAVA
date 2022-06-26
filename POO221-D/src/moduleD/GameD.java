package moduleD;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameShared.Console;
import gameShared.Game;
import gameShared.Player;

public class GameD implements Game{

	private GamePanel gp = new GamePanel();
	private Console con = Console.getInstance();
	
	public GameD() {
		this.con = Console.getInstance();
	}
	
	@Override
	public int getActivations() {
		
		return gp.activations;
	}

	@Override
	public Player getBestPlayer() {
		return gp.bestPlayer;
	}

	@Override
	public int getBestPlayerPoints() {
		
		return gp.bestPScore;
	}

	@Override
	public String getDescription() {
		
		return "The legendary captain of the mercenary group Band of the Hawk enters the dungeon seeking for his vengeance. "
				+ "Now, inside de monsters' nest, he will have to survive hordes of enemies trying to find a way to call "
				+ "the God's Hand and fulfill his revenge.";
	}

	@Override
	public int getHowManyPlayers() {
		
		return gp.players.size();
	}

	@Override
	public String getName() {
		
		return "DUNGEONS";
	}

	@Override
	public JPanel getPanel() {
		return this.gp;
	}

	@Override
	public Player getWorstPlayer() {
		
		return gp.worstPlayer;
	}

	@Override
	public int getWorstPlayerPoints() {
		
		return gp.worstPScore;
	}

	@Override
	public void start(Player arg0) {
		
		/*//TODO ver com o professor como vai funcionar isso daqui
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("DUNGEONS");
		
		window.add(gamePanel);*/
		
		//System.out.println(this.gp.player.hasAchies.toString());
		this.gp.gameConfig.loadConfig();
		this.gp.incActivations();
		this.gp.addPlayerAndScore(arg0, 0);
		this.gp.gameConfig.saveConfig();
		
		/*window.pack(); //window vai receber o tamanho de GamePanel
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);*/
		
		this.gp.setupGame();
		this.gp.startGameThread();
		
		if(gp.bestPlayer != null) {
			System.out.println("BEST: "+gp.bestPlayer.getNickname()+": "+gp.bestPScore+
					   "\nWORST: "+gp.worstPlayer.getNickname()+": "+gp.worstPScore);
		}
		
	}

}

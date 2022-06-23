package moduloD;

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
		
		return null;
	}

	@Override
	public int getBestPlayerPoints() {
		
		return 0;
	}

	@Override
	public String getDescription() {
		
		return null;
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
		
		return null;
	}

	@Override
	public int getWorstPlayerPoints() {
		
		return 0;
	}

	@Override
	public void start(Player arg0) {
		
		/*//TODO ver com o professor como vai funcionar isso daqui
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("DUNGEONS");
		
		window.add(gamePanel);*/
		
		this.gp.gameConfig.loadConfig();
		this.gp.incActivations();
		this.gp.addPlayerAndScore(arg0, 0);
		this.gp.gameConfig.saveConfig();
		
		/*window.pack(); //window vai receber o tamanho de GamePanel
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);*/
		
		this.gp.setupGame();
		this.gp.startGameThread();
		
	}

}

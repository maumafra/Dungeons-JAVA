package moduloD;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameShared.Console;
import gameShared.Game;
import gameShared.Player;

public class GameD implements Game{

	private GamePanel gp;
	private Player p1;
	private Console con;
	
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
		if(this.gp == null) {
			System.out.println("JPanel ainda n�o setado. Execute o start do jogo primeiro!");
			return null;
		}
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
		
		//TODO ver com o professor como vai funcionar isso daqui
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("DUNGEONS");
		
		GamePanel gamePanel = new GamePanel(p1, con);
		this.gp = gamePanel;
		window.add(gamePanel);
		
		gamePanel.gameConfig.loadConfig();
		gamePanel.incActivations();
		gamePanel.addPlayer(arg0, 0);
		gamePanel.gameConfig.saveConfig();
		
		window.pack(); //window vai receber o tamanho de GamePanel
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
	}

}

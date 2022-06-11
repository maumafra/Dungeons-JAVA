package moduloD;

public class TileManager {
	
	GamePanel gp;
	Tile[] tile;
	
	public TileManager(GamePanel gp) {
		setGamePanel(gp);
		tile = new Tile[10];
	}
	
	public void setGamePanel(GamePanel gp) {
		this.gp = gp;
	}
}

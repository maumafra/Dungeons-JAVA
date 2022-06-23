package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_ZoddHorn extends Entity{

	public OBJ_ZoddHorn(GamePanel gp) {
		
		super(gp);
		
		name = "ZoddHorn";
		down1 = setup("/objects/ZoddHorn", gp.tileSize, gp.tileSize);
		type = typeConsumable;
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = true;
		gp.player.score += 5000;
		gp.ui.showMessage("Congratulations! You beat Nosferatu Zodd!");
		gp.gameState = gp.gameWinState;
		gp.stopMusic();
		return picksItem;
	}
}

package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends Entity{
	
	public OBJ_Boots(GamePanel gp) {
		
		super(gp);
		
		name = "Boots";
		down1 = setup("/objects/Boots", gp.tileSize, gp.tileSize);
		type = typeConsumable;
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = false;
		if(gp.player.hasBoots == false) {
			gp.player.score += 50;
			gp.player.speed += 1;
			gp.player.hasBoots = true;
			gp.ui.showMessage("Speed Up!");
			return true;
		}
		return picksItem;
	}
}

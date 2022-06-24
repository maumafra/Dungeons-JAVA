package moduleD;

public class OBJ_ShieldPickUp extends Entity {

	public OBJ_ShieldPickUp(GamePanel gp) {
		super(gp);		
		
		name = "Shield";
		
		down1 = setup("/objects/Shield", gp.tileSize, gp.tileSize);
		
		type = typeConsumable;	
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = false;
		if(gp.player.hasShield == false) {
			gp.player.hasShield = true;
			return true;
		}
		return picksItem;
	}

}

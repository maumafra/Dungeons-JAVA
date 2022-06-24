package moduleD;

public class OBJ_FullHeartPickUp extends Entity {

	public OBJ_FullHeartPickUp(GamePanel gp) {
		super(gp);
		name = "Full Heart";
		
		down1 = setup("/objects/HeartPickUp", gp.tileSize, gp.tileSize);
		
		type = typeConsumable;	
	
	}

	public boolean use (Entity entity) {
		boolean picksItem = false;
		if(gp.player.life < 5) {
			gp.player.life += 2;
			return true;
		}
		return picksItem;
	}
}

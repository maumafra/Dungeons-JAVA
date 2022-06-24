package moduleD;

public class OBJ_HalfHeartPickUp extends Entity{

	public OBJ_HalfHeartPickUp(GamePanel gp) {
		super(gp);
		name = "Half Heart";
		
		down1 = setup("/objects/HalfHeartPickUp", gp.tileSize, gp.tileSize);
		
		type = typeConsumable;		
		
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = false;
		if(gp.player.life < 6) {
			gp.player.life += 1;
			return true;
		}
		return picksItem;
	}
}

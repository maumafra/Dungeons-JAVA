package moduleD;

public class OBJ_KnifePickup extends Entity {

	
	public OBJ_KnifePickup(GamePanel gp) {
		super(gp);
		name = "Dropped Knife";
		
		down1 = setup("/projectiles/KnifeUp", gp.tileSize, gp.tileSize);
		
		type = typeConsumable;
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = false;
		if(gp.player.ammo < gp.player.maxAmmo) {
			gp.player.ammo++;
			return true;
		}
		return picksItem;
	}
}

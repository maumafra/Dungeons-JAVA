package moduloD;

public class OBJ_KnifePickup extends Entity {

	
	public OBJ_KnifePickup(GamePanel gp) {
		super(gp);
		name = "Dropped Knife";
		
		down1 = setup("/projectiles/KnifeUp", gp.tileSize, gp.tileSize);
		
		type = typeConsumable;
	}
}

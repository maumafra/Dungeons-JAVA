package moduloD;

public class OBJ_Knife extends Projectile{

	GamePanel gp;
	
	public OBJ_Knife(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Throwable Knife";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage() {
		up1 = setup("/projectiles/KnifeUp", gp.tileSize, gp.tileSize);
		up2 = setup("/projectiles/KnifeUp", gp.tileSize, gp.tileSize);
		down1 = setup("/projectiles/KnifeDown", gp.tileSize, gp.tileSize);
		down2 = setup("/projectiles/KnifeDown", gp.tileSize, gp.tileSize);
		left1 = setup("/projectiles/KnifeLeft", gp.tileSize, gp.tileSize);
		left2 = setup("/projectiles/KnifeLeft", gp.tileSize, gp.tileSize);
		right1 = setup("/projectiles/KnifeRight", gp.tileSize, gp.tileSize);
		right2 = setup("/projectiles/KnifeRight", gp.tileSize, gp.tileSize);
	}
	
	public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		if(user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void spendResource(Entity user) {
		user.ammo -= useCost;
	}
}

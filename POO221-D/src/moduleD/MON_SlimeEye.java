package moduleD;

import java.util.Random;

public class MON_SlimeEye extends Entity{

	public MON_SlimeEye(GamePanel gp) {
		super(gp);
		
		type = 2;
		name = "Eyed Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 5;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 13;
		solidArea.width = 42;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attack = 1;
		getImage();
	}
	public void getImage() {
		//TODO Fazer a animação
		up1 = setup("/enemies/SlimeEye1", gp.tileSize, gp.tileSize);
		up2 = setup("/enemies/SlimeEye2", gp.tileSize, gp.tileSize);
		down1 = setup("/enemies/SlimeEye1", gp.tileSize, gp.tileSize);
		down2 = setup("/enemies/SlimeEye2", gp.tileSize, gp.tileSize);
		left1 = setup("/enemies/SlimeEye1", gp.tileSize, gp.tileSize);
		left2 = setup("/enemies/SlimeEye2", gp.tileSize, gp.tileSize);
		right1 = setup("/enemies/SlimeEye1", gp.tileSize, gp.tileSize);
		right2 = setup("/enemies/SlimeEye2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
		if(onPath == true) {
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
			searchPath(goalCol, goalRow);
		} else {
			actionLockCounter ++;
			
			if(actionLockCounter == 120) {
				random = new Random();
				int i = random.nextInt(100)+1;//pra não contar o 0
				
				if(i <=25) {
					direction = "up";
				}
				if(i > 25 && i <=50) {
					direction = "down";
				}
				if(i > 50 && i <=75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		}
	}

	public void checkDrop() {
		int i = new Random().nextInt(100)+1;
		
		//SET THE MONSTER DROP
		if(i<20 && gp.player.ammo < gp.player.maxAmmo) {
			dropItem(new OBJ_KnifePickup(gp));
		}
		if(i>=50 && i<=55) {
			dropItem(new OBJ_Boots(gp));
		}
		if(i>55 && i<= 60) {
			dropItem(new OBJ_ShieldPickUp(gp));
		}
		if(i>75) {
			if (i < 90) {
				dropItem(new OBJ_HalfHeartPickUp(gp));
			}
			else {
				dropItem(new OBJ_FullHeartPickUp(gp));
			}
		}
	}
}

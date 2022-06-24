package moduleD;

import java.util.Random;

public class BOSS_Zodd extends Entity{
	
	public BOSS_Zodd(GamePanel gp) {
		super(gp);
		type = 2;
		name = "Zodd";
		speed = 1;
		maxLife = 50;
		life = maxLife;
		
		solidArea.x = gp.tileSize/2;
		solidArea.y = gp.tileSize/2;
		solidArea.width = gp.tileSize;
		solidArea.height = (int) (gp.tileSize*1.5);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attack = 2;
		
		getImage();
	}

	public void getImage() {
		up1 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		up2 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		down1 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		down2 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		right1 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
		right2 = setup("/enemies/ZoddLeft1", gp.tileSize*2, gp.tileSize*2);
	}
	
	public void setAction() {
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
	
	public void checkDrop() {
		worldX += solidArea.x ;
		worldY += solidArea.y;
		dropItem(new OBJ_ZoddHorn(gp));
	}
}

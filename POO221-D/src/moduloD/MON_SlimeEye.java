package moduloD;

import java.util.Random;

public class MON_SlimeEye extends Entity{

	public MON_SlimeEye(GamePanel gp) {
		super(gp);
		
		type = 2;
		name = "Eyed Slime";
		speed = 1;
		maxLife = 5;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 13;
		solidArea.width = 42;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		//TODO Fazer a animação
		up1 = setup("/enemies/SlimeEye1");
		up2 = setup("/enemies/SlimeEye1");
		down1 = setup("/enemies/SlimeEye1");
		down2 = setup("/enemies/SlimeEye1");
		left1 = setup("/enemies/SlimeEye1");
		left2 = setup("/enemies/SlimeEye1");
		right1 = setup("/enemies/SlimeEye1");
		right2 = setup("/enemies/SlimeEye1");
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
}

package moduleD;

import java.util.Random;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
	}
	
	public void setEnemies() {

		
		gp.enem[5] = new MON_SlimeEye(gp);
		gp.enem[5].worldX = 3 *gp.tileSize;
		gp.enem[5].worldY = 4 *gp.tileSize;
		
		gp.enem[6] = new MON_SlimeEye(gp);
		gp.enem[6].worldX = 23 *gp.tileSize;
		gp.enem[6].worldY = 3 *gp.tileSize;
		
		gp.enem[7] = new MON_SlimeEye(gp);
		gp.enem[7].worldX = 22 *gp.tileSize;
		gp.enem[7].worldY = 7 *gp.tileSize;
		
		gp.enem[8] = new MON_SlimeEye(gp);
		gp.enem[8].worldX = 2 *gp.tileSize;
		gp.enem[8].worldY = 3 *gp.tileSize;
		
		gp.enem[9] = new MON_SlimeEye(gp);
		gp.enem[9].worldX = 2 *gp.tileSize;
		gp.enem[9].worldY = 4 *gp.tileSize;
		
		
	}
	
	public void respawnEnemies() {
		int nullEnem = 0;
		for(int i = 0; i< gp.enem.length; i++) {
			if (gp.enem[i] == null && i != 0) {
				nullEnem++;
			}
		}
		
		System.out.println("NULL ENEMIES: "+nullEnem);
		
		if(nullEnem > 0) {
			int numEnemSpawn = new Random().nextInt(nullEnem)+1;
			if(numEnemSpawn > 6) {
				numEnemSpawn = 6;
			}
			int typeEnemSpawn;
			System.out.println("ENEMIES TO SPAWN: "+numEnemSpawn);
			for (int i = 0; i< gp.enem.length; i++) {
				if(gp.enem[i] == null && i != 0 && numEnemSpawn > 0) {
					typeEnemSpawn = new Random().nextInt(100)+1;
					if(typeEnemSpawn <= 75) {
						gp.enem[i] = new MON_SlimeEye(gp);
						if(typeEnemSpawn < 15) {
							gp.enem[i].worldX = 1*gp.tileSize;
							gp.enem[i].worldY = 1*gp.tileSize + numEnemSpawn*gp.tileSize/2;
						} 
						else if (typeEnemSpawn < 30) {
							gp.enem[i].worldX = 1*gp.tileSize + numEnemSpawn*gp.tileSize/2;
							gp.enem[i].worldY = 8*gp.tileSize - numEnemSpawn*gp.tileSize/2;
						} 
						else if (typeEnemSpawn < 45) {
							gp.enem[i].worldX = 27*gp.tileSize;
							gp.enem[i].worldY = 2*gp.tileSize  + numEnemSpawn*gp.tileSize/2;
						} 
						else {
							gp.enem[i].worldX = 27*gp.tileSize - numEnemSpawn*gp.tileSize/2;
							gp.enem[i].worldY = 8*gp.tileSize  - numEnemSpawn*gp.tileSize/2;
						}
						numEnemSpawn--;
					}
					if (typeEnemSpawn > 75) {
						gp.enem[i] = new MON_GreatGoat(gp);
						if(typeEnemSpawn < 81) {
							gp.enem[i].worldX = 1*gp.tileSize;
							gp.enem[i].worldY = 1*gp.tileSize;
						} 
						else if (typeEnemSpawn < 87) {
							gp.enem[i].worldX = 1*gp.tileSize;
							gp.enem[i].worldY = 8*gp.tileSize;
						} 
						else if (typeEnemSpawn < 93) {
							gp.enem[i].worldX = 27*gp.tileSize;
							gp.enem[i].worldY = 2*gp.tileSize;
						} 
						else {
							gp.enem[i].worldX = 27*gp.tileSize;
							gp.enem[i].worldY = 8*gp.tileSize;
						}
						numEnemSpawn--;
					}
				}
			}
		}
	}
}

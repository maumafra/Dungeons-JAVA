package moduloD;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		gp.obj[0] = new OBJ_Behelit(gp);
		gp.obj[0].worldX = 18 * gp.tileSize;
		gp.obj[0].worldY = 3 * gp.tileSize;
		
		gp.obj[1] = new OBJ_ZoddHorn(gp);
		gp.obj[1].worldX = 27 * gp.tileSize;
		gp.obj[1].worldY = 2 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Boots(gp);
		gp.obj[2].worldX = 16 * gp.tileSize;
		gp.obj[2].worldY = 5 * gp.tileSize;
	}
	
	public void setEnemies() {
		gp.enem[0] = new MON_SlimeEye(gp);
		gp.enem[0].worldX = 3 *gp.tileSize;
		gp.enem[0].worldY = 5 *gp.tileSize;
		
		gp.enem[1] = new MON_SlimeEye(gp);
		gp.enem[1].worldX = 3 *gp.tileSize;
		gp.enem[1].worldY = 6 *gp.tileSize;
		
		gp.enem[2] = new MON_SlimeEye(gp);
		gp.enem[2].worldX = 27 *gp.tileSize;
		gp.enem[2].worldY = 6 *gp.tileSize;
		
		gp.enem[3] = new MON_SlimeEye(gp);
		gp.enem[3].worldX = 10 *gp.tileSize;
		gp.enem[3].worldY = 4 *gp.tileSize;
		
		gp.enem[4] = new MON_SlimeEye(gp);
		gp.enem[4].worldX = 3 *gp.tileSize;
		gp.enem[4].worldY = 7 *gp.tileSize;
		
		gp.enem[5] = new MON_SlimeEye(gp);
		gp.enem[5].worldX = 3 *gp.tileSize;
		gp.enem[5].worldY = 4 *gp.tileSize;
		
		gp.enem[6] = new MON_SlimeEye(gp);
		gp.enem[6].worldX = 3 *gp.tileSize;
		gp.enem[6].worldY = 3 *gp.tileSize;
		
		gp.enem[7] = new MON_SlimeEye(gp);
		gp.enem[7].worldX = 2 *gp.tileSize;
		gp.enem[7].worldY = 5 *gp.tileSize;
		
		gp.enem[8] = new MON_SlimeEye(gp);
		gp.enem[8].worldX = 2 *gp.tileSize;
		gp.enem[8].worldY = 3 *gp.tileSize;
		
		gp.enem[9] = new MON_SlimeEye(gp);
		gp.enem[9].worldX = 2 *gp.tileSize;
		gp.enem[9].worldY = 4 *gp.tileSize;
		
		gp.enem[10] = new MON_GreatGoat(gp);
		gp.enem[10].worldX = 9 *gp.tileSize;
		gp.enem[10].worldY = 4 *gp.tileSize;
	}
}

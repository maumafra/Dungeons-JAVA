package moduloD;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new OBJ_Behelit(gp);
		gp.obj[0].worldX = 14 * gp.tileSize;
		gp.obj[0].worldY = 3 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Boots(gp);
		gp.obj[1].worldX = 14 * gp.tileSize;
		gp.obj[1].worldY = 2 * gp.tileSize;
		
		gp.obj[2] = new OBJ_ZoddHorn(gp);
		gp.obj[2].worldX = 27 * gp.tileSize;
		gp.obj[2].worldY = 2 * gp.tileSize;
		
		gp.obj[3] = new OBJ_Boots(gp);
		gp.obj[3].worldX = 3 * gp.tileSize;
		gp.obj[3].worldY = 2 * gp.tileSize;
	}
}

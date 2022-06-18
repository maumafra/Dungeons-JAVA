package moduloD;

public class OBJ_Achievement extends Entity {
	
	public OBJ_Achievement(GamePanel gp) {
		super(gp);
		
		name = "SacrificeMark";
		image = setup("/achievements/AchievementEmpty", gp.tileSize, gp.tileSize);
		//image2 = setup("/objects/SacrificeMark02");
		//image3 = setup("/objects/SacrificeMark03");
		//image4 = setup("/objects/SacrificeMark04");
	}
}

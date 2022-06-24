package moduleD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_SacrificeMark extends Entity {
	
	public OBJ_SacrificeMark(GamePanel gp) {
		super(gp);
		
		name = "SacrificeMark";
		image = setup("/objects/SacrificeMark01", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/SacrificeMark02", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/SacrificeMark03", gp.tileSize, gp.tileSize);
		image4 = setup("/objects/SacrificeMark04", gp.tileSize, gp.tileSize);
	}
	
}

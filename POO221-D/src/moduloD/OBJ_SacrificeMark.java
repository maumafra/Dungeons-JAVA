package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_SacrificeMark extends Entity {
	
	public OBJ_SacrificeMark(GamePanel gp) {
		super(gp);
		
		name = "SacrificeMark";
		image = setup("/objects/SacrificeMark01");
		image2 = setup("/objects/SacrificeMark02");
		image3 = setup("/objects/SacrificeMark03");
		image4 = setup("/objects/SacrificeMark04");
	}
	
}

package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_SacrificeMark04 extends SuperObject{
GamePanel gp;
	
	public OBJ_SacrificeMark04(GamePanel gp) {
		this.gp = gp;
		
		name = "Sacrifice Mark";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/SacrificeMark04.png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
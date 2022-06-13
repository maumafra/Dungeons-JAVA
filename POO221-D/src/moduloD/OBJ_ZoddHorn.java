package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_ZoddHorn extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_ZoddHorn(GamePanel gp) {
		
		this.gp = gp;
		
		name = "ZoddHorn";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/ZoddHorn.png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Behelit extends SuperObject{
	
	GamePanel gp;

	public OBJ_Behelit(GamePanel gp) {
		
		this.gp = gp;
		
		name = "Behelit";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Behelit.png")); 
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

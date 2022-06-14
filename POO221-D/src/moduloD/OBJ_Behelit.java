package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Behelit extends Entity{
	
	public OBJ_Behelit(GamePanel gp) {
		
		super(gp);
		
		name = "Behelit";
		down1 = setup("/objects/Behelit");
		pickup = true;
	}
}

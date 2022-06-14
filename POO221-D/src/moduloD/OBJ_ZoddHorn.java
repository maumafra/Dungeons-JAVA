package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_ZoddHorn extends Entity{

	public OBJ_ZoddHorn(GamePanel gp) {
		
		super(gp);
		
		name = "ZoddHorn";
		down1 = setup("/objects/ZoddHorn");
		pickup = true;
	}
}

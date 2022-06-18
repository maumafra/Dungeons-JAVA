package moduloD;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Heart extends Entity{
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		
		name = "Heart";
		image = setup("/objects/FullHeart", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/HalfHeart", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/EmptyHeart", gp.tileSize, gp.tileSize);
	}
}

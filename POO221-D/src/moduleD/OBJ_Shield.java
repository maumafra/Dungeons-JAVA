package moduleD;

public class OBJ_Shield extends Entity{

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		name = "Shield";
		image = setup("/objects/ShieldIcon", gp.tileSize, gp.tileSize);
	}

}

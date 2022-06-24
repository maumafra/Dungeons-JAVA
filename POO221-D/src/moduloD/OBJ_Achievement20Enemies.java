package moduloD;

import java.awt.Image;

public class OBJ_Achievement20Enemies extends OBJ_Achievement{
	
	
	public OBJ_Achievement20Enemies(GamePanel gp) {
		super(gp);
		name = "Greatsword";
		code = 1;
		motivation = "Kill 20 enemies.";
		image = setup("/achievements/AchievementKill20Enemies", gp.tileSize, gp.tileSize);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getMotivation() {
		// TODO Auto-generated method stub
		return motivation;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return setupRawImage("AchievementKill20Enemies");
	}

}

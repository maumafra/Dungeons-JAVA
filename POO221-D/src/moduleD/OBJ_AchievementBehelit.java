package moduleD;

import java.awt.Image;

public class OBJ_AchievementBehelit extends OBJ_Achievement {

	public OBJ_AchievementBehelit(GamePanel gp) {
		super(gp);
		name = "Behelit";
		code = 3;
		motivation = "Collect the egg of the King.";
		image = setup("/achievements/AchievementBehelit", gp.tileSize, gp.tileSize);
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
		return setupRawImage("AchievementBehelit");
	}

}

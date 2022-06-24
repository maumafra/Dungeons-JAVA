package moduloD;

import java.awt.Image;

public class OBJ_AchievementSurvive1Minute extends OBJ_Achievement {

	public OBJ_AchievementSurvive1Minute(GamePanel gp) {
		super(gp);
		name = "Struggler";
		code = 2;
		motivation = "Survive 1 minute.";
		image = setup("/achievements/AchievementSurvive1Minute", gp.tileSize, gp.tileSize);
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
		return setupRawImage("AchievementSurvive1Minute");
	}
}

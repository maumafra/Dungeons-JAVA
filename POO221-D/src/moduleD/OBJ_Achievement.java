package moduleD;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameShared.Achievement;

public class OBJ_Achievement extends Entity implements Achievement {
	
	String motivation = "";
	int code;
	
	public OBJ_Achievement(GamePanel gp) {
		super(gp);
		
		name = "Empty Achievement";
		code = 0;
		image = setup("/achievements/AchievementEmpty", gp.tileSize, gp.tileSize);
		//image2 = setup("/objects/SacrificeMark02");
		//image3 = setup("/objects/SacrificeMark03");
		//image4 = setup("/objects/SacrificeMark04");
	}

	public Image setupRawImage (String achName) {
		Image rawImage = null;
		try {
			rawImage = ImageIO.read(getClass().getResourceAsStream("/achievements/"+achName+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rawImage;
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
		return setupRawImage("AchievementEmpty");
	}
}

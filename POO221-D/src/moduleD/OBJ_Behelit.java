package moduleD;

import java.io.IOException;

import javax.imageio.ImageIO;

import gameShared.Console;

public class OBJ_Behelit extends Entity{
	
	public OBJ_Behelit(GamePanel gp) {
		
		super(gp);
		
		name = "Behelit";
		down1 = setup("/objects/Behelit", gp.tileSize, gp.tileSize);
		type = typeConsumable;
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = true;
		gp.player.score += 50;
		gp.player.hasBehelit = true;
		gp.playSE(1);
		gp.ui.showMessage("You feel an evil presence watching you...");
		if(gp.player.hasAchies[2] == false) {
			gp.ui.setShowAchievement(3);
			gp.player.hasAchies[2] = true;
			if(gp.actualPlayer != null) {
				gp.pAchiev.put(gp.actualPlayer.getNickname(), gp.player.hasAchies);
				gp.gameConfig.saveAchievement();
				Console c = Console.getInstance();
				c.addAchievements(gp.actualPlayer, new OBJ_AchievementBehelit(gp));
			}
		}
		return picksItem;
	}
}

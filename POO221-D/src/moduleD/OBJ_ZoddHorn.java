package moduleD;

import java.io.IOException;

import javax.imageio.ImageIO;

import gameShared.Console;

public class OBJ_ZoddHorn extends Entity{

	public OBJ_ZoddHorn(GamePanel gp) {
		
		super(gp);
		
		name = "ZoddHorn";
		down1 = setup("/objects/ZoddHorn", gp.tileSize, gp.tileSize);
		type = typeConsumable;
	}
	
	public boolean use (Entity entity) {
		boolean picksItem = true;
		gp.player.score += 200;
		if(gp.actualPlayer != null) {
			gp.players.put(gp.actualPlayer.getNickname(), gp.player.score);
			System.out.println("Player: "+gp.actualPlayer.getNickname()+" - Score:"+gp.player.score);
			Console c = Console.getInstance();
			//c.addPlayer(gp.actualPlayer);
			c.addPoints(gp.actualPlayer, gp.player.score);
			System.out.println(c.getRanking());
			
			if(gp.player.score > gp.bestPScore) {
				gp.bestPlayer = gp.actualPlayer;
				gp.bestPScore = gp.player.score;
			}
			
			if(gp.worstPScore == 0 || gp.player.score < gp.worstPScore) {
				gp.worstPlayer = gp.actualPlayer;
				gp.worstPScore = gp.player.score;
			}
			System.out.println("BEST: "+gp.bestPlayer.getNickname()+": "+gp.bestPScore+
							   "\nWORST: "+gp.worstPlayer.getNickname()+": "+gp.worstPScore);
		}
		gp.gameConfig.saveConfig();
		gp.gameState = gp.gameWinState;
		gp.stopMusic();
		return picksItem;
	}
}

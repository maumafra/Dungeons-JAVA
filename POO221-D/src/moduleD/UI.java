package moduleD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	BufferedImage emptyAchievement, kills20Achievement, surv1MinAchievement, behelitAchievement;
	BufferedImage sMarkImage1 , sMarkImage2, sMarkImage3, sMarkImage4;
	BufferedImage fullHeart, emptyHeart, halfHeart, shield;
	BufferedImage knife;
	public boolean messageOn = false, achievementOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;
	public int optionsScreenState = 0;
	
	
	//ACHIEVEMENTS
	public int achIndex = 0;
	int achievementCounter = 0;
	int[] achCodes = new int[3];
	String[] achText = new String[3];
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE TITLE OBJECT
		Entity sMark = new OBJ_SacrificeMark(gp);
		sMarkImage1 = sMark.image;
		sMarkImage2 = sMark.image2;
		sMarkImage3 = sMark.image3;
		sMarkImage4 = sMark.image4;
		
		Entity achievement = new OBJ_Achievement(gp);
		emptyAchievement = achievement.image;
		
		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		fullHeart = heart.image;
		halfHeart = heart.image2;
		emptyHeart = heart.image3;
		Entity knifeIcon = new OBJ_Knife(gp);
		knife = knifeIcon.up1;
		Entity shieldIcon = new OBJ_Shield(gp);
		shield = shieldIcon.image;
		
		//ACHIEVEMENTS
		OBJ_Achievement ach = new OBJ_Achievement20Enemies(gp);
		kills20Achievement = ach.image;
		achText[0] = ach.getName();
		ach = new OBJ_AchievementSurvive1Minute(gp);
		surv1MinAchievement = ach.image;
		achText[1] = ach.getName();
		ach = new OBJ_AchievementBehelit(gp);
		behelitAchievement = ach.image;
		achText[2] = ach.getName();
		
	}
	
	public void setShowAchievement(int achCode) {
		achievementOn = true;
		for(int i =0; i<achCodes.length; i++) {
			if(achCodes[i] == 0) {
				achCodes[i] = achCode;
				break;
			}
		}
	}
	
	private void achievementsHandler() {
		if(achievementOn == true && achIndex < 3) {
			if(achCodes[achIndex] == 0) {
				return;
			}
			if(achCodes[achIndex] == 1) {
				showAchievement(kills20Achievement, achText[0]);
			}
			if(achCodes[achIndex] == 2) {
				showAchievement(surv1MinAchievement, achText[1]);
			}
			if(achCodes[achIndex] == 3) {
				showAchievement(behelitAchievement, achText[2]);;
			}
		}
	}
	
	private void showAchievement(BufferedImage icon, String text) {
		int x = gp.tileSize*6 + 20;
		int y = gp.tileSize/2;
		int width = gp.tileSize*4 -20;
		int height = (int) (gp.tileSize*1.12);
		
		Color c = new Color(0,0,0,240);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 10, 10);
		
		g2.setColor(Color.orange);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(x+3, y+3, width-6, height-6);
		
		g2.drawImage(icon, x+3, y+3, null);
		g2.setColor(new Color(170,68,225));
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));
		g2.drawString(text, x +gp.tileSize +8, y +25);
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 11f));
		g2.drawString("Achievement Unlocked!", x +gp.tileSize +8, y +40);
		
		achievementCounter++;
		if(achievementCounter > 240) {
			achievementCounter = 0;
			achIndex++;
			System.out.println(achIndex+"");
			if(achIndex == 3) {
				achievementOn = false;
			}
		}
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
		messageCounter = 0;
	}
	
	public void draw (Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			
				drawPlayerLife();
				drawPlayerAmmo();
				
				//TIME
				playTime += (double)1/60;
				drawPlayTime();
				
				//SCORE
				drawPlayerScore();
				
				//MESSAGE
				if(messageOn == true) {
					g2.setColor(Color.white);
					if(message.length() == 41) {
						g2.setColor(Color.yellow);
					} else {
						g2.setFont(g2.getFont().deriveFont(24F));
					}
					g2.drawString(message, 25, 420);
					
					messageCounter++;
					
					if(messageCounter > 180) {
						messageCounter = 0;
						messageOn = false;
					}
				}
				
				//ACHIEVEMENTS
				achievementsHandler();
			
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPlayTime();
			drawPlayerAmmo();
			drawPlayerScore();
			drawPauseScreen();
			
		}
		//OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawPlayerLife();
			drawPlayTime();
			drawPlayerAmmo();
			drawPlayerScore();
			drawOptionsScreen();
			
		}
		//GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		//GAME WIN STATE
		if(gp.gameState == gp.gameWinState) {
			drawWinScreen();
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setColor(new Color(0,0,0,180));
		g2.fillRect(0, (int)(gp.tileSize*2.5), gp.screenWidth, gp.tileSize*2);
		
		int textX;
		int textY;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 90f));
		
		text = "YOU DIED";
		
		//SHADOW
		g2.setColor(new Color(63,0,0));
		textX = getXCenterText(text);
		textY = gp.tileSize*4;
		g2.drawString(text, textX+2, textY+2);
		//TEXT
		g2.setColor(new Color(120,0,0));
		g2.drawString(text, textX, textY);
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		g2.setColor(new Color(0,0,0,180));
		text = "Retry";
		textX = getXCenterText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX+1, textY+1);
		g2.setColor(new Color(120,0,0));
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.setColor(new Color(0,0,0,180));
			g2.drawString(">", textX-38, textY+2);
			g2.setColor(new Color(120,0,0));
			g2.drawString(">", textX-40, textY);
		}
		
		//Back to Menu
		g2.setColor(new Color(0,0,0,180));
		text = "Quit";
		textX = getXCenterText(text);
		textY += gp.tileSize*1.8;
		g2.drawString(text, textX+1, textY+1);
		g2.setColor(new Color(120,0,0));
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.setColor(new Color(0,0,0,180));
			g2.drawString(">", textX-38, textY+2);
			g2.setColor(new Color(120,0,0));
			g2.drawString(">", textX-40, textY);
		}
	}
	
	public void drawWinScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text;
		int frameX = gp.tileSize/2;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*9 + gp.tileSize/2;
		int frameHeight = gp.tileSize*8;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		int textX;
		int textY;
		
		//MENSAGEM COM DESTAQUE
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
		g2.setColor(Color.orange);
		text = "Congratulations!";
		textX = getXCenterText(text);
		textY = frameY + (int)(gp.tileSize*1.5);
		g2.drawString(text, textX + 1, textY + 1);
		g2.setColor(Color.yellow);
		g2.drawString(text, textX, textY);
		
		//MENSAGEM SEM DETAQUE
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		g2.setColor(Color.white);
		text = "You beat Nosferatu Zodd!";
		textX = getXCenterText(text);
		textY += (int)(gp.tileSize*1.5);
		g2.drawString(text, textX, textY);
		
		//TEMPO
		text = "Time: "+dFormat.format(playTime)+"s!";
		textX = getXCenterText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//MOSTRAR PONTUACAO
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
		g2.setColor(Color.ORANGE);
		text = "Score: "+gp.player.score+"!";
		textX = getXCenterText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX + 1, textY + 1);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
		g2.setColor(Color.yellow);
		g2.drawString(text, textX, textY);
		
		//RETRY
		g2.setFont(g2.getFont().deriveFont(40f));
		g2.setColor(Color.GRAY);
		text = "Retry";
		textX = getXCenterText(text);
		textY += gp.tileSize*1.5;
		g2.drawString(text, textX+1, textY+1);
		g2.setColor(Color.white);
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.setColor(Color.GRAY);
			g2.drawString(">", textX-39, textY+1);
			g2.setColor(Color.white);
			g2.drawString(">", textX-40, textY);
		}
		
		//QUIT
		g2.setColor(Color.GRAY);
		text = "Quit";
		textX = getXCenterText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX+1, textY+1);
		g2.setColor(Color.white);
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.setColor(Color.GRAY);
			g2.drawString(">", textX-39, textY+1);
			g2.setColor(Color.white);
			g2.drawString(">", textX-40, textY);
		}
		
	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//SUB WINDOW
		int frameX = gp.tileSize*1 + 35;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*7;
		int frameHeight = gp.tileSize*8;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(optionsScreenState) {
		case 0:
			options_top(frameX, frameY);
			break;
		case 1:
			options_control(frameX, frameY);
			break;
		case 2:
			options_quitConfirm(frameX, frameY);
			break;
		}
		gp.keyH.enterPressed = false;
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		//TITLE
		String text = "Options";
		textX = getXCenterText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//MUSIC
		textX = frameX + 40;
		textY += gp.tileSize*2;
		g2.drawString("Music", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 20, textY);
		}
		//SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX - 20, textY);
		}
		//CONTROL
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX - 20, textY);
			if(gp.keyH.enterPressed == true) {
				optionsScreenState = 1;
				commandNum = 0;
			}
		}
		//QUIT
		textY += gp.tileSize;
		g2.drawString("Quit", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX - 20, textY);
			if(gp.keyH.enterPressed == true) {
				optionsScreenState = 2;
				commandNum = 0;
			}
		}
		//BACK
		textY += gp.tileSize + gp.tileSize/2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX - 20, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		//MUSIC VOLUME
		textX = frameX + (int)(gp.tileSize*3.8);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//SE VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.gameConfig.saveConfig();
	}
	
	public void options_control(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		//TITLE
		String text = "Controls";
		textX = getXCenterText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//COMMANDS
		textX = frameX + 40;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Attack", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Shoot", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;
		
		//INPUTS
		textX = frameX + (int)(gp.tileSize*3.8);
		textY = frameY + gp.tileSize*2;
		g2.drawString("WASD", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += gp.tileSize;
		g2.drawString("F", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;
		g2.drawString("P", textX, textY);
		textY += gp.tileSize;
		
		//BACK
		textX = frameX + 40;
		textY = frameY + (int)(gp.tileSize*7.5);
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 20, textY);
			if(gp.keyH.enterPressed == true) {
				optionsScreenState = 0;
				commandNum = 2;
			}
		}
		
	}
	
	public void options_quitConfirm (int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*2;
		
		currentDialogue = "Quit the game and\nreturn to the title screen?";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, getXCenterText(line), textY);
			textY += 40;
		}
		
		//YES 
		String text = "Yes";
		textX = getXCenterText(text);
		textY += gp.tileSize*2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-20, textY);
			if(gp.keyH.enterPressed == true) {
				optionsScreenState = 0;
				gp.gameState = gp.titleState;
				if(gp.sysHasAudio) {
					gp.music.stop();
				}
				gp.restartGame();
			}
		}
		//NO
		text = "No";
		textX = getXCenterText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-20, textY);
			if(gp.keyH.enterPressed == true) {
				optionsScreenState = 0;
				commandNum = 3;
			}
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25	);
	}
	
	public void drawPlayerScore() {
		String text = "Score: "+gp.player.score;
		g2.setColor(Color.yellow);
		g2.drawString(text, getXCenterText(text), gp.tileSize*9 + gp.tileSize/2);
	}
	
	public void drawPlayTime() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 27F));
		String text = ""+dFormat.format(playTime);
		g2.drawString(text+" s", getXCenterText(text), gp.tileSize*1);
	}

	public void drawPlayerAmmo() {
		int x = gp.tileSize/2 - 30;
		int y = gp.tileSize*9 - 5;
		int i = 0;
				
		//DRAW CURRENT LIFE
		while(i < gp.player.ammo) {
			g2.drawImage(knife, x, y, null);
			i++;
			x += 30;
		}
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2 - 10;
		int y = gp.tileSize/2 - 10;
		int i = 0;
		
		// DRAW MAX LIFE
		while(i < gp.player.maxLife/2) {
			g2.drawImage(emptyHeart, x, y, null);
			i++;
			x += gp.tileSize + 5;
		}
		
		if(gp.player.hasShield) {
			g2.drawImage(shield, x, y, null);
		}
		
		//RESET
		x = gp.tileSize/2 - 10;
		y = gp.tileSize/2 - 10;
		i = 0;
		
		//DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(halfHeart, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(fullHeart, x, y, null);
			}
			i++;
			x += gp.tileSize + 5;
		}
	}

	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "DUNGEONS";
			int x = getXCenterText(text);
			int y = gp.screenHeight/2 - gp.tileSize*3;
			//SHADOW
			g2.setColor(Color.red);
			g2.drawString(text, x+5, y+5);
			//MAIN COLOR
			g2.setColor(Color.orange);
			g2.drawString(text, x, y);
			
			//SACRIFICE MARK
			x = gp.screenWidth/2 - gp.tileSize;
			y += gp.tileSize/2;
			g2.drawImage(sMarkImage1, x, y, gp.tileSize*1, gp.tileSize*1, null);
			x += gp.tileSize;
			g2.drawImage(sMarkImage2, x, y, gp.tileSize*1, gp.tileSize*1, null);
			y += gp.tileSize;
			g2.drawImage(sMarkImage4, x, y, gp.tileSize*1, gp.tileSize*1, null);
			x -= gp.tileSize;
			g2.drawImage(sMarkImage3, x, y, gp.tileSize*1, gp.tileSize*1, null);
			
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
			
			text = "NEW GAME";
			x = getXCenterText(text);
			y = gp.screenHeight/2 + gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "ACHIEVEMENTS";
			x = getXCenterText(text);
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "EXIT GAME";
			x = getXCenterText(text);
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		}
		else if(titleScreenState == 1) {
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
			g2.setColor(Color.orange);
			
			String text = "ACHIEVEMENTS";
			int x = getXCenterText(text);
			int y = gp.screenHeight/2 - gp.tileSize*4;
			g2.drawString(text, x, y);
			
			BufferedImage achIcon;
			
			if(gp.player.hasAchies[0] == true) {
				achIcon = kills20Achievement;
			} else {
				achIcon = emptyAchievement;
			}
			//FIRST IMAGE
			y += gp.tileSize/2;
			g2.drawRect(35, y, gp.tileSize*9, gp.tileSize*2);
			g2.drawImage(achIcon, x-79, y, gp.tileSize*2, gp.tileSize*2, null);
			
			
			if(gp.player.hasAchies[2] == true) {
				achIcon = behelitAchievement;
			} else {
				achIcon = emptyAchievement;
			}
			//SECOND IMAGE
			y += gp.tileSize*2.5;
			g2.drawRect(35, y, gp.tileSize*9, gp.tileSize*2);
			g2.drawImage(achIcon, x-79, y, gp.tileSize*2, gp.tileSize*2, null);
			
			if(gp.player.hasAchies[1] == true) {
				achIcon = surv1MinAchievement;
			} else {
				achIcon = emptyAchievement;
			}
			//THIRD IMAGE
			y += gp.tileSize*2.5;
			g2.drawRect(35, y, gp.tileSize*9, gp.tileSize*2);
			g2.drawImage(achIcon, x-79, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//TEXT
			x += 30;
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));
			g2.setColor(new Color(170,68,225));
			//FIRST
			g2.drawString(achText[0], x, gp.tileSize*3-35);
			//SECOND
			g2.drawString(achText[2], x, gp.tileSize*5 + gp.tileSize/2-35);
			//THIR
			g2.drawString(achText[1], x, gp.tileSize*8-35);
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
			g2.setColor(Color.white);
			//FIRST
			g2.drawString("Kill 20 enemies.", x, gp.tileSize*3);
			//SECOND
			g2.drawString("Collect the Egg of the King.", x, gp.tileSize*5 + gp.tileSize/2);
			//THIR
			g2.drawString("Survive 1 minute.", x, gp.tileSize*8);
			
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
			g2.setColor(Color.orange);
			text = "BACK";
			x = getXCenterText(text) + gp.tileSize*4;
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	
	public void drawPauseScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(60F));
		String text = "PAUSED";
		int x = getXCenterText(text);
		int y = gp.screenHeight/2 - gp.tileSize*2;
		
		g2.drawString(text, x, y);
		
	}	
		
	public int getXCenterText(String text) {
		int textLenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - textLenght/2;
		return x;
	}
}

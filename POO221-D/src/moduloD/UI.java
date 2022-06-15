package moduloD;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	BufferedImage emptyAchievement;
	BufferedImage sMarkImage1 , sMarkImage2, sMarkImage3, sMarkImage4;
	BufferedImage fullHeart, emptyHeart, halfHeart;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;
	
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
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
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
			if (gameFinished == true) {
				String text;
				int x;
				int y;
				
				//MENSAGEM SEM DETAQUE
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
				g2.setColor(Color.white);
				text = "You beat Nosferatu Zodd!";
				x = getXCenterText(text);
				y = gp.screenHeight/2 - (gp.tileSize*2);
				g2.drawString(text, x, y);
				
				//TEMPO
				text = "Time: "+dFormat.format(playTime)+"s!";
				x = getXCenterText(text);
				y = gp.screenHeight/2 - (gp.tileSize*1);
				g2.drawString(text, x, y);
				
				//MENSAGEM COM DESTAQUE
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
				g2.setColor(Color.yellow);
				text = "Congratulations!";
				x = getXCenterText(text);
				y = gp.screenHeight/2 - (gp.tileSize*3);
				g2.drawString(text, x, y);
				
				//TODO MOSTRAR PONTUACAO
				
				gp.gameThread = null;
			} else {
				drawPlayerLife();
				
				//FPS
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 27F));
				g2.setColor(Color.white);
				g2.drawString("FPS: "+gp.FPS, gp.tileSize*9 - gp.tileSize/2, 50);
				
				//TIME
				playTime += (double)1/60;
				drawPlayTime();
				
				//MESSAGE
				if(messageOn == true) {
					
					if(message.length() == 43) {
						g2.setColor(Color.yellow);
					} else {
						g2.setFont(g2.getFont().deriveFont(24F));
					}
					g2.drawString(message, 25, 450);
					
					messageCounter++;
					
					if(messageCounter > 180) {
						messageCounter = 0;
						messageOn = false;
					}
				}
			}
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
			drawPlayerLife();
			drawPlayTime();
		}
	}
	public void drawPlayTime() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 27F));
		String text = ""+dFormat.format(playTime);
		g2.drawString(text+" s", getXCenterText(text), gp.tileSize*1);
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
			String text = "DUNGEON";
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
			
			//FIRST IMAGE
			y += gp.tileSize/2;
			g2.drawRect(35, y, gp.tileSize*9, gp.tileSize*2);
			g2.drawImage(emptyAchievement, x-79, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//SECOND IMAGE
			y += gp.tileSize*2.5;
			g2.drawRect(35, y, gp.tileSize*9, gp.tileSize*2);
			g2.drawImage(emptyAchievement, x-79, y, gp.tileSize*2, gp.tileSize*2, null);
			
			
			//THIRD IMAGE
			y += gp.tileSize*2.5;
			g2.drawRect(35, y, gp.tileSize*9, gp.tileSize*2);
			g2.drawImage(emptyAchievement, x-79, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//TEXT TODO MELHORAR
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
			g2.setColor(Color.white);
			x += 30;
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

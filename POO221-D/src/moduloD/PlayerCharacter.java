package moduloD;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerCharacter extends Entity{
	
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	int hasBehelit = 0;
	int standCounter = 0;
	//boots timer
	int bootCounter = 0;
	boolean hasBoots;
	
	public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.setKeyHandler(keyH);
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	
	public void setKeyHandler(KeyHandler kh) {
		this.keyH = kh;
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 14; //coordenada do mapa
		worldY = gp.tileSize * 4; //coordenada do mapa
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		down1 = setup("Guts2DFrenteDireita");
		down2 = setup("Guts2DFrenteEsquerda");
		left1 = setup("Guts2DEsquerda1");
		left2 = setup("Guts2DEsquerda2");
		right1 = setup("Guts2DDireita1");
		right2 = setup("Guts2DDireita2");
		up1 = setup("Guts2DCimaDireita");
		up2 = setup("Guts2DCimaEsquerda");
	}
	
	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true) {
			//atualização de movimento
			if(keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";	
			} else if (keyH.rightPressed == true) {
				direction = "right";	
			} else if (keyH.leftPressed == true) {
				direction = "left";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(collisionOn == false){
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "right":
					worldX += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		} else {
			standCounter++;
			
			if(standCounter == 10) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		if(hasBoots == true) {
			bootCounter++;
			if(bootCounter > 600) {
				speed -= 1;
				hasBoots = false;
				bootCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case"Behelit":
				hasBehelit++;
				gp.playSE(1);
				gp.obj[i] = null;
				gp.ui.showMessage("A conexão entre os mundos foi restaurada...");
				break;
			case "Boots":
				if(hasBoots == false) {
					speed += 1;
					hasBoots = true;
					gp.obj[i] = null;
					gp.ui.showMessage("Um novo par de botas...");
				}
				break;
			case "ZoddHorn":
				gp.obj[2] = null;
				gp.ui.showMessage("Congratulations! You beat Nosferatu Zodd!");
				gp.ui.gameFinished = true;
				gp.stopMusic();
				//gp.playSE(x); TODO adicionar SE para completar o jogo
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction) {
		//TODO os cases e retirar default
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		}
		
		int x = screenX;
		int y = screenY;
		
		if(screenX > worldX) {
			x = worldX;
		}
		if(screenY > worldY) {
			y = worldY;
		}
		int rightOffset = gp.screenWidth - screenX;
		if(rightOffset > gp.worldWidth - worldX) {
			x = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - screenY;
		if(bottomOffset > gp.worldHeight - worldY) {
			y = gp.screenHeight - (gp.worldHeight - worldY);
		}
		
		g2.drawImage(image, x, y, null);
	}
}

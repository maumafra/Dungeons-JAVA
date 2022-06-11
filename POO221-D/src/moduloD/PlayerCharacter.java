package moduloD;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerCharacter extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public PlayerCharacter(GamePanel gp, KeyHandler keyH) {
		this.setGamePanel(gp);
		this.setKeyHandler(keyH);
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setGamePanel(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setKeyHandler(KeyHandler kh) {
		this.keyH = kh;
	}
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			//TODO o resto das imagens
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DFrenteDireita.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DFrenteEsquerda.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DEsquerda1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DEsquerda2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DDireita1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DDireita2.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DCimaDireita.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Guts2DCimaEsquerda.png"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true) {
			//atualização de movimento
			if(keyH.upPressed == true) {
				direction = "up";
				y -= speed;
			} else if (keyH.downPressed == true) {
				direction = "down";
				y += speed;
			} else if (keyH.rightPressed == true) {
				direction = "right";
				x += speed;
			} else if (keyH.leftPressed == true) {
				direction = "left";
				x -= speed;
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
		g2.drawImage(image, x, y, gp.tamanhoBloco, gp.tamanhoBloco, null);
	}
}

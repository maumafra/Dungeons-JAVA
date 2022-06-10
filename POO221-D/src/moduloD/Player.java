package moduloD;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
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
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void update() {
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
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction) {
		//TODO os cases e retirar default
		case "down":
			image = down1;
			break;
			
		default:
			image = down1;
			break;
		}
		g2.drawImage(image, x, y, gp.tamanhoBloco, gp.tamanhoBloco, null);
	}
}

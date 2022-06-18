package moduloD;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Entity {

	public GamePanel gp;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackDown1, attackDown2, attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public String dialogues[] = new String[20];
	public int dialogueIndex = 0;
	public BufferedImage image, image2, image3, image4;
	public Random random;
	
	
	//STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	public boolean collision = false;
	public boolean pickup = false;
	public boolean invincible = false;
	public boolean contactPlayer = false;
	public boolean collisionOn = false;
	public boolean attacking = false;
	
	//COUNTER
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	public int spriteCounter = 0;
	
	// CHARACTER STATUS
	public int type; // 0 - player, 1 - npc, 2 - enemy
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {
		
	}
	
	public void update() {
		setAction(); //incluir IA caso queira botar NPC
		
		collisionOn = false;
		
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.enem);
		contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(contactPlayer == true && this.type == 2) {
			if(gp.player.invincible == false) {
				gp.player.life--;
				gp.player.invincible = true;
			}
		}
		
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
		
		//CONTADOR INVENCIBILIDADE
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 40){
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		//parar de mover a camera ao chegar no limite do mapa
		if(gp.player.screenX > gp.player.worldX) {
			screenX = worldX;
		}
		if(gp.player.screenY > gp.player.worldY) {
			screenY = worldY;
		}
		int rightOffset = gp.screenWidth - gp.player.screenX;
		if(rightOffset > gp.worldWidth - gp.player.worldX) {
			screenX = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - gp.player.screenY;
		if(bottomOffset > gp.worldHeight - gp.player.worldY) {
			screenY = gp.screenHeight - (gp.worldHeight - worldY);
		}
		
		//s� carregar os tiles que est�o dentro dos limites da tela
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
		&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
		&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
		&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
			
			switch(direction) {
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
			if(pickup == true) {
				spriteCounter++;
				if(spriteCounter > 24) {
					screenY += 4;
				} 
				if (spriteCounter > 48) {
					screenY -= 4;
					spriteCounter = 0;
				}
			}
			
			// CHARACTER BLINK AFTER TAKING DAMAGE
			if(invincible == true) {
				if(invincibleCounter <= 10 || invincibleCounter > 30) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				}
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			// CHARACTER BLINK AFTER TAKING DAMAGE
			if(invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
			
		//parar de mover a camera ao chegar no limite do mapa
		} else if (gp.player.screenX > gp.player.worldX 
		|| gp.player.screenY > gp.player.worldY
		|| rightOffset > gp.worldWidth - gp.player.worldX
		|| bottomOffset > gp.worldHeight - gp.player.worldY) {
			
			switch(direction) {
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
			
			if(pickup == true) {
				spriteCounter++;
				if(spriteCounter > 24) {
					screenY += 4;
				} 
				if (spriteCounter > 48) {
					screenY -= 4;
					spriteCounter = 0;
				}
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}

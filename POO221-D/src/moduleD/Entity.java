package moduleD;

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
	public boolean invincible = false;
	public boolean contactPlayer = false;
	public boolean collisionOn = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public boolean onPath = false;
	public boolean knockBack = false;
	
	//COUNTER
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	public int shotAvailableCounter = 0;
	public int spriteCounter = 0;
	public int dyingCounter = 0;
	public int knockBackCounter = 0;
	
	// CHARACTER STATUS
	public String name;
	public int defaultSpeed;
	public int speed;
	public int maxLife;
	public int life;
	public int maxAmmo;
	public int ammo;
	public int attack = 1;
	public Projectile projectile;
	
	//ITEM ATTRIBUTES
	public String description = "";
	public int useCost;
	
	//TYPE
	public int type; // 0 - player, 1 - npc, 2 - enemy
	public final int typePlayer = 0;
	public final int typeNPC = 1;
	public final int typeEnemy = 2;
	public final int typeConsumable = 3;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public boolean use(Entity entity) {
		return true;
	}

	public void checkDrop() {
		
	}
	
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i<gp.obj.length; i++) {
			if(droppedItem.name.equals("ZoddHorn") || droppedItem.name.equals("Behelit")) {
				System.out.println("DROPPED ESPECIAL: "+droppedItem.name);
				gp.obj[0] = droppedItem;
				gp.obj[0].worldX = worldX;
				gp.obj[0].worldY = worldY;
			}
			else if(gp.obj[i] == null) {
				System.out.println("DROPPED: "+droppedItem.name);
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}
	
	public void setAction() {
		
	}
	
	public void checkCollision() {
		collisionOn = false;
		
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		//gp.cChecker.checkEntity(this, gp.enem);
		contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(contactPlayer == true && this.type == typeEnemy) {
			damagePlayer(attack);
		}
	}
	
	public void update() {
		
		if(knockBack == true) {
			checkCollision();
			
			if(collisionOn == true) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			} 
			else if (collisionOn == false) {
				switch(gp.player.direction) {
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
				
				knockBackCounter ++;
				if(knockBackCounter == 3) {
					knockBackCounter = 0;
					knockBack = false;
					speed = defaultSpeed;
				}
			}
		}
		else {
			setAction(); //incluir IA caso queira botar NPC
			checkCollision();
			
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
		
		if(type == typeEnemy) {
			onPath = true;
		}
	}
	
	public void damagePlayer(int attack) {
		if(gp.player.invincible == false) {
			gp.player.score -= attack*5;
			if(gp.player.score < 0) {
				gp.player.score = 0;
			}
			if(gp.player.hasShield == false) {
				gp.player.life -= attack;
			} else {
				gp.player.hasShield = false;
			}
			gp.player.invincible = true;
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
		
		//só carregar os tiles que estão dentro dos limites da tela
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
			if(type == typeConsumable) {
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
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);
			
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
			
			if(type == typeConsumable) {
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
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);
			
			// CHARACTER BLINK AFTER TAKING DAMAGE
			if(invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5;
		
		if(dyingCounter <= i) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > i && dyingCounter <= i*2) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > i*3 && dyingCounter <= i*4) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > i*5 && dyingCounter <= i*6) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > i*6 && dyingCounter <= i*7) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > i*7){
			alive = false;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
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
	
	public void searchPath(int goalCol, int goalRow) {
		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gp.pFinder.search() == true) {
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				if(enLeftX > nextX) {
					direction = "left";
				}
				if(enLeftX < nextX) {
					direction = "right";
				}
			}
			else if (enTopY > nextY && enLeftX > nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if (enTopY > nextY && enLeftX < nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			
			int nextCol = gp.pFinder.pathList.get(0).col;
			int nextRow = gp.pFinder.pathList.get(0).row;
			
			if(nextCol == goalCol && nextRow == goalRow) {
				onPath = false;
			}
		}
	}
}

package moduloD;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
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
	int attackNum = 1;
	int attackCounter = 0;
	//BOOTS
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
		getPlayerAttackImage();
	}
	
	
	public void setKeyHandler(KeyHandler kh) {
		this.keyH = kh;
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 14; //coordenada do mapa
		worldY = gp.tileSize * 4; //coordenada do mapa
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {
		down1 = setup("/player/Guts2DFrenteDireita", gp.tileSize, gp.tileSize);
		down2 = setup("/player/Guts2DFrenteEsquerda", gp.tileSize, gp.tileSize);
		left1 = setup("/player/Guts2DEsquerda1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/Guts2DEsquerda2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/Guts2DDireita1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/Guts2DDireita2", gp.tileSize, gp.tileSize);
		up1 = setup("/player/Guts2DCimaDireita", gp.tileSize, gp.tileSize);
		up2 = setup("/player/Guts2DCimaEsquerda", gp.tileSize, gp.tileSize);
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = setup("/player/AttackUp01", gp.tileSize*5, gp.tileSize*3);
		attackUp2 = setup("/player/AttackUp02", gp.tileSize*5, gp.tileSize*3);
		attackDown1 = setup("/player/AttackDown01", gp.tileSize*5, gp.tileSize*3);
		attackDown2 = setup("/player/AttackDown02", gp.tileSize*5, gp.tileSize*3);
		attackLeft1 = setup("/player/AttackLeft01", gp.tileSize*3, gp.tileSize*5);
		attackLeft2 = setup("/player/AttackLeft02", gp.tileSize*3, gp.tileSize*5);
		attackRight1 = setup("/player/AttackRight01", gp.tileSize*3, gp.tileSize*5);
		attackRight2 = setup("/player/AttackRight02", gp.tileSize*3, gp.tileSize*5);
	}
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		} else if (keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true 
				|| keyH.enterPressed == true) {
			//atualiza��o de movimento
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
			
			//CHECK NPC COLLISION
			//int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(999);
			
			//CHECK ENEMIES COLLISION
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enem);
			contactMonster(enemyIndex);

			
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
		//CONTADOR INVENCIBILIDADE
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60){
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		//CONTADOR DO SPEED DA BOTA
		if(hasBoots == true) {
			bootCounter++;
			if(bootCounter > 600) {
				speed -= 1;
				hasBoots = false;
				bootCounter = 0;
			}
		}
	}
	
	public void attacking() {
		attackCounter++;
		
		if(attackCounter <= 20) {
			attackNum = 1;
		}
		if(attackCounter > 20 && attackCounter < 50) {
			attackNum = 2;
			
			//SAVE CURRENT VALUES
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//ADJUST PLAYER VALUES
			switch(direction) {
			case "up":
				attackArea.height = gp.tileSize*3;
				attackArea.width = gp.tileSize*5;
				worldY -= gp.tileSize*2.3;
				worldX -= gp.tileSize*2.1;
				break;
			case "down":
				attackArea.height = gp.tileSize*3;
				attackArea.width = gp.tileSize*5;
				worldY -= gp.tileSize/2 - 5;
				worldX -= gp.tileSize*2.1;
				break;
			case "left":
				attackArea.height = gp.tileSize*5;
				attackArea.width = gp.tileSize*3;
				worldY -= attackArea.height - gp.tileSize*2.6;
				worldX -= attackArea.width - gp.tileSize*1;
				break;
			case "right":
				attackArea.height = gp.tileSize*5;
				attackArea.width = gp.tileSize*3;
				worldY -= gp.tileSize*2.4;
				worldX -= gp.tileSize/3;
				break;
			}
			//solidArea gets attackArea values
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			//check enemy collision 
			//int enemyIndex = gp.cChecker.checkEntity(this, gp.enem);
			int enemyIndex[] = gp.cChecker.checkMultipleEntities(this, gp.enem);
			for (int i = 0; i<enemyIndex.length; i++) {
				damageEnemy(enemyIndex[i]);
			}
			//damageEnemy(enemyIndex);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		
		if(attackCounter > 50) {
			attackNum = 1;
			attackCounter = 0;
			attacking = false;
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
				gp.ui.showMessage("A conex�o entre os mundos foi restaurada...");
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
				//gp.playSE(1); TODO adicionar SE para completar o jogo
				break;
			}
		}
	}
	
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			if(i != 999) {
				
			} else {
				attacking = true;
			}
		}	
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			if(invincible == false) {
				life--;
				invincible = true;
			}
		}
	}

	
	public void damageEnemy(int i) {
		if(i != 999) {
			if(gp.enem[i] != null && gp.enem[i].invincible == false ) {
				gp.enem[i].life -= 4;
				gp.enem[i].invincible = true;
				
				if(gp.enem[i].life <= 0) {
					gp.enem[i] = null;
				}
			}
		}
	}
	
	public void draw(Graphics2D g2) {	
		
		BufferedImage image = null;
		
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
			x = gp.screenWidth - (gp.worldWidth - worldX) - (screenX - x);
		}
		int bottomOffset = gp.screenHeight - screenY;
		if(bottomOffset > gp.worldHeight - worldY) {
			y = gp.screenHeight - (gp.worldHeight - worldY) - (screenY - y);
		}
		
		switch(direction) {
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
			} else {
				if(attackNum == 1) {
					image = attackDown1;
					x -= 96;
				}
				if(attackNum == 2) {
					image = attackDown2;
					x -= 96;
				}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
			} else {
				if(attackNum == 1) {
					image = attackLeft1;
					y -= 96;
					x -= 96;
				}
				if(attackNum == 2) {
					image = attackLeft2;
					y -= 96;
					x -= 96;
				}
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
			} else {
				if(attackNum == 1) {
					image = attackRight1;
					y -= 96;
				}
				if(attackNum == 2) {
					image = attackRight2;
					y -= 96;
				}
			}
			break;
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
			} else {
				if(attackNum == 1) {
					image = attackUp1;
					y -= 96;
					x -= 96;
				}
				if(attackNum == 2) {
					image = attackUp2;
					y -= 96;
					x -= 96;
				}
			}
			break;
		}
		
		// CHARACTER BLINK AFTER TAKING DAMAGE
		if(invincible == true) {
			if(invincibleCounter <= 20 || invincibleCounter > 40) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			}
		}

		g2.drawImage(image, x, y, null);
		
		// RESET CHARACTER BLINK TO NOT MIX WITH OTHER ENTITIES
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		// DEBUG	
		if(gp.keyH.checkDrawTime == true) {
			g2.setColor(Color.yellow);
			g2.drawRect(x + 8, y + 16, solidArea.width, solidArea.height);
			
			switch(direction) {
			case "up": y = worldY - attackArea.height + gp.tileSize; break;
			case "down": y = worldY; break; 
			case "left": x = screenX - attackArea.width + gp.tileSize;  break;
			case "right": x = screenX; break;
			}
		
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1));
			g2.drawRect(x, y, attackArea.width, attackArea.height);
		}	
			
	}
}

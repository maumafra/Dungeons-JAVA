package moduloD;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileManager {
	
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		setGamePanel(gp);
		tile = new Tile[20];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImage() {
		
		setup(0, "BrickPurple", true);
		setup(1, "Dirt", false);
		setup(2, "DirtPurple", false);
		setup(3, "Torch", true);
		setup(4, "Cage", true);
		setup(5, "BrickPurpleBorder", true);
		setup(6, "BrickPurpleBorderLB", true);
		setup(7, "BrickPurpleBorderRB", true);
		setup(8, "BrickPurpleBorderRT", true);
		setup(9, "DirtPurpleGrass", false);
		setup(10, "DirtPurpleGrassBlood", false);
		setup(11, "DirtPurpleRock", false);
		setup(12, "DirtPurpleRockBlood", false);
		setup(13, "BrickPurpleRight", true);
		setup(14, "BrickPurpleLeft", true);
		setup(15, "BrickPurpleBottom", true);

	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String mapPath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {//map01 29 blocos
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;

		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
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
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			} else if (gp.player.screenX > gp.player.worldX 
			|| gp.player.screenY > gp.player.worldY
			|| rightOffset > gp.worldWidth - gp.player.worldX
			|| bottomOffset > gp.worldHeight - gp.player.worldY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}

			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void setGamePanel(GamePanel gp) {
		this.gp = gp;
	}
}

/* IMPORTANTE: Esse projeto se baseia em uma playlist de vídeos postada na Youtube pelo canal
 * RyiSnow. Em seus vídeos, Ryi ensina as bases para criar um jogo 2D, enquanto ele faz seu
 * projeto. Nosso objetivo ao usar os vídeos dele como fonte é conseguir entender a lógica de
 * funcionalidades como: leitura de comandos, implementar os "assets", movimento de cï¿½mera,
 * movimento sob um mapa, etc. para então implementar ao nosso projeto, que terá seu próprio 
 * enredo, personagens, funcionalidades, cálculo de pontuação, inimigos etc.
 * 
 * REFERÊNCIA: How to Make a 2D Game in Java. RyiSnow. 
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4
 */

package moduloD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JPanel;

import gameShared.Console;
import gameShared.Player;

public class GamePanel extends JPanel implements Runnable{
	
	//CONFIGURAï¿½ï¿½ES DE TELA
	final int tileSizeDefault = 16; // bloco de 16x16 pixels - padrï¿½o de tamanho 2D
	final int scale = 3; 
	 //16x16 ï¿½ muito pequeno pra resoluï¿½ï¿½o dos monitores atuais, por isso precisamos ampliar o
	 //bloco padrï¿½o atravï¿½s de uma escala
	final int tileSize = tileSizeDefault * scale; //tamanho do bloco real, apï¿½s conversï¿½o
	//resultado de bloco 48x48
	final int maxScreenCol = 11; // 500/48 = 10.4
	final int maxScreenRow = 10; // 480/48 = 10
	final int screenWidth = 500; //o certo seria: 11*48, mas isso foge das especif. do trabalho
	final int screenHeight = 480; //10*48 = 480
	
	// WORLD SETTINGS
	public final int maxWorldCol = 29; //DEPENDE DO MAPA!! Nesse caso sï¿½o as configuraï¿½ï¿½es do map01
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS - Frames Per Second
	public int FPS = 60;
	
	//SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public Sound music = new Sound(this);
	public Sound se = new Sound(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public Config gameConfig = new Config(this);
	public Thread gameThread; //precisa do implements Runnable que gera o mï¿½todo Run
	public int activations;
	public boolean sysHasAudio = true;
	
	//CONSOLE INTEGRATION
	public Player actualPlayer;
	public Console console;
	public HashMap<String, Integer> players = new HashMap<>();
	
	//ENTITY AND OBJECT
	public PlayerCharacter player = new PlayerCharacter(this, keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];//TODO se quiser adicionar os NPCs futuramente
	public Entity enem[] = new Entity[30];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> entityList = new ArrayList<>(); //arraylist para dar prioridade de desenho para a entidade de maior y
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int optionsState = 3;
	public final int gameOverState = 4;
	public final int gameWinState = 5;
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); //vai escutar o evento da tecla e mandar pra nossa classe
		this.setFocusable(true); //agora o panel estï¿½ "focado" para receber o input
	}
	
	public void restartGame() {
		player.setDefaultValues();
		player.invincible = false;
		ui.messageOn = false;
		aSetter.setEnemies();
		aSetter.setObject();
		if(sysHasAudio == true) {
			playMusic(0);
		}
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setEnemies();
		try {
			playMusic(0);
		} catch(NullPointerException e) {
			sysHasAudio = false;
		}
		
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/* Método run: vai definir em que frequência o loop de update e drawComponent vãoo ser executados,
	 * nesse caso definimos 60 frames/s, e printamos o valor do FPS no console para ver o quanto que
	 * ele alcança a cada segundo.
	 */
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //divide 1s (1x10^9 ns) pela frequencia para conseguir o intervalo
		//1/60 = 0.016666... segundos
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				// 1 atualiza as infrmações do jogo
				update();
				// 2 desenha os componentes na tela
				repaint(); //para chamar o método paintComponent, é necessário chamar esse método
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: "+drawCount);
				System.out.println(activations);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		if(gameState == playState) {
			//PLAYER
			player.update();
			
			//ENEMIES
			for(int i = 0; i < enem.length; i++) {
				if(enem[i] != null) {
					if(enem[i].alive == true && enem[i].dying == false) {
						enem[i].update();
					}
					if(enem[i].alive == false) {
						enem[i].checkDrop();
						enem[i] = null;
					}
					
				}
			}
			
			//PROJECTILES
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
					
				}
			}
		}
		if(gameState == pauseState) {
			//nada
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; //Subclasse de Graphics que dï¿½ mais controle ao usuï¿½rio sobre elementos 2D
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			// TILE
			tileM.draw(g2);//Primeiro os tiles, para  as entidades/objetos darem overlay
			
			//ADD ENTITIES TO ARRAYLIST
			entityList.add(player);
			/*TODO caso implementar NPCs
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}*/
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i = 0; i < enem.length; i++) {
				if(enem[i] != null) {
					entityList.add(enem[i]);
				}
			}
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			//SORT
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			
			//DRAW ENTITIES
			for(Entity e : entityList) {
				e.draw(g2);
			}
			
			//CLEARING ENTITY LIST
			entityList.clear();
			
			//UI
			ui.draw(g2);//Ultima layer
			
			
		}
		//DEBUG
		if(keyH.checkDrawTime == true && gameState == playState) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 27F));
			g2.setColor(Color.white);
			g2.drawString("FPS: "+FPS, tileSize*9 - tileSize/2, 50);
			g2.drawString("Activations: "+activations, 10, 340);
			g2.drawString("Draw Time: "+passed, 10, 360);
			//System.out.println("Draw t: "+passed);
			g2.drawString("Boot Counter: "+player.bootCounter, 10, 380);
			g2.drawString("Speed: "+player.speed, 10, 400);
			g2.drawString("Invincible: "+player.invincibleCounter, 10, 420);
		
			g2.dispose();
		}
	}
	
	public void playMusic (int i) {
		if(sysHasAudio) {
			music.setFile(i);
			music.play();
			music.loop();
		}
	}
	
	public void stopMusic() {
		if(sysHasAudio) {
			music.stop();
		}
	}
	
	public void playSE(int i) {
		if(sysHasAudio) {
			se.setFile(i);
			se.play();
		}
	}
	
	public void incActivations() {
		activations++;
	}
	
	public void addPlayerAndScore(Player p1, int score) {
		if(p1 != null) {
			this.actualPlayer = p1;
			players.put(p1.getNickname(), score);
		}
	}
}

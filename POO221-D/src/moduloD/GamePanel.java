/* IMPORTANTE: Esse projeto se baseia em uma playlist de v�deos postada na Youtube pelo canal
 * RyiSnow. Em seus v�deos, Ryi ensina as bases para criar um jogo 2D, enquanto ele faz seu
 * projeto. Nosso objetivo ao usar os v�deos dele como fonte � conseguir entender a l�gica de
 * funcionalidades como: leitura de comandos, implementar os "assets", movimento de c�mera,
 * movimento sob um mapa, etc. para ent�o implementar ao nosso projeto, que ter� seu pr�prio 
 * enredo, personagens, funcionalidades, c�lculo de pontua��o, inimigos etc.
 * 
 * REFER�NCIA: How to Make a 2D Game in Java. RyiSnow. 
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4
 */

package moduloD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	//CONFIGURA��ES DE TELA
	final int tileSizeDefault = 16; // bloco de 16x16 pixels - padr�o de tamanho 2D
	final int scale = 3; 
	 //16x16 � muito pequeno pra resolu��o dos monitores atuais, por isso precisamos ampliar o
	 //bloco padr�o atrav�s de uma escala
	final int tileSize = tileSizeDefault * scale; //tamanho do bloco real, ap�s convers�o
	//resultado de bloco 48x48
	final int maxScreenCol = 11; // 500/48 = 10.4
	final int maxScreenRow = 10; // 480/48 = 10
	final int screenWidth = 500; //o certo seria: 11*48, mas isso foge das especif. do trabalho
	final int screenHeight = 480; //10*48 = 480
	
	// WORLD SETTINGS
	public final int maxWorldCol = 29; //DEPENDE DO MAPA!! Nesse caso s�o as configura��es do map01
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS - Frames Per Second
	int FPS = 60;
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	CollisionChecker cChecker = new CollisionChecker(this);
	AssetSetter aSetter = new AssetSetter(this);
	UI ui = new UI(this);
	Thread gameThread; //precisa do implements Runnable que gera o m�todo Run
	
	//ENTITY AND OBJECT
	PlayerCharacter player = new PlayerCharacter(this, keyH);
	Entity obj[] = new Entity[10];
	Entity npc[] = new Entity[10];//TODO se quiser adicionar os NPCs futuramente
	Entity enem[] = new Entity[30];
	ArrayList<Entity> entityList = new ArrayList<>(); //arraylist para dar prioridade de desenho para a entidade de maior y
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); //vai escutar o evento da tecla e mandar pra nossa classe
		this.setFocusable(true); //agora o panel est� "focado" para receber o input
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setEnemies();
		playMusic(0);
		
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/* M�todo run: vai definir em que frequ�ncia o loop de update e drawComponent v�o ser executados,
	 * nesse caso definimos 60 frames/s, e printamos o valor do FPS no console para ver o quanto que
	 * ele alcan�a a cada segundo.
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
				// 1 atualiza as infrma��es do jogo
				update();
				// 2 desenha os componentes na tela
				repaint(); //para chamar o m�todo paintComponent, � necess�rio chamar esse m�todo
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: "+drawCount);
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
					enem[i].update();
				}
			}
		}
		if(gameState == pauseState) {
			//nada
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; //Subclasse de Graphics que d� mais controle ao usu�rio sobre elementos 2D
		
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
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: "+passed, 10, 380);
			//System.out.println("Draw t: "+passed);
			g2.drawString("Boot Counter: "+player.bootCounter, 10, 400);
			g2.drawString("Speed: "+player.speed, 10, 420);
			g2.drawString("Invincible: "+player.invincibleCounter, 10, 440);
		
			g2.dispose();
		}
	}
	
	public void playMusic (int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}

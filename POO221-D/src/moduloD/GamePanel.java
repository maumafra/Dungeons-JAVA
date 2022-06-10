/* IMPORTANTE: Esse projeto se baseia em uma playlist de vídeos postada na Youtube pelo canal
 * RyiSnow. Em seus vídeos, Ryi ensina as bases para criar um jogo 2D, enquanto ele faz seu
 * projeto. Nosso objetivo ao usar os vídeos dele como fonte é conseguir entender a lógica de
 * funcionalidades como: leitura de comandos, implementar os "assets", movimento de câmera,
 * movimento sob um mapa, etc. para então implementar ao nosso projeto, que terá seu próprio 
 * enredo, personagens, funcionalidades, cálculo de pontuação, inimigos etc.
 * 
 * REFERÊNCIA: How to Make a 2D Game in Java. RyiSnow. 
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4
 */

package moduloD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	//CONFIGURAÇÕES DE TELA
	final int tamanhoBlocoPadrao = 16; // bloco de 16x16 pixels - padrão de tamanho 2D
	final int escala = 3; 
	 //16x16 é muito pequeno pra resolução dos monitores atuais, por isso precisamos ampliar o
	 //bloco padrão através de uma escala
	
	final int tamanhoBloco = tamanhoBlocoPadrao * escala; //tamanho do bloco real, após conversão
	//resultado de bloco 48x48
	final int totalColTela = 11; // 500/48 = 10.4
	final int totalLinTela = 10; // 480/48 = 10
	final int comprimentoTela = 500; //o certo seria: 11*48, mas isso foge das especif. do trabalho
	final int alturaTela = 480; //10*48 = 480
	
	// FPS - Frames Per Second
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //precisa do implements Runnable que gera o método Run
	Player player = new Player(this, keyH);
	
	// Posição Inicial do Jogador
	int playerX = 100; // coordenada X
	int playerY = 100; // coordenada Y
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(comprimentoTela, alturaTela));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); //vai escutar o evento da tecla e mandar pra nossa classe
		this.setFocusable(true); //agora o panel está "focado" para receber o input
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/* Método run: vai definir em que frequência o loop de update e drawComponent vão ser executados,
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
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; //Subclasse de Graphics que dá mais controle ao usuário sobre elementos 2D
		
		player.draw(g2);
		
		g2.dispose();
	}
}

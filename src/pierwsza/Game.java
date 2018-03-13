package pierwsza;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * 
 * Klasa Game odpowiada min. za generowanie elementów graficznych, obslugê poziomow oraz ruch myszka i obsluge w menu
 */
public class Game extends Canvas implements Runnable, MouseListener, MouseMotionListener,ActionListener {
	private static final long serialVersionUID = -1725117739823536898L;
	public static final int WIDTH = 1000, HEIGHT = 700;
	private Thread watek;
	/**
	 * Zmienna wskazuj¹ca na dzia³anie gry
	 */
	private boolean dziala = false;
	/**
	 * Zmienna zwi¹zana z przewijaniem t³a
	 */
	public int pozycja = 0;
	/**
	 * Zmienna która opisuje aktualny etap gry
	 */
	public int poziom = 1;
	public int ktory = 1;
	/**
	 * Timer który jest wykorzystywany do pojawiania siê przeciwników
	 */
	public long timer2;
	/**
	 * Zmienna która oznacza zakoñczenie gry
	 */
	public int koniecgry=0;
	public int cos = 0;
	public int cos2 = 700;
	/**
	 * Zmienna oznaczaj¹ca bossa
	 */
	public boolean boss1 = false;
	private Handler handler;
	private Random r;
	Font czcionka = new Font ("Century Gothic Bold Italic", 1, 40);
	/**
	 * Timer s³u¿acy zamknieciu gry po okreslonym czasie
	 */
	Timer czas = new Timer(1, (ActionListener) this);

	private HUD hud;
	public boolean czymenu = true;
	/**
	 * Zmienne graficzne do poziomow 
	 */
	private BufferedImage img,img1,img2,img3,img4,img5,koniec,wygrana,menu;
	
	/**
	 * Zmienna reprezentujaca animacje menu
	 */
	private Image menutlo;

	
	public Game() {

		menutlo = Toolkit.getDefaultToolkit().createImage("menu.gif");
		try {
			img = ImageIO.read(new File("level1.jpg"));
			img1 = ImageIO.read(new File("level1.jpg"));
			img2 = ImageIO.read(new File("level2.png"));
			img3 = ImageIO.read(new File("level22.png"));
			koniec = ImageIO.read(new File("gameover.png"));
			wygrana = ImageIO.read(new File("victory.png"));
			img4 = ImageIO.read(new File("level3.jpg"));
			img5 = ImageIO.read(new File("level33.jpg"));
			menu = ImageIO.read(new File("menu2.png"));
		} catch (IOException e) {
		}

		GameObject gracz = new Player(468, 636, ID.Player);
		handler = new Handler(hud, gracz,this);
		this.addKeyListener(new KeyInput(handler));
		addMouseListener(this);
		new Okno(WIDTH, HEIGHT, "Podniebne Potyczki", this);
		hud = new HUD();
		r = new Random();

		// handler.addObject(new Player(468,636,ID.Player));
	}

	/**
	 * Tworzy nowy w¹tek gry 
	 */
	public synchronized void start() {
		watek = new Thread(this);
		watek.start();
		dziala = true;
	}

	/**
	 * Konczy prace watku
	 */
	public synchronized void stop() {
		try {
			watek.join();
			dziala = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public void run() {
		

		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();// timer do tworzenia wrogow
		long timer1 = System.currentTimeMillis();// timer do ograniczenia fpsow
		int frames = 0;
		czas.setRepeats(false);
		czas.setInitialDelay(4000);

		
		while (dziala) {
			if (System.currentTimeMillis() - timer1 > 1000) {
				timer1 += 5;// dla 5 1000/5=200=maxfps
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				while (delta >= 1) {
					tick();
					delta--;
				}
				if (dziala)
					render();
				frames++;

				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
				if (czymenu == false) 
				{
					
					if (System.currentTimeMillis() - timer2 >= 1000 && boss1 == false && poziom == 1) 
					{
						timer2 += 700;// mniejsza liczba czesciej nowy statek
										// sie pojawia
						handler.addObject(new BasicEnemy(r.nextInt(WIDTH - 64), 0, ID.BasicEnemy));
						if (hud.wynik >= 1500 && hud.wynik <= 2500) {
							boss1 = true;
							poziom = 2;
						}
					}
					if (System.currentTimeMillis() - timer2 > 1000 && boss1 == true && poziom == 2
							&& handler.object.isEmpty()) {
						handler.addBoss(new Boss1Enemy(435, 10, ID.Boss1Enemy));
						poziom = 3;
						hud.boss1 = 1;
						boss1 = false;
					}
					if (System.currentTimeMillis() - timer2 > 1000 && boss1 == false && poziom == 3 && hud.BOSSHEALTH<=0) 
					{
						timer2 += 700;// mniejsza liczba czesciej nowy statek
										// sie pojawia
						handler.addObject(new BasicEnemy(r.nextInt(WIDTH - 64), 0, ID.BasicEnemy));
						if (hud.wynik >= 4500 && hud.wynik <= 5500) {
							boss1 = true;
							poziom = 4;
						}
					}
					if (System.currentTimeMillis() - timer2 > 1000 && boss1 == true && poziom == 4
							&& handler.object.isEmpty()) 
					{
						handler.addBoss(new Boss2Enemy(435, 10, ID.Boss1Enemy));
						poziom = 5;
						hud.boss1 = 1;
						handler.bosszyje=1;
						hud.BOSSHEALTH=100;
						boss1 = false;
					}
					if (System.currentTimeMillis() - timer2 > 1000 && boss1 == false && poziom == 5 && hud.BOSSHEALTH<=0) 
					{
						timer2 += 700;// mniejsza liczba czesciej nowy statek
										// sie pojawia
						handler.addObject(new BasicEnemy(r.nextInt(WIDTH - 64), 0, ID.BasicEnemy));

						if (hud.wynik >= 9000 && hud.wynik <= 10000) {
							boss1 = true;
							poziom = 6;
						}
					}
					if (System.currentTimeMillis() - timer2 > 1000 && boss1 == true && poziom == 6
							&& handler.object.isEmpty()) 
					{
						handler.addBoss(new Boss3Enemy(435, 10, ID.Boss1Enemy));
						koniecgry = 1;
						hud.boss1 = 1;
						handler.bosszyje=1;
						hud.BOSSHEALTH=100;
						boss1 = false;
					}
					if(koniecgry==1 && handler.boss.isEmpty())
					{
						czas.start();
					}
					if (hud.HEALTH == 0)
					{
						czas.start();

				}

				}

			}
		}
	}

	/**
	 * Inicjujê mechanikê najwa¿nejszych elementów gry
	 */
	private void tick() {
		handler.tick();
		hud.tick();
	}

	/**
	 * Generujê szatê graficzn¹ gry
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if (czymenu == false) {
			if (hud.poziom == 1) {
				g.drawImage(img, 0, -cos + pozycja, null);
				pozycja++;
				g.drawImage(img1, 0, -cos2 + pozycja, null);
			} else if (hud.poziom == 2) {
				g.drawImage(img2, 0, -cos + pozycja, null);
				pozycja++;
				g.drawImage(img3, 0, -cos2 + pozycja, null);

			}
			else {

				g.drawImage(img4, 0, -cos + pozycja, null);
				pozycja++;
				g.drawImage(img5, 0, -cos2 + pozycja, null);

			}

			if (pozycja == HEIGHT && ktory == 1) {
				cos = 700;
				pozycja = 0;
				cos2 = 0;
				ktory = 2;
			}
			if (pozycja == HEIGHT && ktory == 2) {
				cos = 0;
				pozycja = 0;
				cos2 = 700;
				ktory = 1;
			}
			handler.render(g);
			hud.render(g);
			if (hud.HEALTH == 0)
				
				g.drawImage(koniec, 250, 100, null);

		}
		if (czymenu == true) 
		{

			g.drawImage(menutlo, 0, 0, null);
			g.drawImage(menu, 0, 0, null);
			handler.object.clear();
			handler.bullety.clear();
			handler.mybullety.clear();
			handler.boss.clear();
		    handler.rakiety.clear();
		}
		if(koniecgry==1 && hud.BOSSHEALTH<=0 ){
			g.drawImage(wygrana, 100, 100, null); 
			g.setFont (czcionka);
			g.drawString("Osi¹gna³eœ wynik ="+hud.wynik, 250, 400);
		}

		bs.show();
	}

	/**
	 * 
	 * 
	 * Funkcja zapobiega wychodzeniu obiektów poza ekran
	 * 
	 */ 
	public static int clamp(int var, int min, int max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x, y;

		x = e.getX();
		y = e.getY();

		if (x >= 86 && x <= 193 && y <= 320 && y >= 294) {
			
			timer2=System.currentTimeMillis();
			czymenu = false;
			

		}
		if (x >= 68 && x <= 262 && y <= 429 && y >= 387) {

			System.exit(0);

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Pobiera aktualny czas systemowy
	 */
	public void ustawTimera() 
	{
	timer2=System.currentTimeMillis();

	}

	public static void main(String[] args) {
		new Game();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}

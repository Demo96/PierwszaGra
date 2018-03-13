package pierwsza;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

/**
 Klasa Handler odpowiada za ca³¹ rozgrywkê, poruszanie siê przeciwników, pocisków,
 renderowanie siê modeli wszystkich obiektów oraz
 sprawdza kolizje obiektów. 
 */
public class Handler implements ActionListener {
	/**
	 licznik do generowania pocisków 
	 */
	long timer = System.currentTimeMillis();
	/**
	 licznik do generowania rakiet bossow
	 */
	long timerrakiet = System.currentTimeMillis();
	/**
	 Obiekt klasy HUD, pozwalajacy na operowanie interfejsem(zdrowie, punkty) 
	 */
	public HUD hud;
	public int eksplozja = 0;
	/**
	 zmienna sprawdzaj¹ca czy nast¹pi³ wybuch przeciwnika 
	 */
	public int wybuchEnemy = 0;
	/**
	 zmienna sprawdzaj¹ca czy nast¹pi³ wybuch gracza
	 */
	public int wybuchPlayer = 0;
	/**
	 obiekt klasy Game
	 */
	public Game gra;
	/**
	 zmienna sprawdzaj¹ca czy nast¹pi³ wybuch bossa
	 */
	public int wybuchBossa = 0;
	/**
	 obiekt klasy Kolizja, pozwala na korzystanie z metody sprawdzKolizje
	 , ktora ulatwia sprawdzanie kolizji obiektow
	 */
	public Kolizje kolizja;
	Timer czas = new Timer(1, (ActionListener) this);
	/**
	 ogranicznik strzalow 
	 */
	public int ograniczeniestrzalow = 20;
	/**
	 obiekt zapamietuje poszczegolne obiekty z list 
	 */
	public GameObject pamietnik; // zmienna zapamietuje samolot wroga do
	/**
	 obiekt przeciwnika do pomocy przy renderowaniu wybuchu
	 */
	public GameObject enemy;
	/**
	 obiekt bossa do pomocy przy renderowaniu wybuchu 
	 */
	public GameObject bossik;

	/**
	 lista obiektów przeciwników 
	 */
	LinkedList<GameObject> object = new LinkedList<GameObject>();// lista
																	// obiektow
																	// latajacych
	/**
	 lista obiektów pocisków przeciwnika
	 */
	LinkedList<GameObject> bullety = new LinkedList<GameObject>();// lista
																	// pociskow
																	// wroga
	/**
	 lista obiektów pocisków gracza 
	 */
	LinkedList<GameObject> mybullety = new LinkedList<GameObject>();// lista
																	// wlasnych
																	// pociskow
	/**
	 lista obiektów bossów
	 */
	LinkedList<GameObject> boss = new LinkedList<GameObject>();// lista
	// bossow
	/**
	 lista obiektów rakiet 
	 */
	LinkedList<GameObject> rakiety = new LinkedList<GameObject>();
	/**
	 obiekt gracza do pomocy przy renderowaniu wybuchu
	 */
	GameObject player;

	/**
	 zmienna sprawdzajaca czy gracz zyje 
	 */
	int czyzyje = 1;
	/**
	 zmienna sprawdzajaca czy boss zyje 
	 */
	int bosszyje = 1;
	/**
	 zmienna do generowania losowych liczb 
	 */
	Random r = new Random();

	/**
	 Konstruktor otrzymuje obiekt Klasy HUD, GameObject i Game od G³ównej klasy
	 */
	public Handler(HUD hudek, GameObject gracz, Game gra) {
		this.hud = hudek;
		this.player = gracz;
		this.gra = gra;				
	}



	public void actionPerformed(ActionEvent e) { 
		if (eksplozja == 1) {
			czyzyje = 0;
			wybuchPlayer = 0;
		}
		if (eksplozja == 2) {
			wybuchEnemy = 0;
		}
		if (eksplozja == 3) {
			wybuchBossa = 0;

		}
	}

	/**
	 Metoda odpowiada za sprawdzenie kolizji obiektów oraz usuwa odpowiednie
	 obiekty po wyjœciu poza ekran gry. Sprawdza kolizje gracza z innymi samolotami,
	 pociskami przeciwnika, bossem, rakietami bossa, pociskami gracza i modelami
	 przeciwnikow.
	  
	 */
	public void tick() {

		int a = 0;
		int z = 0;
		czas.setRepeats(false);
		czas.setInitialDelay(900);

		for (int i = 0; i < object.size(); i++) // petla sprawdza
												// wszystkieobiekty utworzone
		{
			GameObject tempObject = object.get(i);
			if (czyzyje == 1) {
				// sprawdzanie czy jest kolizja samolot-wrog
				if (kolizja.sprawdz(tempObject, player.getX(), player.getY(), 64, 39, 64, 39) == 1) {
				hud.HEALTH = hud.HEALTH - 10;
					hud.wynik = hud.wynik + 50;
					enemy = tempObject;
					wybuchEnemy = 1;
					// this.removeObject(tempObject);

					if (hud.HEALTH <= 0) {

						wybuchPlayer = 1;


					}

				}
			}
			if (tempObject.y > Game.HEIGHT) // usuwa samoloty
				this.removeObject(object.get(i)); // jak wyleca za ekran
			tempObject.tick();
		}

		for (int i = 0; i < bullety.size(); i++) // petla sprawdza wszystkie
													// bullety utworzone
		{
			GameObject tempObject = bullety.get(i);

			if (tempObject.getId() == ID.BasicBullet) {// sprawdzanie czy jest
														// kolizja
														// samolot-pocisk wroga
				if (kolizja.sprawdz(tempObject, player.getX(), player.getY(), 6, 16, 64, 39) == 1) {
					hud.HEALTH--;
					this.removeBullet(bullety.get(i));

					if (hud.HEALTH == 0) {

						wybuchPlayer = 1;

					}
				}
			}
			if (tempObject.y > Game.HEIGHT) // usuwa bullety
				this.removeBullet(bullety.get(i)); // jak wyleca za ekran
			tempObject.tick();
		}

		for (int j = 0; j < object.size(); j++) // petla nadrzedna sprawdza
												// wszystkich przeciwnikow i
												// zapisuje ich wspolrzedne
		{
			GameObject tempObject = object.get(j);
			pamietnik = tempObject;

			if (tempObject.getId() == ID.BasicEnemy) {
				a = tempObject.getX();
				z = tempObject.getY();
			}
			for (int i = 0; i < mybullety.size(); i++)// petla podrzedna
														// sprawdza wszystkie
														// moje kule i porownuje
														// z xy przeciwnika
			{
				tempObject = mybullety.get(i);
				if (kolizja.sprawdz(tempObject, a, z, 6, 16, 64, 39) == 1) {
					enemy = pamietnik;
					wybuchEnemy = 1;

					this.removeMyBullet(mybullety.get(i));
					hud.wynik = hud.wynik + 50;
				}
				if (tempObject.y < 0) // usuwa moje bullety
					this.removeMyBullet(mybullety.get(i)); // jak wyleca za
															// ekran

			}
		}

		for (int j = 0; j < boss.size(); j++) // petla nadrzedna sprawdza bossa
		{
			GameObject tempObject = boss.get(j);
			pamietnik = tempObject;
			a = tempObject.getX();
			z = tempObject.getY();
			if (bosszyje == 1) {
				for (int i = 0; i < mybullety.size(); i++)// petla podrzedna
				// sprawdza wszystkie
				// moje kule i porownuje
				// z xy bossa1
				{
					tempObject = mybullety.get(i);
					if (kolizja.sprawdz(tempObject, a, z, 6, 16, 164, 215) == 1) {
						hud.BOSSHEALTH = hud.BOSSHEALTH - 5;
						hud.wynik = hud.wynik + 50;
						if (hud.BOSSHEALTH <= 0) {
							gra.ustawTimera();
							bosszyje = 0;
							wybuchBossa = 1;
							removeBoss(boss.get(0));
							hud.poziom = hud.poziom+1;
							hud.boss1 = 0;
						}
						this.removeMyBullet(mybullety.get(i));
					}
				}
			}
		}
		for (int i = 0; i < boss.size(); i++) // petla sprawdza
		// wszystkieobiekty utworzone
		{
			GameObject tempObject = boss.get(i);
			bossik = tempObject;
			tempObject.tick();
			if (bosszyje == 1) {
				// sprawdzanie czy jest kolizja samolot-boss
				if (kolizja.sprawdz(tempObject, player.getX(), player.getY(), 164, 215, 64, 39) == 1) {
					hud.HEALTH = 0;
					if (hud.HEALTH <= 0) {
						wybuchPlayer = 1;
					}
				}
			}
		}
		for (int i = 0; i < rakiety.size(); i++) // petla sprawdza wszystkie
		// rakiety utworzone
		{
			Rakieta tempObject = (Rakieta) rakiety.get(i);
			if (tempObject.y < player.y+20)
				tempObject.osy = 1;
			else if (tempObject.y > player.y+20)
				tempObject.osy = -1;
			else
				tempObject.osy = 0;
			if (tempObject.x < player.x+32)
				tempObject.osx = 1;
			else if (tempObject.x > player.x+32)
				tempObject.osx = -1;
			else
				tempObject.osx = 0;
			tempObject.tick();
			if(tempObject.czaszycia<=0)
				this.removeRakieta(rakiety.get(i));


		}
		
		for (int i = 0; i < rakiety.size(); i++) // petla sprawdza
			// wszystkieobiekty utworzone
			{
			Rakieta tempObject = (Rakieta) rakiety.get(i);
					// sprawdzanie czy jest kolizja samolot-boss
					if (kolizja.sprawdz(tempObject, player.getX(), player.getY(), 6, 6, 64, 39) == 1) {
						hud.HEALTH = hud.HEALTH-2;
						this.removeRakieta(rakiety.get(i));
						if (hud.HEALTH <= 0) {
							wybuchPlayer = 1;
						}
					}
			}
		
		if (czyzyje == 1) {
			for (int i = 0; i < mybullety.size(); i++) {
				GameObject tempObject = mybullety.get(i);
				tempObject.tick();
			}

			player.tick();
			ograniczeniestrzalow++;

		}

	}

	/**
	 Metoda render przyjmuje obiekt g klasy Graphics pomocniczej do 
	 renderowanie obiektow. Metoda odpowiada za renderowanie modeli 
	 przeciwnikow i graczy. Sprawdza wszystkie listy obiektów i odpala
	 ich wewnêtrzne metody renderowania modeli na danej pozycji.
	 */
	public void render(Graphics g) {
		if (wybuchEnemy == 1) {

			enemy.Wybuch(g);
			this.removeObject(enemy);
			eksplozja = 2;
			czas.start();

		}
		if (wybuchBossa == 1) {

			bossik.Wybuch(g);
			this.removeObject(bossik);
			eksplozja = 3;
			czas.start();

		}
		if (wybuchPlayer == 1 && czyzyje == 1) {

			player.Wybuch(g);
			this.removeObject(player);
			eksplozja = 1;
			czas.start();

		}

		for (int i = 0; i < object.size(); i++) // petla sprawdza wszystkie
												// obiekty utworzone
		{
			GameObject tempObject = object.get(i);
			tempObject.render(g);
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 500;// czestotliwosc pociskow mniejsza liczba wieksza
								// czest
				int temp = r.nextInt(object.size());
				for (int j = 0; j < temp; j++) {
					int temp2 = r.nextInt(object.size());
					tempObject = object.get(temp2);
					this.addBullet(new BasicBullet(tempObject.x + 28, tempObject.y + 64, ID.BasicBullet));// tworzenie
																											// pociskow
																											// wroga
				}
			}
		}

		for (int i = 0; i < bullety.size(); i++) // petla sprawdza wszystkie
													// bullety utworzone
		{
			GameObject tempObject = bullety.get(i);
			tempObject.render(g);

		}
		if (czyzyje == 1) {
			for (int i = 0; i < mybullety.size(); i++) // petla sprawdza
														// wszystkie
														// moje bullety
														// utworzone
			{
				GameObject tempObject = mybullety.get(i);
				tempObject.render(g);
			}
		}
		if (bosszyje == 1) {
			for (int i = 0; i < boss.size(); i++) // petla sprawdza wszystkie
			// bossy utworzone
			{
				GameObject tempObject = boss.get(i);
				if (System.currentTimeMillis() - timer > 1000) {
					timer+=700;
					this.addBullet(new BasicBullet(tempObject.x + 20, tempObject.y + 215, ID.BasicBullet));
					this.addBullet(new BasicBullet(tempObject.x + 144, tempObject.y + 215, ID.BasicBullet));
				}
				if (System.currentTimeMillis() - timerrakiet > 1000) {
					timerrakiet += 10000;// czestotliwosc pociskow mniejsza liczba
									// wieksza
									// czest
					this.addRakieta(new Rakieta(tempObject.x, tempObject.y + 215, ID.Rakieta));// tworzenie
																								// rakiet
					this.addRakieta(new Rakieta(tempObject.x + 164, tempObject.y + 215, ID.Rakieta));
				}
				tempObject.render(g);
			}
		}
		for (int i = 0; i < rakiety.size(); i++) // petla sprawdza wszystkie
		// bullety utworzone
		{
			GameObject tempObject = rakiety.get(i);
			tempObject.render(g);

		}
		if (czyzyje == 1)
			player.render(g);

	}

	/**
	 dodaje obiekt do listy
	 */
	public void addObject(GameObject object) {
		this.object.add(object);
	}

	/**
	 usuwa obiekt z listy
	 */
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	/**
	 dodaje obiekt do listy
	 */
	public void addBoss(GameObject object) {
		this.boss.add(object);
	}
	/**
	 usuwa obiekt z listy
	 */
	public void removeBoss(GameObject object) {
		this.boss.remove(object);
	}
	/**
	 dodaje obiekt do listy obiektow
	 */
	public void addBullet(GameObject object) {
		this.bullety.add(object);
	}
	/**
	usuwa obiekt z listy
	 */
	public void removeBullet(GameObject object) {
		this.bullety.remove(object);
	}
	/**
	 dodaje obiekt do listy obiektow
	 */
	public void addMyBullet(GameObject object) {
		this.mybullety.add(object);
	}
	/**
	 usuwa obiekt z listy
	 */
	public void removeMyBullet(GameObject object) {
		this.mybullety.remove(object);
	}
	/**
	 dodaje obiekt do listy obiektow
	 */
	public void addRakieta(GameObject object) {
		this.rakiety.add(object);
	}
	/**
	 usuwa obiekt z listy
	 */
	public void removeRakieta(GameObject object) {
		this.rakiety.remove(object);
	}
}
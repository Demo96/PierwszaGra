package pierwsza;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD 
{
	/**
	 * zmienna przechowuje ilosc zycia gracza
	 */
	public static int HEALTH = 100;
	/**
	 * zmienna przechowuje ilosc zycia bossa
	 */
	public static int BOSSHEALTH = 100;
	/**
	 * zielen paska zycia gracza
	 */
	private int greenValue = 255;
	/**
	 * zielen paska zycia bossa
	 */
	private int greeenValue = 255;
	/**
	 * zmienna pomocnicza do zmiany wyniku i poziomu
	 */
	public static int pokaz = 0;
	/**
	 * zmienna pomocnicza do zmiany wyniku i poziomu
	 */
	public static int pokaz1=1;
	/**
	 * zmienna przechowujaca wynik
	 */
	public static int wynik = 0;
	/**
	 * zmienna przechowujaca informacje o poziomie
	 */
	public static int poziom = 1;
	/**
	 *   zmienna przechowuje informacje o tym czy aktualnie na mapie jest boss
	 */
	public static int boss1 = 0;
	
	/**
	 *   funkcja aktualizuje paski zycia, wynik oraz informacje o poziom na ekranie
	 */
	public void tick()
	{
	HEALTH=Game.clamp(HEALTH, 0, 200);
	BOSSHEALTH=Game.clamp(BOSSHEALTH, 0, 200);
	greenValue=Game.clamp(greenValue,0,255);
	greenValue=HEALTH*2;
	greeenValue=Game.clamp(greeenValue,0,255);
	greeenValue=BOSSHEALTH*2;
	pokaz=wynik;
	pokaz1=poziom;
	}
	
	/**
	 * @param g
	 * funkcja rusyje paski zycia,wynik oraz informacje o poziomie
	 */
	public void render(Graphics g)
	{
	g.setColor(Color.RED);
	g.fillRect(15, 15, 200, 32);
	g.setColor(new Color(75,greenValue,0));
	g.fillRect(15, 15, HEALTH*2, 32);
	g.setColor(Color.WHITE);
	g.drawRect(15, 15, 200, 32);
	g.drawString("Wynik " + pokaz, 15, 64);
	g.drawString("Poziom " + pokaz1, 15, 80);
	if(boss1==1)
	{
		g.setColor(Color.RED);
		g.fillRect(700, 15, 200, 32);
		g.setColor(new Color(75,greeenValue,0));
		g.fillRect(700, 15, BOSSHEALTH*2, 32);
		g.setColor(Color.WHITE);
		g.drawRect(700, 15, 200, 32);	
	}
	}
	
	public void score(int score)
	{
	this.wynik=score;
	}
	
	public int getScore()
	{
	return wynik;
	}
	
	
}
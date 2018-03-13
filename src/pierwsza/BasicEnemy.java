package pierwsza;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class BasicEnemy extends GameObject 
{
	/**
	 * przechowuje obrazek obiektu
	 */
	public BufferedImage wrog;
	/**
	 * zmienna pomocnicza do tworzenia losowego ruchu przeciwnikow
	 */
	public Random r= new Random();
	public int licznik=0;
	public Handler hand;
	public Image bum2;
	/**
	 * zmienna pomocnicza przechowuje informacje o kierunku w ktory sie przemiesci obiekt
	 */
	public int kierunek=r.nextInt(3);
	public BasicEnemy(int x, int y, ID id) 
	{
		super(x, y, id);
		try {
		    wrog= ImageIO.read(new File("wrog.png"));
		} catch (IOException e) {}
		bum2 = Toolkit.getDefaultToolkit().createImage("explosion2.gif");
		valX=2;
		valY=2;
	}

	public void tick()
	{
		x = Game.clamp(x,0,Game.WIDTH-37);
		if(licznik==20)
			{licznik=0;
     kierunek=r.nextInt(3);}
	if(kierunek==1)
		x-=valX;
	else if(kierunek==2)
		x+=valX;
	else
	y+=valY;
	licznik++;
	//if( y<=0 || y>Game.HEIGHT - 42) valY *= -1;
	//if( x<=0 || x>Game.WIDTH - 16) valX *= -1;
	}

	public void render(Graphics g) {		
				
			
	
			
			
			
			g.drawImage(wrog,x,y,null);
			
			
	
	}
	public void wybuch(Graphics g){
		
		g.drawImage(bum2,x,y,null);
		
	}

}
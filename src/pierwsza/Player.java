package pierwsza;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Player extends GameObject 
{
	
	/**
	 *  zmienna przechowuje rysunek samolotu
	 */
	public BufferedImage samolot;
	public HUD hud;
	/**
	 *  zmienna przechowuje animacje wybuchu samolotu
	 */
	public Image bum;
	
	
	
	Random r = new Random();

	public Player(int x, int y, ID id) 
	{
		super(x, y, id);
		try {
		    samolot= ImageIO.read(new File("samolot.png"));
		    
		} catch (IOException e) {}
		bum = Toolkit.getDefaultToolkit().createImage("explosion.gif");
		valX=3;
		valY=3;
	}


	public void tick() 
	{
		x += valX;
		y += valY;
		x = Game.clamp(x,0,Game.WIDTH-37);
		y = Game.clamp(y, 0, Game.HEIGHT-60);
	}


	public void render(Graphics g) 
	{
		
          if(hud.HEALTH>0){
        	   g.drawImage(samolot,x,y,null);
        	     	
        	
        	}
             
          
		
       		
    }	
		
	}
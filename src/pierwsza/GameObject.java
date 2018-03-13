package pierwsza;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class GameObject 
{
	/**
	 * wspolrzedna x obiektu
	 */
	
	protected int x;
	/**
	 * wspolrzedna y obiektu 
	 */
	protected int y;
	/**
	 * ID obiektu
	 */
	protected ID id;
	/**
	 * predkosc po x
	 */
	protected int valX;
	/**
	 * predkosc po y
	 */
	protected int valY;

	/**
	 * zmienna przechowujaca animacje wybuchu
	 */
	public Image bum2,bum1,bum3;

	/**
	 * @param x-wspolrzedna x
	 * @param y-wspolrzedna y
	 * @param id-id obiektu
	 */
	public GameObject(int x, int y, ID id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		bum2 = Toolkit.getDefaultToolkit().createImage("explosion2.gif");
		bum1 = Toolkit.getDefaultToolkit().createImage("explosion.gif");
		bum3 = Toolkit.getDefaultToolkit().createImage("explosion3.gif");
	}
	/**
	 * @param g
	 * funkcja odpowiada za generowanie wybuchow
	 */
	public void Wybuch(Graphics g){
		if(this.id==ID.BasicEnemy){
		g.drawImage(bum2,getX(),getY(),null);
		}if(this.id==ID.Player){
			g.drawImage(bum1,getX()-50,getY()-50,null);
			}
		if(this.id==ID.Boss1Enemy){
			g.drawImage(bum3,getX()-30,getY(),null);
			
		}
	}
	/**
	 * funkcja zmienia pozycje obiektu
	 */
	public abstract void tick();
	/**
	 * @param g
	 * funcja rysuje obiekt
	 */
	public abstract void render(Graphics g);
	public void setX(int x)
	{
		this.x=x;
	}
	
	public void setY(int y)
	{
		this.y=y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void SetID()
	{
		this.id=id;
	}
	
	public ID getId()
	{
		return id;	
	}
	
	public void SetValX(int valX)
	{
		this.valX=valX;
	}
	
	public void SetValY(int valY)
	{
		this.valY=valY;
	}
	
	public int getValX()
	{
		return valX;	
	}
	
	public int getValY()
	{
		return valY;	
	}
}
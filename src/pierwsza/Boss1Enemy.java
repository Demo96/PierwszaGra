package pierwsza;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Boss1Enemy extends GameObject {
	/**
	 * przechowuje obrazek obiektu 
	 */
	public BufferedImage wrog;

	/**
	 * zmienna pomocnicza przy zmianie kierunku poruszania sie obiektu
	 */
	public int kierunek = 40;

	public Boss1Enemy(int x, int y, ID id) {
		super(x, y, id);
		try {
			wrog = ImageIO.read(new File("boss1.png"));
		} catch (IOException e) {
		}
		valX = 1;
		valY = 0;
	}

	public void tick() {
		x += valX;
		kierunek++;
		if (kierunek == 80) {
			valX *= -1;
			kierunek = 0;
		}
	}

	public void render(Graphics g) {
		g.drawImage(wrog, x, y, null);

	}

}
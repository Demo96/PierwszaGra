package pierwsza;

import java.awt.Color;
import java.awt.Graphics;

public class Rakieta extends GameObject {
	int czaszycia = 300;
	int osx = 0;
	int osy = 0;

	public Rakieta(int x, int y, ID id) {
		super(x, y, id);
		valX = 2;
		valY = 2;
	}

	/**
	 * funkcja odpowiada za ruch do gory rakiety
	 */
	public void tick1() {
		y += valY;
	}

	/**
	 * funkcja odpowiada za ruch w prawo rakiety
	 */
	public void tick2() {
		x += valX;
	}

	/**
	 * funkcja odpowiada za ruch do dolu rakiety
	 */
	public void tick3() {
		y -= valY;
	}

	/**
	 * funkcja odpowiada za ruch w lewo rakiety
	 */
	public void tick4() {
		x -= valX;
	}

	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, 6, 6);
	}

	@Override
	public void tick() {
		if (osx == 1)
			tick2();
		else if (osx == -1)
			tick4();
		if (osy == 1)
			tick1();
		else if (osy == -1)
			tick3();
		czaszycia--;
	}

}
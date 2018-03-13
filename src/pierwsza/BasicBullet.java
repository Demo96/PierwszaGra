package pierwsza;

import java.awt.Color;
import java.awt.Graphics;

public class BasicBullet extends GameObject {

	public BasicBullet(int x, int y, ID id) {
		super(x, y, id);
		valX = 5;
		valY = 8;
	}

	public void tick() {
		// x+=valX;
		y += valY;
		// if( y<=0 || y>Game.HEIGHT - 42) valY *= -1;
		// if( x<=0 || x>Game.WIDTH - 16) valX *= -1;
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 6, 16);
	}

}

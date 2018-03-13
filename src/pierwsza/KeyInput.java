package pierwsza;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Patryk
 * Klasa odpowiada za obs³ugê klawiatury przy sterowaniu samolotem
 */
public class KeyInput extends KeyAdapter {
	private boolean[] keyDown = new boolean[4];
	private Handler handler;

	
	 
	public KeyInput(Handler handler) {
		this.handler = handler;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
			handler.player.SetValY(-5);
		else if (key == KeyEvent.VK_A)
			handler.player.SetValX(-5);
		else if (key == KeyEvent.VK_S)
			handler.player.SetValY(5);
		else if (key == KeyEvent.VK_D)
			handler.player.SetValX(5);
		else if (key == KeyEvent.VK_SPACE) {
			if (handler.ograniczeniestrzalow > 15)
				{handler.addMyBullet(new MyBullet(handler.player.x + 28, handler.player.y, ID.MyBullet));
			handler.ograniczeniestrzalow = 0;}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
			handler.player.SetValY(0);
		else if (key == KeyEvent.VK_A)
			handler.player.SetValX(0);
		else if (key == KeyEvent.VK_S)
			handler.player.SetValY(0);
		else if (key == KeyEvent.VK_D)
			handler.player.SetValX(0);
		else if (key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}
}

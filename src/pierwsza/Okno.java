package pierwsza;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.EventQueue;



/**
Klasa odpowiada za utworzenie nowego okna
 */
public class Okno extends Canvas
{
	private static final long serialVersionUID = -6143627814753771453L;
	/**
	Metoda tworzy nowe okno gry, przyjmuje parametry szerokosc (szerokosc okna),
	wysokosc(wysokosc okna), tytul(napis tyulowy gry na pasku onka), oraz obiekt
	klasy Game(potrzebny do dodania g³ównej klasy gry do nowego okna).
	 */
	public Okno(int szerokosc, int wysokosc, String tytul, Game game)
	{
	JFrame okno = new JFrame(tytul);
	okno.setPreferredSize(new Dimension(szerokosc,wysokosc));
	okno.setMaximumSize(new Dimension(szerokosc,wysokosc));
	okno.setMinimumSize(new Dimension(szerokosc,wysokosc));
	
	okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	okno.setResizable(false);
	okno.setLocationRelativeTo(null);
	okno.add(game);
	
	okno.setVisible(true);
	game.start();
	}
	
}
package pierwsza;

public class Kolizje 
{
	/**
	Metoda sprawdza czy miedzy wspolrzednymi i rozmiarem tempObject oraz wyslanymi
	wspolrzednymi i rozmiarem innego obiektu nie zaszla kolizja. Jezeli ktorys z punktow
	obiektu wchodzi na model innego obiektu funkcja zwraca 1, w przeciwnym wypadku zwraca
	0.
	 */
	public static int sprawdz(GameObject tempObject, int x, int y, int widthx, int lengthx, int widthy, int lenghty)
	{
             if ((tempObject.getX() >= x && tempObject.getX() <= x + widthy && tempObject.getY() >= y
                     && tempObject.getY() <= y + lenghty)
                     || (tempObject.getX() + widthx >= x && tempObject.getX() + widthx <= x + widthy && tempObject.getY() + lengthx >= y
                             && tempObject.getY() + lengthx <= y + lenghty)
                     || (tempObject.getX() + widthx >= x && tempObject.getX() + widthx <= x + widthy && tempObject.getY() >= y
                             && tempObject.getY() <= y + lenghty)
                     || (tempObject.getX() >= x && tempObject.getX() <= x + widthy && tempObject.getY() + lengthx >= y
                             && tempObject.getY() + lengthx <= y + lenghty))
             {
            	 return 1;
             }
             else return 0;
	}
}
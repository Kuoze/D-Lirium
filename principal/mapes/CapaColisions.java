package principal.mapes;

import java.awt.Rectangle;

public class CapaColisions extends CapaTiled {

	private Rectangle[] colisionables;
	
	public CapaColisions(int amplada, int alt, int x, int y, Rectangle[] colisionables) {
		super(amplada, alt, x, y);
		this.colisionables = colisionables;
	}
	
	public Rectangle[] obtenirColisionables() {
		return colisionables;
	}
}

package principal.inventari;

import java.awt.Point;

public class ObjecteUnicTiled {
	
	private Point posicio;
	private Objecte objecte;
	
	public ObjecteUnicTiled(Point posicio, Objecte objecte) {
		this.posicio = posicio;
		this.objecte = objecte;
	}
	
	public Point obtenirPosicio() {
		return posicio;
	}
	
	public Objecte obtenirObjecte() {
		return objecte;
	}

}
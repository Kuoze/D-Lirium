package principal.inventari.armes;

import java.awt.Rectangle;
import java.util.ArrayList;

import principal.ens.Jugador;

public class Desarmat extends Arma {

	public Desarmat(int id, String nom, String descripcio, int atacMinim, int atacMaxim) {
		super(id, nom, descripcio, atacMinim, atacMaxim, false, false, 0, "/sons/cop.wav");
	}

	public ArrayList<Rectangle> obtenirAbast(final Jugador jugador) {
		return null;
	}

}

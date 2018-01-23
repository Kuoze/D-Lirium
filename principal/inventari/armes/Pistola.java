package principal.inventari.armes;

import java.awt.Rectangle;
import java.util.ArrayList;

import principal.Constants;
import principal.ens.Jugador;

public class Pistola extends Arma {

	public Pistola(int id, String nom, String descripcio, int atacMinim, int atacMaxim, boolean automatica,
			boolean penetrant, double atacsPerSegon) {
		super(id, nom, descripcio, atacMinim, atacMaxim, automatica, penetrant, atacsPerSegon, "/sons/tret.wav");
	}

	public ArrayList<Rectangle> obtenirAbast(final Jugador jugador) {

		final ArrayList<Rectangle> abast = new ArrayList<>();

		final Rectangle abast1 = new Rectangle();

		if (jugador.obtenirDireccio() == 0 || jugador.obtenirDireccio() == 1) {
			abast1.width = 1;
			abast1.height = 10 * Constants.COSTAT_SPRITE;
			abast1.x = Constants.CENTRE_FINESTRA_X;
			if (jugador.obtenirDireccio() == 0) {
				abast1.y = Constants.CENTRE_FINESTRA_Y - 9;
			} else {

				abast1.y = Constants.CENTRE_FINESTRA_Y - 9 - abast1.height;
			}
		} else {
			abast1.width = 10 * Constants.COSTAT_SPRITE;
			abast1.height = 1;
			abast1.y = Constants.CENTRE_FINESTRA_Y - 3;

			if (jugador.obtenirDireccio() == 3) {
				abast1.x = Constants.CENTRE_FINESTRA_X - abast1.width;
			} else {

				abast1.x = Constants.CENTRE_FINESTRA_X;
			}
		}

		abast.add(abast1);

		return abast;
	}
}
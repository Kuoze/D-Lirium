package principal.estatMaquina;

import java.awt.Graphics;

import principal.estatMaquina.estats.joc.GestorJoc;
import principal.estatMaquina.estats.menujoc.GestorMenu;
import principal.grafics.SuperficieDibuix;

public class GestorEstats {

	private EstatJoc[] estats;
	private EstatJoc estatActual;

	public GestorEstats(final SuperficieDibuix sd) {
		iniciarEstats(sd);
		iniciarEstatActual();
	}

	private void iniciarEstats(final SuperficieDibuix sd) {
		estats = new EstatJoc[2];
		estats[0] = new GestorJoc();
		estats[1] = new GestorMenu(sd);
		// Afegim els diferents estats a l'array
	}

	private void iniciarEstatActual() {
		estatActual = estats[0];
	}

	public void actualitzar() {
		estatActual.actualitzar();
	}

	public void dibuixar(final Graphics g) {
		estatActual.dibuixar(g);
	}

	public void canviarEstatActual(final int nouEstat) {
		estatActual = estats[nouEstat];
	}

	public EstatJoc obtenirEstatActual() {
		return estatActual;
	}
}
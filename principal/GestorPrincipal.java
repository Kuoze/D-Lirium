package principal;

import principal.control.GestorControls;
import principal.estatMaquina.GestorEstats;
import principal.grafics.Finestra;
import principal.grafics.SuperficieDibuix;
import principal.so.So;

public class GestorPrincipal {
	private boolean enFuncionament = false;
	private String titol;
	private int ample;
	private int alt;

	public static SuperficieDibuix sd;
	private Finestra finestra;
	private GestorEstats ge;

	private static int fps = 0;
	private static int aps = 0;

	private So musica = new So("/sons/musica.wav");

	private GestorPrincipal(final String titol, final int ample, final int alt) {
		this.titol = titol;
		this.ample = ample;
		this.alt = alt;
	}

	public static void main(String[] args) {
		// Per a OpenGL en Mac/Linux
		// System.setProperty("sun.java2d.opengl", "True");

		/*
		 * Per Directx en Windows System.setProperty("sun.java2d.d3d", "True");
		 * System.setProperty("sun.java2d.ddforcevram", "True");
		 */

		// System.setProperty("sun.java2d.transaccel", "True");

		GestorPrincipal gp = new GestorPrincipal("D-Lirium", Constants.AMPLADA_PANTALLA_COMPLETA,
				Constants.ALT_PANTALLA_COMPLETA);

		gp.inicialitzarJoc();
		gp.inicialitzarBuclePrincipal();
	}

	private void inicialitzarJoc() {
		enFuncionament = true;
		inicialitzar();
		musica.repetir();
	}

	private void inicialitzar() {
		sd = new SuperficieDibuix(ample, alt);
		finestra = new Finestra(titol, sd);
		ge = new GestorEstats(sd);
	}

	private void inicialitzarBuclePrincipal() {
		int actualitzacionsAcumulades = 0;
		int framesAcumulats = 0;

		final int NS_PER_SEGON = 1000000000;
		final int APS_OBJECTIU = 60;
		final double NS_PER_ACTUALITZACIO = NS_PER_SEGON / APS_OBJECTIU;

		long referenciaActualitzacio = System.nanoTime();
		long referenciaComptador = System.nanoTime();

		double tempsTranscorregut;
		double delta = 0;

		while (enFuncionament) {
			final long iniciBucle = System.nanoTime();

			tempsTranscorregut = iniciBucle - referenciaActualitzacio;
			referenciaActualitzacio = iniciBucle;

			delta += tempsTranscorregut / NS_PER_ACTUALITZACIO;

			while (delta >= 1) {
				actualitzar();
				actualitzacionsAcumulades++;
				delta--;
			}

			dibuixar();
			framesAcumulats++;

			if (System.nanoTime() - referenciaComptador > NS_PER_SEGON) {

				aps = actualitzacionsAcumulades;
				fps = framesAcumulats;

				actualitzacionsAcumulades = 0;
				framesAcumulats = 0;
				referenciaComptador = System.nanoTime();
			}
		}
	}

	private void actualitzar() {
		if (GestorControls.teclat.inventariActiu) {
			ge.canviarEstatActual(1);
		} else {
			ge.canviarEstatActual(0);
		}

		ge.actualitzar();
		sd.actualitzar();
	}

	private void dibuixar() {
		sd.dibuixar(ge);
	}

	public static int obtenirFPS() {
		return fps;
	}

	public static int obtenirAPS() {
		return aps;
	}
}

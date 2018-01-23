package principal.ens;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.control.GestorControls;
import principal.eines.DibuixDebug;
import principal.inventari.RegistreObjectes;
import principal.inventari.armes.Arma;
import principal.inventari.armes.Desarmat;
import principal.sprites.FullSprites;

public class Jugador {

	private double posicioX;
	private double posicioY;

	private int direccio;

	private double velocitat = 1;

	private boolean enMoviment;

	private FullSprites fs;

	private BufferedImage imatgeActual;

	private final int AMPLE_JUGADOR = 16;
	private final int ALT_JUGADOR = 16;

	private final Rectangle LIMIT_AMUNT = new Rectangle(Constants.CENTRE_FINESTRA_X - AMPLE_JUGADOR / 2,
			Constants.CENTRE_FINESTRA_Y, AMPLE_JUGADOR, 1);
	private final Rectangle LIMIT_AVALL = new Rectangle(Constants.CENTRE_FINESTRA_X - AMPLE_JUGADOR / 2,
			Constants.CENTRE_FINESTRA_Y + ALT_JUGADOR, AMPLE_JUGADOR, 1);
	private final Rectangle LIMIT_ESQUERRA = new Rectangle(Constants.CENTRE_FINESTRA_X - AMPLE_JUGADOR / 2,
			Constants.CENTRE_FINESTRA_Y, 1, ALT_JUGADOR);
	private final Rectangle LIMIT_DRETA = new Rectangle(Constants.CENTRE_FINESTRA_X + AMPLE_JUGADOR / 2,
			Constants.CENTRE_FINESTRA_Y, 1, ALT_JUGADOR);

	private int animacio;
	private int estat;

	public static final int RESISTENCIA_TOTAL = 600;
	private int resistencia = 600;
	private int recuperacio = 0;
	private final int RECUPERACIO_MAXIMA = 300;
	private boolean recuperat = true;

	public int limitPes = 70;
	public int pesActual = 30;

	private EmmagatzematgeEquip ee;

	private ArrayList<Rectangle> abastActual;

	public int punts = 0;

	public Jugador() {
		posicioX = ElementsPrincipals.mapa.obtenirPosicioInicial().getX();
		posicioY = ElementsPrincipals.mapa.obtenirPosicioInicial().getY();

		direccio = 0;

		enMoviment = false;

		fs = new FullSprites(Constants.RUTA_PERSONATGE, Constants.COSTAT_SPRITE, false);

		imatgeActual = fs.obtenirSprite(0).obtenirImatge();

		animacio = 0;
		estat = 0;

		ee = new EmmagatzematgeEquip((Arma) RegistreObjectes.obtenerObjeto(599));

		abastActual = new ArrayList<>();
	}

	public void actualitzar() {
		canviarFullSprites();
		gestionarVelocitatResistencia();
		canviarAnimacioEstat();
		enMoviment = false;
		determinarDireccio();
		animar();
		actualitzarArmes();
	}

	private void actualitzarArmes() {
		if (ee.obtenirArma1() instanceof Desarmat) {
			return;
		}
		calcularAbastAtac();
		ee.obtenirArma1().actualitzar();
	}

	private void calcularAbastAtac() {
		abastActual = ee.obtenirArma1().obtenirAbast(this);
	}

	private void canviarFullSprites() {
		if (ee.obtenirArma1() instanceof Arma && !(ee.obtenirArma1() instanceof Desarmat)) {
			fs = new FullSprites(Constants.RUTA_PERSONATGE_PISTOLA, Constants.COSTAT_SPRITE, false);
		}
	}

	private void gestionarVelocitatResistencia() {
		if (GestorControls.teclat.corrent && resistencia > 0) {
			velocitat = 2;
			recuperat = false;
			recuperacio = 0;
		} else {
			velocitat = 1;
			if (!recuperat && recuperacio < RECUPERACIO_MAXIMA) {
				recuperacio++;
			}
			if (recuperacio == RECUPERACIO_MAXIMA && resistencia < 600) {
				resistencia++;
			}
		}
	}

	private void canviarAnimacioEstat() {
		if (animacio < 30) {
			animacio++;
		} else {
			animacio = 0;
		}

		if (animacio < 15) {
			estat = 1;
		} else {
			estat = 2;
		}
	}

	private void determinarDireccio() {
		final int velocitatX = avaluarVelocitatX();
		final int velocitatY = avaluarVelocitatY();

		if (velocitatX == 0 && velocitatY == 0) {
			return;
		}

		if ((velocitatX != 0 && velocitatY == 0) || (velocitatX == 0 && velocitatY != 0)) {
			moure(velocitatX, velocitatY);
		} else {
			// esquerra i amunt
			if (velocitatX == -1 && velocitatY == -1) {
				if (GestorControls.teclat.esquerra.obtenirDarreraPulsacio() > GestorControls.teclat.amunt
						.obtenirDarreraPulsacio()) {
					moure(velocitatX, 0);
				} else {
					moure(0, velocitatY);
				}
			}
			// esquerra i avall
			if (velocitatX == -1 && velocitatY == 1) {
				if (GestorControls.teclat.esquerra.obtenirDarreraPulsacio() > GestorControls.teclat.avall
						.obtenirDarreraPulsacio()) {
					moure(velocitatX, 0);
				} else {
					moure(0, velocitatY);
				}
			}
			// dreta i amunt
			if (velocitatX == 1 && velocitatY == -1) {
				if (GestorControls.teclat.dreta.obtenirDarreraPulsacio() > GestorControls.teclat.amunt
						.obtenirDarreraPulsacio()) {
					moure(velocitatX, 0);
				} else {
					moure(0, velocitatY);
				}
			}
			// dreta i avall
			if (velocitatX == 1 && velocitatY == 1) {
				if (GestorControls.teclat.dreta.obtenirDarreraPulsacio() > GestorControls.teclat.avall
						.obtenirDarreraPulsacio()) {
					moure(velocitatX, 0);
				} else {
					moure(0, velocitatY);
				}
			}
		}
	}

	private int avaluarVelocitatX() {
		int velocitatX = 0;

		if (GestorControls.teclat.esquerra.estaPulsada() && !GestorControls.teclat.dreta.estaPulsada()) {
			velocitatX = -1;
		} else if (GestorControls.teclat.dreta.estaPulsada() && !GestorControls.teclat.esquerra.estaPulsada()) {
			velocitatX = 1;
		}

		return velocitatX;
	}

	private int avaluarVelocitatY() {
		int velocitatY = 0;

		if (GestorControls.teclat.amunt.estaPulsada() && !GestorControls.teclat.avall.estaPulsada()) {
			velocitatY = -1;
		} else if (GestorControls.teclat.avall.estaPulsada() && !GestorControls.teclat.amunt.estaPulsada()) {
			velocitatY = 1;
		}

		return velocitatY;
	}

	private void moure(final int velocitatX, final int velocitatY) {
		enMoviment = true;

		canviarDireccio(velocitatX, velocitatY);

		if (!foraMapa(velocitatX, velocitatY)) {
			if (velocitatX == -1 && !enColisioEsquerra(velocitatX)) {
				posicioX += velocitatX * velocitat;
				restarResistencia();
			}
			if (velocitatX == 1 && !enColisioDreta(velocitatX)) {
				posicioX += velocitatX * velocitat;
				restarResistencia();
			}
			if (velocitatY == -1 && !enColisioAmunt(velocitatY)) {
				posicioY += velocitatY * velocitat;
				restarResistencia();
			}
			if (velocitatY == 1 && !enColisioAvall(velocitatY)) {
				posicioY += velocitatY * velocitat;
				restarResistencia();
			}
		}
	}

	private void restarResistencia() {
		if (GestorControls.teclat.corrent && resistencia > 0) {
			resistencia--;
		}
	}

	private boolean enColisioAmunt(int velocitatY) {
		for (int r = 0; r < ElementsPrincipals.mapa.areesColisioPerActualitzacio.size(); r++) {
			final Rectangle area = ElementsPrincipals.mapa.areesColisioPerActualitzacio.get(r);

			int origenX = area.x;
			int origenY = area.y + velocitatY * (int) velocitat + 3 * (int) velocitat;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMIT_AMUNT.intersects(areaFutura)) {
				return true;
			}
		}

		return false;
	}

	private boolean enColisioAvall(int velocitatY) {
		for (int r = 0; r < ElementsPrincipals.mapa.areesColisioPerActualitzacio.size(); r++) {
			final Rectangle area = ElementsPrincipals.mapa.areesColisioPerActualitzacio.get(r);

			int origenX = area.x;
			int origenY = area.y + velocitatY * (int) velocitat - 3 * (int) velocitat;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMIT_AVALL.intersects(areaFutura)) {
				return true;
			}
		}

		return false;
	}

	private boolean enColisioEsquerra(int velocitatX) {
		for (int r = 0; r < ElementsPrincipals.mapa.areesColisioPerActualitzacio.size(); r++) {
			final Rectangle area = ElementsPrincipals.mapa.areesColisioPerActualitzacio.get(r);

			int origenX = area.x + velocitatX * (int) velocitat + 3 * (int) velocitat;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMIT_ESQUERRA.intersects(areaFutura)) {
				return true;
			}
		}

		return false;
	}

	private boolean enColisioDreta(int velocitatX) {
		for (int r = 0; r < ElementsPrincipals.mapa.areesColisioPerActualitzacio.size(); r++) {
			final Rectangle area = ElementsPrincipals.mapa.areesColisioPerActualitzacio.get(r);

			int origenX = area.x + velocitatX * (int) velocitat - 3 * (int) velocitat;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMIT_DRETA.intersects(areaFutura)) {
				return true;
			}
		}

		return false;
	}

	private boolean foraMapa(final int velocitatX, final int velocitatY) {

		int posicioFuturaX = (int) posicioX + velocitatX * (int) velocitat;
		int posicioFuturaY = (int) posicioY + velocitatY * (int) velocitat;

		final Rectangle voresMapa = ElementsPrincipals.mapa.obtenirVores(posicioFuturaX, posicioFuturaY);

		final boolean fora;

		if (LIMIT_AMUNT.intersects(voresMapa) || LIMIT_AVALL.intersects(voresMapa)
				|| LIMIT_ESQUERRA.intersects(voresMapa) || LIMIT_DRETA.intersects(voresMapa)) {
			fora = false;
		} else {
			fora = true;
		}

		return fora;
	}

	private void canviarDireccio(final int velocitatX, final int velocitatY) {
		if (velocitatX == -1) {
			direccio = 3;
		} else if (velocitatX == 1) {
			direccio = 2;
		}

		if (velocitatY == -1) {
			direccio = 1;
		} else if (velocitatY == 1) {
			direccio = 0;
		}
	}

	private void animar() {
		if (!enMoviment) {
			estat = 0;
			animacio = 0;
		}

		imatgeActual = fs.obtenirSprite(direccio, estat).obtenirImatge();
	}

	public void dibuixar(Graphics g) {
		final int centreX = Constants.AMPLE_JOC / 2 - Constants.COSTAT_SPRITE / 2;
		final int centreY = Constants.ALT_JOC / 2 - Constants.COSTAT_SPRITE / 2;

		DibuixDebug.dibuixarImatge(g, imatgeActual, centreX, centreY);

		/*
		 * if (!alcanceActual.isEmpty()) { g.setColor(Color.red); dibujarAlcance(g); }
		 */
		// DibujoDebug.dibujarString(g, posicionX + "-" + posicionY, centroX,
		// centroY - 8);
	}

	private void dibuixarAbast(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, abastActual.get(0));
	}

	public void establirPosicioX(double posicioX) {
		this.posicioX = posicioX;
	}

	public void establirPosicioY(double posicioY) {
		this.posicioY = posicioY;
	}

	public double obtenirPosicioX() {
		return posicioX;
	}

	public double obtenirPosicioY() {
		return posicioY;
	}

	public int obtenirPosicioXInt() {
		return (int) posicioX;
	}

	public int obtenirPosicioYInt() {
		return (int) posicioY;
	}

	public int obtenirAmplada() {
		return AMPLE_JUGADOR;
	}

	public int obtenirAlt() {
		return ALT_JUGADOR;
	}

	public int obtenirResistencia() {
		return resistencia;
	}

	public Rectangle obtenir_LIMIT_AMUNT() {
		return LIMIT_AMUNT;
	}

	public EmmagatzematgeEquip obtenirEmmagatzematgeEquip() {
		return ee;
	}

	public int obtenirDireccio() {
		return direccio;
	}

	public Point obtenirPosicio() {
		return new Point((int) posicioX, (int) posicioY);
	}

	public ArrayList<Rectangle> obtenirAbastActual() {
		return abastActual;
	}
}
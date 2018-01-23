package principal.interficie_usuari;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.eines.DibuixDebug;
import principal.ens.Jugador;

public class MenuInferior {

	private Rectangle areaInventari;
	private Rectangle voraAreaInventari;

	private Color negreDesaturat;
	private Color vermellClar;
	private Color vermellFosc;
	private Color blauClar;
	private Color blauFosc;
	private Color verdClar;
	private Color verdFosc;
	private Color rosaClar;
	private Color rosaFosc;

	public MenuInferior() {

		int altMenu = 64;
		areaInventari = new Rectangle(0, Constants.ALT_JOC - altMenu, Constants.AMPLE_JOC, altMenu);
		voraAreaInventari = new Rectangle(areaInventari.x, areaInventari.y - 1, areaInventari.width, 1);

		negreDesaturat = new Color(23, 23, 23);
		vermellClar = new Color(255, 0, 0);
		vermellFosc = new Color(150, 0, 0);
		blauClar = new Color(0, 200, 255);
		blauFosc = new Color(0, 132, 168);
		verdClar = new Color(0, 255, 0);
		verdFosc = new Color(0, 150, 0);
		rosaClar = new Color(255, 0, 150);
		rosaFosc = new Color(128, 0, 74);
	}

	public void dibuixar(final Graphics g) {
		dibuixarAreaInventari(g);
		dibuixarBarraVitalitat(g);
		dibuixarBarraPoder(g);
		dibuixarBarraResistencia(g);
		dibuixarBarraExperiencia(g, 75);
		dibuixarRanuresObjectes(g);
		dibuixarPunts(g);
	}

	private void dibuixarPunts(final Graphics g) {
		final int midaVertical = 4;
		DibuixDebug.dibuixarString(g, "Puntos: " + ElementsPrincipals.jugador.punts, areaInventari.x + 10,
				areaInventari.y + midaVertical * 15, Color.white);
	}

	private void dibuixarAreaInventari(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari, negreDesaturat);
		DibuixDebug.dibuixarRectangleFarcit(g, voraAreaInventari, Color.white);
	}

	private void dibuixarBarraVitalitat(final Graphics g) {
		final int midaVertical = 4;
		final int ampladaTotal = 100;

		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical, ampladaTotal,
				midaVertical, vermellClar);
		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 2, ampladaTotal,
				midaVertical, vermellFosc);

		g.setColor(Color.white);
		DibuixDebug.dibuixarString(g, "VIT", areaInventari.x + 10, areaInventari.y + midaVertical * 3);
		DibuixDebug.dibuixarString(g, "1000", ampladaTotal + 45, areaInventari.y + midaVertical * 3);
	}

	private void dibuixarBarraPoder(final Graphics g) {
		final int midaVertical = 4;
		final int ampladaTotal = 100;

		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 4, ampladaTotal,
				midaVertical, blauClar);
		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 5, ampladaTotal,
				midaVertical, blauFosc);

		g.setColor(Color.white);
		DibuixDebug.dibuixarString(g, "POW", areaInventari.x + 10, areaInventari.y + midaVertical * 6);
		DibuixDebug.dibuixarString(g, "1000", ampladaTotal + 45, areaInventari.y + midaVertical * 6);
	}

	private void dibuixarBarraResistencia(final Graphics g) {
		final int midaVertical = 4;
		final int ampladaTotal = 100;
		final int amplada = ampladaTotal * ElementsPrincipals.jugador.obtenirResistencia() / Jugador.RESISTENCIA_TOTAL;

		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 7, amplada,
				midaVertical, verdClar);
		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 8, amplada,
				midaVertical, verdFosc);

		g.setColor(Color.white);
		DibuixDebug.dibuixarString(g, "RST", areaInventari.x + 10, areaInventari.y + midaVertical * 9);
		DibuixDebug.dibuixarString(g, "" + ElementsPrincipals.jugador.obtenirResistencia(), ampladaTotal + 45,
				areaInventari.y + midaVertical * 9);
	}

	private void dibuixarBarraExperiencia(final Graphics g, final int experiencia) {
		final int midaVertical = 4;
		final int ampladaTotal = 100;
		final int amplada = ampladaTotal * experiencia / ampladaTotal;

		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 10, amplada,
				midaVertical, rosaClar);
		DibuixDebug.dibuixarRectangleFarcit(g, areaInventari.x + 35, areaInventari.y + midaVertical * 11, amplada,
				midaVertical, rosaFosc);

		g.setColor(Color.white);
		DibuixDebug.dibuixarString(g, "EXP", areaInventari.x + 10, areaInventari.y + midaVertical * 12);
		DibuixDebug.dibuixarString(g, experiencia + "%", ampladaTotal + 45, areaInventari.y + midaVertical * 12);
	}

	private void dibuixarRanuresObjectes(final Graphics g) {
		final int ampladaRanura = 32;
		final int nombreRanures = 10;
		final int espaiRanures = 10;
		final int ampladaTotal = ampladaRanura * nombreRanures + espaiRanures * nombreRanures;
		final int xInicial = Constants.AMPLE_JOC - ampladaTotal;
		final int ampladaRanuraIEspai = ampladaRanura + espaiRanures;

		g.setColor(Color.white);

		for (int i = 0; i < nombreRanures; i++) {
			int xActual = xInicial + ampladaRanuraIEspai * i;

			Rectangle ranura = new Rectangle(xActual, areaInventari.y + 4, ampladaRanura, ampladaRanura);
			DibuixDebug.dibuixarRectangleFarcit(g, ranura);
			DibuixDebug.dibuixarString(g, "" + i, xActual + 13, areaInventari.y + 54);
		}
	}
}
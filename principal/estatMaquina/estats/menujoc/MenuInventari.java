package principal.estatMaquina.estats.menujoc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.eines.DibuixDebug;
import principal.eines.EscaladorElements;
import principal.eines.MesuradorStrings;
import principal.grafics.SuperficieDibuix;
import principal.inventari.Objecte;

public class MenuInventari extends SeccioMenu {

	public MenuInventari(String nomSeccio, Rectangle etiquetaMenu, EstructuraMenu em) {
		super(nomSeccio, etiquetaMenu, em);

		int ampladaBarra = 100;
	}

	public void actualitzar() {
		actualitzarPosicionsMenu();
	}

	private void actualitzarPosicionsMenu() {
		if (ElementsPrincipals.inventari.obtenirConsumibles().isEmpty()) {
			return;
		}

		for (int i = 0; i < ElementsPrincipals.inventari.obtenirConsumibles().size(); i++) {
			final Point puntInicial = new Point(em.FONS.x + 16, barraPes.y + barraPes.height + margeGeneral);

			final int costat = Constants.COSTAT_SPRITE;

			int idActual = ElementsPrincipals.inventari.obtenirConsumibles().get(i).obtenirID();

			ElementsPrincipals.inventari.obtenirObjecte(idActual).establirPosicioMenu(
					new Rectangle(puntInicial.x + i * (costat + margeGeneral), puntInicial.y, costat, costat));
		}
	}

	public void dibuixar(Graphics g, SuperficieDibuix sd, EstructuraMenu em) {
		dibuixarLimitPes(g, em);

		if (sd.obtenirRatoli().obtenirRectanglePosicio()
				.intersects(EscaladorElements.escalarRectangleAmunt(barraPes))) {
			dibuixarTooltipPes(g, sd, em);
		}

		dibuixarElementsConsumibles(g, em);
		dibuixarPaginador(g, em);
	}

	private void dibuixarElementsConsumibles(final Graphics g, EstructuraMenu em) {

		if (ElementsPrincipals.inventari.obtenirConsumibles().isEmpty()) {
			return;
		}

		// final Point puntoInicial = new Point(titularPanel.x + margenGeneral,
		// titularPanel.y + titularPanel.height + margenGeneral);
		final Point puntInicial = new Point(em.FONS.x + 16, barraPes.y + barraPes.height + margeGeneral);

		final int costat = Constants.COSTAT_SPRITE;

		for (int i = 0; i < ElementsPrincipals.inventari.obtenirConsumibles().size(); i++) {

			int idActual = ElementsPrincipals.inventari.obtenirConsumibles().get(i).obtenirID();
			Objecte objecteActual = ElementsPrincipals.inventari.obtenirObjecte(idActual);

			DibuixDebug.dibuixarImatge(g, objecteActual.obtenirSprite().obtenirImatge(),
					objecteActual.obtenirPosicioMenu().x, objecteActual.obtenirPosicioMenu().y);

			g.setColor(Color.black);

			DibuixDebug.dibuixarRectangleFarcit(g, puntInicial.x + i * (costat + margeGeneral) + costat - 12,
					puntInicial.y + 32 - 8, 12, 8);

			g.setColor(Color.white);

			String text = "";

			if (objecteActual.obtenirQuantitat() < 10) {
				text = "0" + objecteActual.obtenirQuantitat();
			} else {
				text = "" + objecteActual.obtenirQuantitat();
			}

			g.setFont(g.getFont().deriveFont(10f));
			DibuixDebug.dibuixarString(g, text, puntInicial.x + i * (costat + margeGeneral) + costat
					- MesuradorStrings.mesurarAmpladaPixels(g, text), puntInicial.y + 31);
		}
		g.setFont(g.getFont().deriveFont(12f));
	}

	private void dibuixarPaginador(final Graphics g, EstructuraMenu em) {
		final int costat = 32;
		final int alt = 16;

		final Rectangle anterior = new Rectangle(em.FONS.x + em.FONS.width - margeGeneral * 2 - costat * 2 - 4,
				em.FONS.y + em.FONS.height - margeGeneral - alt, costat, alt);
		final Rectangle proper = new Rectangle(anterior.x + anterior.width + margeGeneral, anterior.y, costat, alt);

		g.setColor(Color.orange);

		g.fillRect(anterior.x, anterior.y, anterior.width, anterior.height);
		g.fillRect(proper.x, proper.y, proper.width, proper.height);
	}
}
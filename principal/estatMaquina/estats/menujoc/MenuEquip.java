package principal.estatMaquina.estats.menujoc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.GestorPrincipal;
import principal.eines.DibuixDebug;
import principal.eines.EscaladorElements;
import principal.eines.MesuradorStrings;
import principal.grafics.SuperficieDibuix;
import principal.inventari.Objecte;
import principal.inventari.armes.Arma;
import principal.inventari.armes.Desarmat;

public class MenuEquip extends SeccioMenu {

	final Rectangle panellObjectes = new Rectangle(em.FONS.x + margeGeneral,
			barraPes.y + barraPes.height + margeGeneral, 248,
			Constants.ALT_JOC - barraPes.y - barraPes.height - margeGeneral * 2);
	final Rectangle titularPanellObjectes = new Rectangle(panellObjectes.x, panellObjectes.y, panellObjectes.width, 24);

	final Rectangle panellEquip = new Rectangle(panellObjectes.x + panellObjectes.width + margeGeneral,
			panellObjectes.y, 88, panellObjectes.height);
	final Rectangle titularPanellEquip = new Rectangle(panellEquip.x, panellEquip.y, panellEquip.width, 24);

	final Rectangle panellAtributs = new Rectangle(panellEquip.x + panellEquip.width + margeGeneral, panellObjectes.y,
			132, panellEquip.height);
	final Rectangle titularPanellAtributs = new Rectangle(panellAtributs.x, panellAtributs.y, panellAtributs.width, 24);

	final Rectangle etiquetaArma = new Rectangle(titularPanellEquip.x + margeGeneral,
			titularPanellEquip.y + titularPanellEquip.height + margeGeneral,
			titularPanellEquip.width - margeGeneral * 2,
			margeGeneral * 2 + MesuradorStrings.mesurarAltPixels(GestorPrincipal.sd.getGraphics(), "Arma"));
	final Rectangle contenidorArma = new Rectangle(etiquetaArma.x + 1, etiquetaArma.y + etiquetaArma.height,
			etiquetaArma.width - 2, Constants.COSTAT_SPRITE);

	Objecte objecteSeleccionat = null;

	public MenuEquip(String nomSeccio, Rectangle etiquetaMenu, EstructuraMenu em) {
		super(nomSeccio, etiquetaMenu, em);
	}

	public void actualitzar() {
		actualitzarPosicionsMenu();
		actualitzarSeleccioRatoli();
		actualitzarObjecteSeleccionat();
	}

	private void actualitzarPosicionsMenu() {
		if (ElementsPrincipals.inventari.obtenirArmes().isEmpty()) {
			return;
		}

		for (int i = 0; i < ElementsPrincipals.inventari.obtenirArmes().size(); i++) {
			final Point puntInicial = new Point(titularPanellObjectes.x + margeGeneral,
					titularPanellObjectes.y + titularPanellObjectes.height + margeGeneral);

			final int costat = Constants.COSTAT_SPRITE;

			int idActual = ElementsPrincipals.inventari.obtenirArmes().get(i).obtenirID();

			ElementsPrincipals.inventari.obtenirObjecte(idActual).establirPosicioMenu(
					new Rectangle(puntInicial.x + i * (costat + margeGeneral), puntInicial.y, costat, costat));
		}
	}

	private void actualitzarSeleccioRatoli() {
		Rectangle posicioRatoli = GestorPrincipal.sd.obtenirRatoli().obtenirRectanglePosicio();

		if (posicioRatoli.intersects(EscaladorElements.escalarRectangleAmunt(panellObjectes))) {
			if (ElementsPrincipals.inventari.obtenirArmes().isEmpty()) {
				return;
			}

			for (Objecte objecte : ElementsPrincipals.inventari.obtenirArmes()) {
				if (GestorPrincipal.sd.obtenirRatoli().obtenirClic() && posicioRatoli
						.intersects(EscaladorElements.escalarRectangleAmunt(objecte.obtenirPosicioMenu()))) {
					objecteSeleccionat = objecte;
				}
			}
		} else if (posicioRatoli.intersects(EscaladorElements.escalarRectangleAmunt(panellEquip))) {
			if (objecteSeleccionat != null && objecteSeleccionat instanceof Arma
					&& GestorPrincipal.sd.obtenirRatoli().obtenirClic()
					&& posicioRatoli.intersects(EscaladorElements.escalarRectangleAmunt(contenidorArma))) {
				ElementsPrincipals.jugador.obtenirEmmagatzematgeEquip().canviarArma1((Arma) objecteSeleccionat);
				objecteSeleccionat = null;
			}
		} else if (posicioRatoli.intersects(EscaladorElements.escalarRectangleAmunt(panellAtributs))) {

		}
	}

	private void actualitzarObjecteSeleccionat() {
		if (objecteSeleccionat != null) {
			if (GestorPrincipal.sd.obtenirRatoli().obtenirClic2()) {
				objecteSeleccionat = null;
				return;
			}

			Point posicioRatoli = EscaladorElements
					.escalarPuntAvall(GestorPrincipal.sd.obtenirRatoli().obtenirPuntPosicio());
			objecteSeleccionat.establirPosicioFlotant(
					new Rectangle(posicioRatoli.x, posicioRatoli.y, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE));
		}
	}

	public void dibuixar(Graphics g, SuperficieDibuix sd, EstructuraMenu em) {
		dibuixarLimitPes(g, em);

		dibuixarPanells(g);

		if (sd.obtenirRatoli().obtenirRectanglePosicio()
				.intersects(EscaladorElements.escalarRectangleAmunt(barraPes))) {
			dibuixarTooltipPes(g, sd, em);
		}
	}

	private void dibuixarPanells(final Graphics g) {
		dibuixarPanellObjectes(g, panellObjectes, titularPanellObjectes, "Equipables");
		dibuixarPanellEquip(g, panellEquip, titularPanellEquip, "Equipo");
		dibuixarPanellAtributs(g, panellAtributs, titularPanellAtributs, "Atributos");
	}

	private void dibuixarPanellObjectes(final Graphics g, final Rectangle panell, final Rectangle titularPanell,
			final String nomPanell) {
		dibuixarPanell(g, panell, titularPanell, nomPanell);
		dibuixarElementsEquipables(g, panell, titularPanell);
	}

	private void dibuixarElementsEquipables(final Graphics g, final Rectangle panelObjetos,
			final Rectangle titularPanell) {

		if (ElementsPrincipals.inventari.obtenirArmes().isEmpty()) {
			return;
		}

		final Point puntInicial = new Point(titularPanell.x + margeGeneral,
				titularPanell.y + titularPanell.height + margeGeneral);

		final int costat = Constants.COSTAT_SPRITE;

		for (int i = 0; i < ElementsPrincipals.inventari.obtenirArmes().size(); i++) {

			int idActual = ElementsPrincipals.inventari.obtenirArmes().get(i).obtenirID();
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

		if (objecteSeleccionat != null) {
			DibuixDebug.dibuixarImatge(g, objecteSeleccionat.obtenirSprite().obtenirImatge(), new Point(
					objecteSeleccionat.obtenirPosicioFlotant().x, objecteSeleccionat.obtenirPosicioFlotant().y));
		}
	}

	private void dibuixarPanellEquip(final Graphics g, final Rectangle panell, final Rectangle titularPanell,
			final String nomPanell) {
		dibuixarPanell(g, panell, titularPanell, nomPanell);

		g.setColor(Color.black);

		DibuixDebug.dibuixarRectangleFarcit(g, etiquetaArma);
		DibuixDebug.dibuixarRectangleContorn(g, contenidorArma);

		if (!(ElementsPrincipals.jugador.obtenirEmmagatzematgeEquip().obtenirArma1() instanceof Desarmat)) {
			Point coordenadaImatge = new Point(
					contenidorArma.x + contenidorArma.width / 2 - Constants.COSTAT_SPRITE / 2, contenidorArma.y);

			DibuixDebug.dibuixarImatge(g, ElementsPrincipals.jugador.obtenirEmmagatzematgeEquip().obtenirArma1()
					.obtenirSprite().obtenirImatge(), coordenadaImatge);
		}

		g.setColor(Color.white);
		DibuixDebug.dibuixarString(g, "Arma",
				new Point(
						etiquetaArma.x + etiquetaArma.width / 2 - MesuradorStrings.mesurarAmpladaPixels(g, "Arma") / 2,
						etiquetaArma.y + etiquetaArma.height / 2 + MesuradorStrings.mesurarAltPixels(g, "Arma") / 2));
		// dibuixar arma equipada si n'hi ha
	}

	private void dibuixarPanellAtributs(final Graphics g, final Rectangle panell, final Rectangle titularPanell,
			final String nomPanell) {
		dibuixarPanell(g, panell, titularPanell, nomPanell);
		// dibuixar els atributs
	}

	private void dibuixarPanell(final Graphics g, final Rectangle panell, final Rectangle titularPanell,
			final String nomPanell) {
		g.setColor(new Color(0xff6700));
		DibuixDebug.dibuixarRectangleContorn(g, panell);
		DibuixDebug.dibuixarRectangleFarcit(g, titularPanell);
		g.setColor(Color.white);
		DibuixDebug.dibuixarString(g, nomPanell, new Point(
				titularPanell.x + titularPanell.width / 2 - MesuradorStrings.mesurarAmpladaPixels(g, nomPanell) / 2,
				titularPanell.y + titularPanell.height - MesuradorStrings.mesurarAltPixels(g, nomPanell) / 2 - 4));
		g.setColor(new Color(0xff6700));
	}

	public Objecte obtenirObjecteSeleccionat() {
		return objecteSeleccionat;
	}

	public void eliminarObjecteSeleccionat() {
		objecteSeleccionat = null;
	}
}
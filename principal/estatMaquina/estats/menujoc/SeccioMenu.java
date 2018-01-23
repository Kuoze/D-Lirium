package principal.estatMaquina.estats.menujoc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.eines.DibuixDebug;
import principal.eines.EscaladorElements;
import principal.eines.GeneradorTooltip;
import principal.eines.MesuradorStrings;
import principal.grafics.SuperficieDibuix;

public abstract class SeccioMenu {

	protected final String nomSeccio;
	protected final Rectangle etiquetaMenu;
	protected final EstructuraMenu em;

	protected final int margeGeneral = 8;
	protected final Rectangle barraPes;

	public SeccioMenu(final String nomSeccio, final Rectangle etiquetaMenu, final EstructuraMenu em) {
		this.nomSeccio = nomSeccio;
		this.etiquetaMenu = etiquetaMenu;
		this.em = em;

		int ampladaBarra = 100;

		barraPes = new Rectangle(Constants.AMPLE_JOC - ampladaBarra - 12, em.BANNER_SUPERIOR.height + margeGeneral,
				ElementsPrincipals.jugador.limitPes, 8);
	}

	public abstract void actualitzar();

	public abstract void dibuixar(final Graphics g, final SuperficieDibuix sd, final EstructuraMenu em);

	public void dibuixarEtiquetaInactiva(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, etiquetaMenu, Color.white);
		DibuixDebug.dibuixarString(g, nomSeccio, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}

	public void dibuixarEtiquetaActiva(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, etiquetaMenu, Color.white);

		final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
		DibuixDebug.dibuixarRectangleFarcit(g, marcaActiva, new Color(0xff6700));

		DibuixDebug.dibuixarString(g, nomSeccio, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}

	public void dibuixarEtiquetaInactivaRessaltada(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, etiquetaMenu, Color.white);

		DibuixDebug.dibuixarRectangleFarcit(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10,
				etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10), new Color(0x2a2a2a));

		DibuixDebug.dibuixarString(g, nomSeccio, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}

	public void dibuixarEtiquetaActivaRessaltada(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, etiquetaMenu, Color.white);

		final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
		DibuixDebug.dibuixarRectangleFarcit(g, marcaActiva, new Color(0xff6700));

		DibuixDebug.dibuixarRectangleFarcit(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10,
				etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10), new Color(0x2a2a2a));

		DibuixDebug.dibuixarString(g, nomSeccio, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}

	protected void dibuixarLimitPes(final Graphics g, EstructuraMenu em) {

		final Rectangle contenidoBarra = new Rectangle(barraPes.x + 1, barraPes.y + 1,
				barraPes.width / (ElementsPrincipals.jugador.limitPes / ElementsPrincipals.jugador.pesActual),
				barraPes.height - 2);

		DibuixDebug.dibuixarString(g, "Peso", barraPes.x - 30, barraPes.y + 7, Color.black);
		DibuixDebug.dibuixarRectangleFarcit(g, barraPes, Color.gray);
		DibuixDebug.dibuixarRectangleFarcit(g, contenidoBarra, em.COLOR_BANNER_SUPERIOR);
	}

	protected void dibuixarTooltipPes(final Graphics g, SuperficieDibuix sd, EstructuraMenu em) {

		final Point posicioRatoli = sd.obtenirRatoli().obtenirPuntPosicio();
		final Point posicioTooltip = GeneradorTooltip.generarTooltip(posicioRatoli);
		final String pistaPosicio = GeneradorTooltip.obtenerPosicionTooltip(posicioRatoli);
		final Point posicioTooltipEscalada = EscaladorElements.escalarPuntAvall(posicioTooltip);

		final String informacioPes = ElementsPrincipals.jugador.pesActual + "/" + ElementsPrincipals.jugador.limitPes;
		final int ample = MesuradorStrings.mesurarAmpladaPixels(g, informacioPes);
		final int alt = MesuradorStrings.mesurarAltPixels(g, informacioPes);
		final int margeFont = 2;

		Rectangle tooltip = null;

		switch (pistaPosicio) {
		case "no":
			tooltip = new Rectangle(posicioTooltipEscalada.x, posicioTooltipEscalada.y, ample + margeFont * 2,
					alt);
			break;
		case "ne":
			tooltip = new Rectangle(posicioTooltipEscalada.x - ample, posicioTooltipEscalada.y,
					ample + margeFont * 2, alt);
			break;
		case "so":
			tooltip = new Rectangle(posicioTooltipEscalada.x, posicioTooltipEscalada.y - alt, ample, alt);
			break;
		case "se":
			tooltip = new Rectangle(posicioTooltipEscalada.x - ample, posicioTooltipEscalada.y - alt,
					ample + margeFont * 2, alt);
			break;
		}

		DibuixDebug.dibuixarRectangleFarcit(g, tooltip, Color.black);
		DibuixDebug.dibuixarString(g, informacioPes,
				new Point(tooltip.x + margeFont, tooltip.y + tooltip.height - margeFont), Color.white);
	}

	public String obtenirNomSeccio() {
		return nomSeccio;
	}

	public Rectangle obtenirEtiquetaMenu() {
		return etiquetaMenu;
	}

	public Rectangle obtenirEtiquetaMenuEscalada() {
		final Rectangle etiquetaEscalada = new Rectangle((int) (etiquetaMenu.x * Constants.FACTOR_ESCALAT_X),
				(int) (etiquetaMenu.y * Constants.FACTOR_ESCALAT_Y),
				(int) (etiquetaMenu.width * Constants.FACTOR_ESCALAT_X),
				(int) (etiquetaMenu.height * Constants.FACTOR_ESCALAT_Y));

		return etiquetaEscalada;
	}

}
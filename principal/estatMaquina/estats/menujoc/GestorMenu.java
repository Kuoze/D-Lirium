package principal.estatMaquina.estats.menujoc;

import java.awt.Graphics;
import java.awt.Rectangle;

import principal.estatMaquina.EstatJoc;
import principal.grafics.SuperficieDibuix;

public class GestorMenu implements EstatJoc {

	private final SuperficieDibuix sd;

	private final EstructuraMenu estructuraMenu;

	private final SeccioMenu[] seccions;

	private SeccioMenu seccioActual;

	public GestorMenu(final SuperficieDibuix sd) {
		this.sd = sd;

		estructuraMenu = new EstructuraMenu();

		seccions = new SeccioMenu[2];

		final Rectangle etiquetaInventari = new Rectangle(
				estructuraMenu.BANNER_LATERAL.x + estructuraMenu.MARGE_HORITZONTAL_ETIQUETES,
				estructuraMenu.BANNER_LATERAL.y + estructuraMenu.MARGE_VERTICAL_ETIQUETES,
				estructuraMenu.AMPLE_ETIQUETES, estructuraMenu.ALT_ETIQUETES);

		seccions[0] = new MenuInventari("Inventari", etiquetaInventari, estructuraMenu);

		final Rectangle etiquetaEquip = new Rectangle(
				estructuraMenu.BANNER_LATERAL.x + estructuraMenu.MARGE_HORITZONTAL_ETIQUETES,
				etiquetaInventari.y + etiquetaInventari.height + estructuraMenu.MARGE_VERTICAL_ETIQUETES,
				estructuraMenu.AMPLE_ETIQUETES, estructuraMenu.ALT_ETIQUETES);

		seccions[1] = new MenuEquip("Equip", etiquetaEquip, estructuraMenu);

		seccioActual = seccions[0];
	}

	public void actualitzar() {
		for (int i = 0; i < seccions.length; i++) {
			if (sd.obtenirRatoli().obtenirClic() && sd.obtenirRatoli().obtenirRectanglePosicio()
					.intersects(seccions[i].obtenirEtiquetaMenuEscalada())) {
				if (seccions[i] instanceof MenuEquip) {
					MenuEquip seccion = (MenuEquip) seccions[i];
					if (seccion.objecteSeleccionat != null) {
						seccion.eliminarObjecteSeleccionat();
					}
				}

				seccioActual = seccions[i];
			}
		}

		seccioActual.actualitzar();
	}

	public void dibuixar(final Graphics g) {
		estructuraMenu.dibuixar(g);

		for (int i = 0; i < seccions.length; i++) {

			if (seccioActual == seccions[i]) {
				if (sd.obtenirRatoli().obtenirRectanglePosicio().intersects(seccions[i].obtenirEtiquetaMenuEscalada())) {
					seccions[i].dibuixarEtiquetaActivaRessaltada(g);
				} else {
					seccions[i].dibuixarEtiquetaActiva(g);
				}
			} else {
				if (sd.obtenirRatoli().obtenirRectanglePosicio().intersects(seccions[i].obtenirEtiquetaMenuEscalada())) {
					seccions[i].dibuixarEtiquetaInactivaRessaltada(g);
				} else {
					seccions[i].dibuixarEtiquetaInactiva(g);
				}
			}
		}

		seccioActual.dibuixar(g, sd, estructuraMenu);
	}
}
package principal.estatMaquina.estats.menujoc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constants;
import principal.eines.DibuixDebug;

public class EstructuraMenu {

	public final Color COLOR_BANNER_SUPERIOR;
	public final Color COLOR_BANNER_LATERAL;
	public final Color COLOR_FONS;

	public final Rectangle BANNER_SUPERIOR;
	public final Rectangle BANNER_LATERAL;
	public final Rectangle FONS;

	public final int MARGE_HORITZONTAL_ETIQUETES;
	public final int MARGE_VERTICAL_ETIQUETES;
	public final int AMPLE_ETIQUETES;
	public final int ALT_ETIQUETES;

	public EstructuraMenu() {
		COLOR_BANNER_SUPERIOR = new Color(0xff6700);
		COLOR_BANNER_LATERAL = Color.black;
		COLOR_FONS = Color.white;

		BANNER_SUPERIOR = new Rectangle(0, 0, Constants.AMPLE_JOC, 20);
		BANNER_LATERAL = new Rectangle(0, BANNER_SUPERIOR.height, 140, Constants.ALT_JOC - BANNER_SUPERIOR.height);
		FONS = new Rectangle(BANNER_LATERAL.x + BANNER_LATERAL.width, BANNER_LATERAL.y,
				Constants.AMPLE_JOC - BANNER_LATERAL.width, Constants.ALT_JOC - BANNER_SUPERIOR.height);

		MARGE_HORITZONTAL_ETIQUETES = 20;
		MARGE_VERTICAL_ETIQUETES = 20;
		AMPLE_ETIQUETES = 100;
		ALT_ETIQUETES = 20;
	}

	public void actualitzar() {

	}

	public void dibuixar(final Graphics g) {
		DibuixDebug.dibuixarRectangleFarcit(g, BANNER_SUPERIOR, COLOR_BANNER_SUPERIOR);
		DibuixDebug.dibuixarRectangleFarcit(g, BANNER_LATERAL, COLOR_BANNER_LATERAL);
		DibuixDebug.dibuixarRectangleFarcit(g, FONS, COLOR_FONS);
	}
}
package principal.eines;

import java.awt.Point;

import principal.Constants;

public class GeneradorTooltip {

	public static Point generarTooltip(final Point pi) {

		final int x = pi.x;
		final int y = pi.y;

		final Point centroCanvas = new Point(Constants.CENTRE_FINESTRA_X, Constants.CENTRE_FINESTRA_Y);

		final int centroCanvasXEscalado = EscaladorElements.escalarPuntAmunt(centroCanvas).x;
		final int centroCanvasYEscalado = EscaladorElements.escalarPuntAmunt(centroCanvas).y;

		final int ladoCursor = 32;
		final int margenCursor = 5;

		final Point pf = new Point();

		if (x <= centroCanvasXEscalado) {
			if (y <= centroCanvasYEscalado) {
				pf.x = x + ladoCursor + margenCursor;
				pf.y = y + ladoCursor + margenCursor;
			} else {
				pf.x = x + ladoCursor + margenCursor;
				pf.y = y - ladoCursor - margenCursor;
			}
		} else {
			if (y <= centroCanvasYEscalado) {
				pf.x = x - ladoCursor - margenCursor;
				pf.y = y + ladoCursor + margenCursor;
			} else {
				pf.x = x - ladoCursor - margenCursor;
				pf.y = y - ladoCursor - margenCursor;
			}
		}

		return pf;
	}

	public static String obtenerPosicionTooltip(final Point pi) {
		final int x = pi.x;
		final int y = pi.y;

		final Point centroCanvas = new Point(Constants.CENTRE_FINESTRA_X, Constants.CENTRE_FINESTRA_Y);

		final int centroCanvasXEscalado = EscaladorElements.escalarPuntAmunt(centroCanvas).x;
		final int centroCanvasYEscalado = EscaladorElements.escalarPuntAmunt(centroCanvas).y;

		String posicion = "";

		if (x <= centroCanvasXEscalado) {
			if (y <= centroCanvasYEscalado) {
				posicion = "no";
			} else {
				posicion = "so";
			}
		} else {
			if (y <= centroCanvasYEscalado) {
				posicion = "ne";
			} else {
				posicion = "se";
			}
		}

		return posicion;
	}
}
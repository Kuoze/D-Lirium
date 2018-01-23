package principal.eines;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class MesuradorStrings {
	public static int mesurarAmpladaPixels(final Graphics g, final String s) {
		FontMetrics fm = g.getFontMetrics();

		return fm.stringWidth(s);
	}

	public static int mesurarAltPixels(final Graphics g, final String s) {
		FontMetrics fm = g.getFontMetrics();

		return (int) fm.getLineMetrics(s, g).getHeight();
	}

	public static Rectangle obtenirContorn(final Graphics g, final String s, final Point p) {
		FontMetrics fm = g.getFontMetrics();
		final int marge = 2;

		final int amplada = fm.stringWidth(s);
		final int alt = (int) fm.getLineMetrics(s, g).getHeight();

		return new Rectangle(p.x - marge, p.y - alt + marge, amplada + marge * 2, alt);
	}
}
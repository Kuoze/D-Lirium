package principal.eines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class DibuixDebug {

	private static int objectesDibuixats = 0;

	public static void dibuixarImatge(final Graphics g, final BufferedImage img, final int x, final int y) {
		objectesDibuixats++;
		g.drawImage(img, x, y, null);
	}

	public static void dibuixarImatge(final Graphics g, final BufferedImage img, final Point p) {
		objectesDibuixats++;
		g.drawImage(img, p.x, p.y, null);
	}

	public static void dibuixarString(final Graphics g, final String s, final int x, final int y) {
		objectesDibuixats++;
		g.drawString(s, x, y);
	}

	public static void dibuixarString(final Graphics g, final String s, final Point p) {
		objectesDibuixats++;
		g.drawString(s, p.x, p.y);
	}

	public static void dibuixarString(final Graphics g, final String s, final int x, final int y, final Color c) {
		objectesDibuixats++;
		g.setColor(c);
		g.drawString(s, x, y);
	}

	public static void dibuixarString(final Graphics g, final String s, final Point p, final Color c) {
		objectesDibuixats++;
		g.setColor(c);
		g.drawString(s, p.x, p.y);
	}

	public static void dibuixarRectangleFarcit(final Graphics g, final int x, final int y, final int ancho,
			final int alto) {
		objectesDibuixats++;
		g.fillRect(x, y, ancho, alto);
	}

	public static void dibuixarRectangleFarcit(final Graphics g, final Rectangle r) {
		objectesDibuixats++;
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	public static void dibuixarRectangleFarcit(final Graphics g, final int x, final int y, final int ancho,
			final int alto, final Color c) {
		objectesDibuixats++;
		g.setColor(c);
		g.fillRect(x, y, ancho, alto);
	}

	public static void dibuixarRectangleFarcit(final Graphics g, final Rectangle r, final Color c) {
		objectesDibuixats++;
		g.setColor(c);
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	public static void dibuixarRectangleContorn(final Graphics g, final int x, final int y, final int ancho,
			final int alto) {
		objectesDibuixats++;
		g.drawRect(x, y, ancho, alto);
	}

	public static void dibuixarRectangleContorn(final Graphics g, final Rectangle r) {
		objectesDibuixats++;
		g.drawRect(r.x, r.y, r.width, r.height);
	}

	public static void dibuixarRectangleContorn(final Graphics g, final int x, final int y, final int ancho,
			final int alto, final Color c) {
		objectesDibuixats++;
		g.setColor(c);
		g.drawRect(x, y, ancho, alto);
	}

	public static void dibuixarRectangleContorn(final Graphics g, final Rectangle r, final Color c) {
		objectesDibuixats++;
		g.setColor(c);
		g.drawRect(r.x, r.y, r.width, r.height);
	}

	public static int obtenirComptadorObjectes() {
		return objectesDibuixats;
	}

	public static void reiniciarComptadorObjectes() {
		objectesDibuixats = 0;
	}
}
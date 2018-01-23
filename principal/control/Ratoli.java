package principal.control;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import principal.Constants;
import principal.eines.CarregadorRecursos;
import principal.eines.DadesDebug;
import principal.grafics.SuperficieDibuix;

public class Ratoli extends MouseAdapter {

	private final Cursor cursor;
	private Point posicio;
	private boolean clic;
	private boolean clic2;

	public final int costatCursor;

	public Ratoli(final SuperficieDibuix sd) {
		Toolkit configuracio = Toolkit.getDefaultToolkit();

		final BufferedImage iconaCarregat = CarregadorRecursos
				.cargarImagenCompatibleTranslucida(Constants.RUTA_ICONA_RATOLI);

		costatCursor = iconaCarregat.getHeight();

		BufferedImage icona = iconaCarregat;

		Point punta = new Point(0, 0);

		this.cursor = configuracio.createCustomCursor(icona, punta, "Cursor per defecte");

		posicio = new Point();
		actualitzarPosicio(sd);

		clic = false;
		clic2 = false;
	}

	public void actualitzar(final SuperficieDibuix sd) {
		actualitzarPosicio(sd);

	}

	public void dibuixar(final Graphics g) {
		DadesDebug.enviarDada("RX: " + posicio.getX());
		DadesDebug.enviarDada("RY: " + posicio.getY());
	}

	public Cursor obtenirCursor() {
		return this.cursor;
	}

	private void actualitzarPosicio(final SuperficieDibuix sd) {
		final Point posicioInicial = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(posicioInicial, sd);

		posicio.setLocation(posicioInicial.getX(), posicioInicial.getY());
	}

	public Point obtenirPuntPosicio() {
		return posicio;
	}

	public Rectangle obtenirRectanglePosicio() {
		final Rectangle area = new Rectangle(posicio.x, posicio.y, 1, 1);

		return area;
	}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			clic = true;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			clic2 = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			clic = false;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			clic2 = false;
		}
	}

	public boolean obtenirClic() {
		return clic;
	}

	public boolean obtenirClic2() {
		return clic2;
	}
}
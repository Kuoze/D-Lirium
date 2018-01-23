package principal.grafics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.Constants;
import principal.GestorPrincipal;
import principal.control.GestorControls;
import principal.control.Ratoli;
import principal.eines.DadesDebug;
import principal.eines.DibuixDebug;
import principal.estatMaquina.GestorEstats;

public class SuperficieDibuix extends Canvas {

	private static final long serialVersionUID = -6227038142688953660L;

	private int amplada;
	private int alt;

	private Ratoli ratoli;

	public SuperficieDibuix(final int amplada, final int alt) {
		this.amplada = amplada;
		this.alt = alt;

		this.ratoli = new Ratoli(this);

		setIgnoreRepaint(true);
		setCursor(ratoli.obtenirCursor());
		setPreferredSize(new Dimension(amplada, alt));
		addKeyListener(GestorControls.teclat);
		addMouseListener(ratoli);
		setFocusable(true);
		requestFocus();
	}

	public void actualitzar() {
		ratoli.actualitzar(this);
	}

	public void dibuixar(final GestorEstats ge) {
		final BufferStrategy buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(4);
			return;
		}

		final Graphics2D g = (Graphics2D) buffer.getDrawGraphics();

		DibuixDebug.reiniciarComptadorObjectes();

		g.setFont(Constants.FONT_PIXEL);
		DibuixDebug.dibuixarRectangleFarcit(g, 0, 0, Constants.AMPLADA_PANTALLA_COMPLETA,
				Constants.ALT_PANTALLA_COMPLETA, Color.black);

		if (Constants.FACTOR_ESCALAT_X != 1.0 || Constants.FACTOR_ESCALAT_Y != 1.0) {
			g.scale(Constants.FACTOR_ESCALAT_X, Constants.FACTOR_ESCALAT_Y);
		}

		ge.dibuixar(g);

		g.setColor(Color.white);

		DibuixDebug.dibuixarString(g, "FPS: " + GestorPrincipal.obtenirFPS(), 20, 20);
		DibuixDebug.dibuixarString(g, "APS: " + GestorPrincipal.obtenirAPS(), 20, 30);

		DadesDebug.enviarDada("ESCALA X: " + Constants.FACTOR_ESCALAT_X);
		DadesDebug.enviarDada("ESCALA Y: " + Constants.FACTOR_ESCALAT_Y);
		DadesDebug.enviarDada("OPF: " + DibuixDebug.obtenirComptadorObjectes());

		if (GestorControls.teclat.debug) {
			DadesDebug.dibuixarDades(g);
		} else {
			DadesDebug.buidarDades();
		}

		Toolkit.getDefaultToolkit().sync();

		g.dispose();

		buffer.show();
	}

	public int obtenirAmplada() {
		return amplada;
	}

	public int obtenirAlt() {
		return alt;
	}

	public Ratoli obtenirRatoli() {
		return ratoli;
	}
}
package principal.sprites;

import java.awt.image.BufferedImage;

public class Sprite {

	private final BufferedImage imatge;

	private final int ample;
	private final int alt;

	public Sprite(final BufferedImage imatge) {
		this.imatge = imatge;

		ample = imatge.getWidth();
		alt = imatge.getHeight();
	}

	public BufferedImage obtenirImatge() {
		return imatge;
	}

	public int obtenirAmple() {
		return ample;
	}

	public int obtenirAlt() {
		return alt;
	}
}
package principal.sprites;

import java.awt.image.BufferedImage;

import principal.eines.CarregadorRecursos;

public class FullSprites {

	final private int ampladaFullEnPixels;
	final private int altFullEnPixels;

	final private int ampladaFullEnSprites;
	final private int altFullEnSprites;

	final private int ampleSprites;
	final private int altSprites;

	final private Sprite[] sprites;

	public FullSprites(final String ruta, final int costatSprites, final boolean fullOpac) {
		final BufferedImage imatge;

		if (fullOpac) {
			imatge = CarregadorRecursos.carregarImatgeCompatibleOpaca(ruta);
		} else {
			imatge = CarregadorRecursos.cargarImagenCompatibleTranslucida(ruta);
		}

		ampladaFullEnPixels = imatge.getWidth();
		altFullEnPixels = imatge.getHeight();

		ampladaFullEnSprites = ampladaFullEnPixels / costatSprites;
		altFullEnSprites = altFullEnPixels / costatSprites;

		ampleSprites = costatSprites;
		altSprites = costatSprites;

		sprites = new Sprite[ampladaFullEnSprites * altFullEnSprites];

		omplirSpritesDesdeImatge(imatge);
	}

	public FullSprites(final String ruta, final int amplaSprites, final int altSprites, final boolean fullOpac) {
		final BufferedImage imatge;

		if (fullOpac) {
			imatge = CarregadorRecursos.carregarImatgeCompatibleOpaca(ruta);
		} else {
			imatge = CarregadorRecursos.cargarImagenCompatibleTranslucida(ruta);
		}

		ampladaFullEnPixels = imatge.getWidth();
		altFullEnPixels = imatge.getHeight();

		ampladaFullEnSprites = ampladaFullEnPixels / amplaSprites;
		altFullEnSprites = altFullEnPixels / altSprites;

		this.ampleSprites = amplaSprites;
		this.altSprites = altSprites;

		sprites = new Sprite[ampladaFullEnSprites * altFullEnSprites];

		omplirSpritesDesdeImatge(imatge);
	}

	private void omplirSpritesDesdeImatge(final BufferedImage imatge) {
		for (int y = 0; y < altFullEnSprites; y++) {
			for (int x = 0; x < ampladaFullEnSprites; x++) {
				final int posicioX = x * ampleSprites;
				final int posicioY = y * altSprites;

				sprites[x + y * ampladaFullEnSprites] = new Sprite(
						imatge.getSubimage(posicioX, posicioY, ampleSprites, altSprites));
			}
		}
	}

	public Sprite obtenirSprite(final int index) {
		return sprites[index];
	}

	public Sprite obtenirSprite(final int x, final int y) {
		return sprites[x + y * ampladaFullEnSprites];
	}
}

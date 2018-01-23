package principal.inventari.consumibles;

import principal.Constants;
import principal.inventari.Objecte;
import principal.sprites.FullSprites;
import principal.sprites.Sprite;

public class Consumible extends Objecte {

	public static FullSprites fullConsumibles = new FullSprites(Constants.RUTA_OBJECTES, Constants.COSTAT_SPRITE, false);

	public Consumible(int id, String nom, String descripcio) {
		super(id, nom, descripcio);
	}

	public Consumible(int id, String nom, String descripcio, int quantitat) {
		super(id, nom, descripcio, quantitat);
	}

	public Sprite obtenirSprite() {
		return fullConsumibles.obtenirSprite(id);
	}

}
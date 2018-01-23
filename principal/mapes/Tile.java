package principal.mapes;

import java.awt.Rectangle;

import principal.sprites.Sprite;

public class Tile {

	private final Sprite sprite;
	private final int id;
	private boolean solid;

	public Tile(final Sprite sprite, final int id) {
		this.sprite = sprite;
		this.id = id;
		solid = false;
	}

	public Tile(final Sprite sprite, final int id, final boolean solid) {
		this.sprite = sprite;
		this.id = id;
		this.solid = solid;
	}

	public Sprite obtenirSprite() {
		return sprite;
	}

	public int obtenirID() {
		return id;
	}

	public void establirSolid(final boolean solid) {
		this.solid = solid;
	}

	public Rectangle obtenirLimits(final int x, final int y) {
		return new Rectangle(x, y, sprite.obtenirAmple(), sprite.obtenirAlt());
	}
}
package principal.mapes;

public class CapaSprites extends CapaTiled {
	
	private int[] sprites;
	
	public CapaSprites(int ample, int alt, int x, int y, int[] sprites) {
		super(ample, alt, x, y);
		this.sprites = sprites;
	}
	
	public int[] obtenirArraySprites() {
		return sprites;
	}

}

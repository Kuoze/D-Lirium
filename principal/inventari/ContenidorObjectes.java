package principal.inventari;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import principal.eines.CarregadorRecursos;
import principal.eines.DibuixDebug;

public class ContenidorObjectes {

	private static final BufferedImage sprite = CarregadorRecursos
			.cargarImagenCompatibleTranslucida("/imatges/sac.png");

	private Point posicio;
	private Objecte[] objectes;

	public ContenidorObjectes(final Point posicio, final int[] objects, final int[] quantitats) {

		this.posicio = posicio;
		this.objectes = new Objecte[objects.length];

		for (int i = 0; i < objects.length; i++) {
			this.objectes[i] = RegistreObjectes.obtenerObjeto(objects[i]);
			this.objectes[i].incrementarQuantitat(quantitats[i]);
		}
	}

	public void dibuixar(final Graphics g, final int puntX, final int puntY) {
		DibuixDebug.dibuixarImatge(g, sprite, puntX, puntY);
	}

	public Point obtenirPosicio() {
		return posicio;
	}

	public Objecte[] obtenirObjectes() {
		return objectes;
	}
}
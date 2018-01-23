package principal.eines;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class DadesDebug {
	private static ArrayList<String> dades = new ArrayList<String>();

	public static void enviarDada(final String dada) {
		dades.add(dada);
	}

	public static void buidarDades() {
		dades.clear();
	}

	public static void dibuixarDades(final Graphics g) {
		g.setColor(Color.white);

		for (int i = 0; i < dades.size(); i++) {
			DibuixDebug.dibuixarString(g, dades.get(i), 20, 40 + i * 10);
		}

		dades.clear();
	}
}
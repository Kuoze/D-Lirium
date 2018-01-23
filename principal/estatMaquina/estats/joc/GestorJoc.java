package principal.estatMaquina.estats.joc;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.eines.CarregadorRecursos;
import principal.eines.DadesDebug;
import principal.eines.DibuixDebug;
import principal.estatMaquina.EstatJoc;
import principal.interficie_usuari.MenuInferior;
import principal.mapes.Mapa;

public class GestorJoc implements EstatJoc {

	BufferedImage logo;
	MenuInferior menuInferior;

	public GestorJoc() {
		menuInferior = new MenuInferior();
		logo = CarregadorRecursos.cargarImagenCompatibleTranslucida(Constants.RUTA_LOGOTIP);

	}

	public void actualitzar() {

		ElementsPrincipals.jugador.actualitzar();
		ElementsPrincipals.mapa.actualitzar();
	}

	public void dibuixar(Graphics g) {
		ElementsPrincipals.mapa.dibuixar(g);
		ElementsPrincipals.jugador.dibuixar(g);
		menuInferior.dibuixar(g);

		DibuixDebug.dibuixarImatge(g, logo, 640 - logo.getWidth() - 5, 0 + 5);

		DadesDebug.enviarDada("X = " + ElementsPrincipals.jugador.obtenirPosicioXInt());
		DadesDebug.enviarDada("Y = " + ElementsPrincipals.jugador.obtenirPosicioYInt());
	}
}
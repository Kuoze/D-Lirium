package principal;

import principal.ens.Jugador;
import principal.inventari.Inventari;
import principal.mapes.MapaTiled;

public class ElementsPrincipals {

	public static MapaTiled mapa = new MapaTiled("/mapes/mapa-apocaliptic.json");
	// public static Mapa mapa = new Mapa(Constants.RUTA_MAPA);
	public static Jugador jugador = new Jugador();
	public static Inventari inventari = new Inventari();

}
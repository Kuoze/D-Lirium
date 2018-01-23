package principal;

import java.awt.Font;

import principal.eines.CarregadorRecursos;

public class Constants {

	public static final int COSTAT_SPRITE = 32;

	public static int AMPLE_JOC = 640;
	public static int ALT_JOC = 360;

	public static int AMPLADA_PANTALLA_COMPLETA = 1280;
	public static int ALT_PANTALLA_COMPLETA = 720;

	// 4:3

	public static double FACTOR_ESCALAT_X = (double) AMPLADA_PANTALLA_COMPLETA / (double) AMPLE_JOC;
	public static double FACTOR_ESCALAT_Y = (double) ALT_PANTALLA_COMPLETA / (double) ALT_JOC;

	public static int CENTRE_FINESTRA_X = AMPLE_JOC / 2;
	public static int CENTRE_FINESTRA_Y = ALT_JOC / 2;

	public static int MARGE_X = AMPLE_JOC / 2 - COSTAT_SPRITE / 2;
	public static int MARGE_Y = ALT_JOC / 2 - COSTAT_SPRITE / 2;

	public static String RUTA_MAPA = "/mapes/03.adm";
	public static String RUTA_ICONA_RATOLI = "/imatges/icones/iconaCursor.png";
	public static String RUTA_PERSONATGE = "/imatges/fullsPersonatges/2.png";
	public static String RUTA_PERSONATGE_PISTOLA = "/imatges/fullsPersonatges/4.png";
	public static String RUTA_ICONA_FINESTRA = "/imatges/icones/iconaFinestra.png";
	public static String RUTA_LOGOTIP = "/imatges/icones/logotip.png";
	public static String RUTA_OBJECTES = "/imatges/fullsObjectes/1.png";
	public static String RUTA_OBJECTES_ARMES = "/imatges/fullsObjectes/armes.png";
	public static String RUTA_ENEMICS = "/imatges/fullsEnemics/";
	public static String RUTA_OBJECTES_CURATIUS = "/imatges/fullsObjectes/1.png";
	public static String RUTA_ARMES = "/imatges/fullsObjectes/armes.png";

	public static Font FONT_PIXEL = CarregadorRecursos.carregarFont("/fonts/px10.ttf");
}
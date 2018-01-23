package principal.mapes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.control.GestorControls;
import principal.eines.CarregadorRecursos;
import principal.eines.DibuixDebug;
import principal.ens.Enemic;
import principal.ens.RegistreEnemics;
import principal.inventari.ContenidorObjectes;
import principal.sprites.FullSprites;
import principal.sprites.Sprite;

public class Mapa {

	private String[] parts;

	private final int ample;
	private final int alt;

	private final Point posicioInicial;
	private final Point puntSortida;

	private Rectangle zonaSortida;

	private String properMapa;

	private final Sprite[] paleta;

	private final boolean[] colisions;

	public final ArrayList<Rectangle> areesColisio = new ArrayList<Rectangle>();
	public ArrayList<ContenidorObjectes> objectesMapa;
	public final ArrayList<Enemic> enemics;

	private final int[] sprites;

	private final int MARGE_X = Constants.AMPLE_JOC / 2 - Constants.COSTAT_SPRITE / 2;
	private final int MARGE_Y = Constants.ALT_JOC / 2 - Constants.COSTAT_SPRITE / 2;

	public Mapa(final String ruta) {

		String contingut = CarregadorRecursos.llegirFitxerText(ruta);

		parts = contingut.split("\\*");

		ample = Integer.parseInt(parts[0]);
		alt = Integer.parseInt(parts[1]);

		String fullsEmprats = parts[2];
		String[] fullsSeparats = fullsEmprats.split(",");

		// Lectura de la paleta d'sprites
		String paletaSencera = parts[3];
		String[] partsPaleta = paletaSencera.split("#");

		// Assignar sprites aquí
		paleta = assignarSprites(partsPaleta, fullsSeparats);

		String colisionsSenceres = parts[4];
		colisions = extreureColisions(colisionsSenceres);

		String spritesSencers = parts[5];
		String[] cadenesSprites = spritesSencers.split(" ");

		sprites = extreureSprites(cadenesSprites);

		String posicio = parts[6];
		String[] posicions = posicio.split("-");

		posicioInicial = new Point();
		posicioInicial.x = Integer.parseInt(posicions[0]) * Constants.COSTAT_SPRITE;
		posicioInicial.y = Integer.parseInt(posicions[1]) * Constants.COSTAT_SPRITE;

		String sortida = parts[7];
		String[] dadesSortida = sortida.split("-");

		puntSortida = new Point();
		puntSortida.x = Integer.parseInt(dadesSortida[0]);
		puntSortida.y = Integer.parseInt(dadesSortida[1]);
		properMapa = dadesSortida[2];

		zonaSortida = new Rectangle(0, 0, 0, 0);

		String informacioObjectes = parts[8];
		objectesMapa = assignarObjectes(informacioObjectes);

		String informacioEnemics = parts[9];
		enemics = assignarEnemics(informacioEnemics);
	}

	private ArrayList<Enemic> assignarEnemics(final String informacioEnemics) {
		ArrayList<Enemic> enemics = new ArrayList<>();

		String[] infoEnemicsSeparada = informacioEnemics.split("#");
		for (int i = 0; i < infoEnemicsSeparada.length; i++) {
			String[] infoEnemicActual = infoEnemicsSeparada[i].split(":");
			String[] coordenades = infoEnemicActual[0].split(",");
			String idEnemic = infoEnemicActual[1];

			Enemic enemic = RegistreEnemics.obtenirEnemic(Integer.parseInt(idEnemic));
			enemic.establirPosicio(Double.parseDouble(coordenades[0]), Double.parseDouble(coordenades[1]));
			enemics.add(enemic);
		}
		return enemics;
	}

	private Sprite[] assignarSprites(final String[] partsPaleta, final String[] fullsSeparats) {
		Sprite[] paleta = new Sprite[partsPaleta.length];

		FullSprites full = new FullSprites("/imatges/fullsTextures/" + fullsSeparats[0] + ".png",
				Constants.COSTAT_SPRITE, true);

		for (int i = 0; i < partsPaleta.length; i++) {
			String spriteTemporal = partsPaleta[i];

			String[] partsSprite = spriteTemporal.split("-");

			int indexPaleta = Integer.parseInt(partsSprite[0]);

			int indexSpriteFull = Integer.parseInt(partsSprite[2]);

			paleta[indexPaleta] = full.obtenirSprite(indexSpriteFull);
		}

		return paleta;
	}

	private boolean[] extreureColisions(final String cadenaColisions) {
		boolean[] colisions = new boolean[cadenaColisions.length()];

		for (int i = 0; i < cadenaColisions.length(); i++) {
			if (cadenaColisions.charAt(i) == '0') {
				colisions[i] = false;
			} else {
				colisions[i] = true;
			}
		}

		return colisions;
	}

	private ArrayList<ContenidorObjectes> assignarObjectes(final String infoObjectes) {
		final ArrayList<ContenidorObjectes> objectes = new ArrayList<ContenidorObjectes>();

		String[] contenidorObjectes = infoObjectes.split("#");

		for (String contenidorsIndividuals : contenidorObjectes) {
			final ArrayList<Integer> idObjectes = new ArrayList<Integer>();
			final ArrayList<Integer> quantitatObjectes = new ArrayList<Integer>();

			final String[] divisioInformacioObjectes = contenidorsIndividuals.split(":");
			final String[] coordenades = divisioInformacioObjectes[0].split(",");

			final Point posicioContenidor = new Point(Integer.parseInt(coordenades[0]),
					Integer.parseInt(coordenades[1]));

			final String[] objectesQuantitats = divisioInformacioObjectes[1].split("/");

			for (String objectaActual : objectesQuantitats) {
				final String[] dadesObjecteActual = objectaActual.split("-");

				idObjectes.add(Integer.parseInt(dadesObjecteActual[0]));
				quantitatObjectes.add(Integer.parseInt(dadesObjecteActual[1]));
			}

			final int[] idObjectesArray = new int[idObjectes.size()];
			final int[] quantitatObjectesArray = new int[quantitatObjectes.size()];

			for (int i = 0; i < idObjectesArray.length; i++) {
				idObjectesArray[i] = idObjectes.get(i);
				quantitatObjectesArray[i] = quantitatObjectes.get(i);
			}

			final ContenidorObjectes contenidor = new ContenidorObjectes(posicioContenidor, idObjectesArray,
					quantitatObjectesArray);

			objectes.add(contenidor);
		}

		return objectes;
	}

	private int[] extreureSprites(final String[] cadenesSprites) {
		ArrayList<Integer> sprites = new ArrayList<Integer>();

		for (int i = 0; i < cadenesSprites.length; i++) {
			if (cadenesSprites[i].length() == 2) {
				sprites.add(Integer.parseInt(cadenesSprites[i]));
			} else {
				String un = "";
				String dos = "";

				String error = cadenesSprites[i];

				un += error.charAt(0);
				un += error.charAt(1);

				dos += error.charAt(2);
				dos += error.charAt(3);

				sprites.add(Integer.parseInt(un));
				sprites.add(Integer.parseInt(dos));
			}
		}

		int[] vectorSprites = new int[sprites.size()];

		for (int i = 0; i < sprites.size(); i++) {
			vectorSprites[i] = sprites.get(i);
		}

		return vectorSprites;
	}

	public void actualitzar() {
		actualitzarAreesColisio();
		actualitzarZonaSortida();
		actualitzarRecollidaObjectes();
	}

	private void actualitzarAreesColisio() {
		if (!areesColisio.isEmpty()) {
			areesColisio.clear();
		}

		for (int y = 0; y < this.alt; y++) {
			for (int x = 0; x < this.ample; x++) {
				int puntX = x * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirPosicioXInt() + MARGE_X;
				int puntY = y * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirPosicioYInt() + MARGE_Y;

				if (colisions[x + y * this.ample]) {
					final Rectangle r = new Rectangle(puntX, puntY, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);
					areesColisio.add(r);
				}
			}
		}
	}

	private void actualitzarZonaSortida() {
		int puntX = ((int) puntSortida.getX()) * Constants.COSTAT_SPRITE
				- ElementsPrincipals.jugador.obtenirPosicioXInt() + MARGE_X;
		int puntY = ((int) puntSortida.getY()) * Constants.COSTAT_SPRITE
				- ElementsPrincipals.jugador.obtenirPosicioYInt() + MARGE_Y;

		zonaSortida = new Rectangle(puntX, puntY, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);
	}

	private void actualitzarRecollidaObjectes() {
		if (!objectesMapa.isEmpty()) {
			final Rectangle areaJugador = new Rectangle(ElementsPrincipals.jugador.obtenirPosicioXInt(),
					ElementsPrincipals.jugador.obtenirPosicioYInt(), Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);

			for (int i = 0; i < objectesMapa.size(); i++) {
				final ContenidorObjectes contenidor = objectesMapa.get(i);

				final Rectangle posicioContenidor = new Rectangle(
						contenidor.obtenirPosicio().x * Constants.COSTAT_SPRITE,
						contenidor.obtenirPosicio().y * Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE,
						Constants.COSTAT_SPRITE);

				if (areaJugador.intersects(posicioContenidor) && GestorControls.teclat.recollint) {
					ElementsPrincipals.inventari.recollirObjecte(contenidor);
					objectesMapa.remove(i);
				}
			}
		}
	}

	public void dibuixar(Graphics g) {

		for (int y = 0; y < this.alt; y++) {
			for (int x = 0; x < this.ample; x++) {
				BufferedImage imatge = paleta[sprites[x + y * this.ample]].obtenirImatge();

				int puntX = x * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirPosicioXInt() + MARGE_X;
				int puntY = y * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirPosicioYInt() + MARGE_Y;

				DibuixDebug.dibuixarImatge(g, imatge, puntX, puntY);
			}
		}

		if (!objectesMapa.isEmpty()) {
			for (ContenidorObjectes contenidor : objectesMapa) {
				final int puntX = contenidor.obtenirPosicio().x * Constants.COSTAT_SPRITE
						- ElementsPrincipals.jugador.obtenirPosicioXInt() + MARGE_X;
				final int puntY = contenidor.obtenirPosicio().y * Constants.COSTAT_SPRITE
						- ElementsPrincipals.jugador.obtenirPosicioYInt() + MARGE_Y;

				contenidor.dibuixar(g, puntX, puntY);
			}
		}

		if (!enemics.isEmpty()) {
			for (Enemic enemic : enemics) {
				final int puntX = (int) enemic.obtenirPosicioX() * Constants.COSTAT_SPRITE
						- (int) ElementsPrincipals.jugador.obtenirPosicioX() + MARGE_X;
				final int puntY = (int) enemic.obtenirPosicioY() * Constants.COSTAT_SPRITE
						- (int) ElementsPrincipals.jugador.obtenirPosicioY() + MARGE_Y;
				enemic.dibuixar(g, puntX, puntY);
			}
		}
	}

	public Rectangle obtenirVores(final int posicioX, final int posicioY) {

		int x = MARGE_X - posicioX + ElementsPrincipals.jugador.obtenirAmplada();
		int y = MARGE_Y - posicioY + ElementsPrincipals.jugador.obtenirAlt();

		int ample = this.ample * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirAmplada() * 2;
		int alt = this.alt * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirAlt() * 2;

		return new Rectangle(x, y, ample, alt);
	}

	public Point obtenirPosicioInicial() {
		return posicioInicial;
	}

	public Point obtenirPuntSortida() {
		return puntSortida;
	}

	public String obtenirProperMapa() {
		return properMapa;
	}

	public Rectangle obtenirZonaSortida() {
		return zonaSortida;
	}
}
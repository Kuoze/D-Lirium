package principal.mapes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.control.GestorControls;
import principal.dijkstra.Dijkstra;
import principal.eines.CalculadoraDistancia;
import principal.eines.CarregadorRecursos;
import principal.eines.DibuixDebug;
import principal.ens.Enemic;
import principal.ens.RegistreEnemics;
import principal.inventari.Objecte;
import principal.inventari.ObjecteUnicTiled;
import principal.inventari.RegistreObjectes;
import principal.inventari.armes.Desarmat;
import principal.sprites.FullSprites;
import principal.sprites.Sprite;

public class MapaTiled {

	private int ampladaMapaEnTiles;
	private int altMapaEnTiles;

	private Point puntInicial;

	private ArrayList<CapaSprites> capesSprites;
	private ArrayList<CapaColisions> capesColisions;

	private ArrayList<Rectangle> areesColisioOriginals;
	public ArrayList<Rectangle> areesColisioPerActualitzacio;

	private Sprite[] paletaSprites;

	private Dijkstra d;

	private ArrayList<ObjecteUnicTiled> objectesMapa;
	private ArrayList<Enemic> enemicsMapa;

	public MapaTiled(final String ruta) {
		String contingut = CarregadorRecursos.llegirFitxerText(ruta);

		// AMPLE, ALT
		JSONObject globalJSON = obtenirObjecteJSON(contingut);
		ampladaMapaEnTiles = obtenirIntDesdeJSON(globalJSON, "width");
		altMapaEnTiles = obtenirIntDesdeJSON(globalJSON, "height");

		// PUNT INICIAL
		JSONObject puntInicial = obtenirObjecteJSON(globalJSON.get("start").toString());
		this.puntInicial = new Point(obtenirIntDesdeJSON(puntInicial, "x"), obtenirIntDesdeJSON(puntInicial, "y"));

		// CAPES
		JSONArray capes = obtenirArraJSON(globalJSON.get("layers").toString());

		this.capesSprites = new ArrayList<>();
		this.capesColisions = new ArrayList<>();

		// INICIALITZAR CAPES
		for (int i = 0; i < capes.size(); i++) {
			JSONObject dadesCapa = obtenirObjecteJSON(capes.get(i).toString());

			int ampladaCapa = obtenirIntDesdeJSON(dadesCapa, "width");
			int altCapa = obtenirIntDesdeJSON(dadesCapa, "height");
			int xCapa = obtenirIntDesdeJSON(dadesCapa, "x");
			int yCapa = obtenirIntDesdeJSON(dadesCapa, "y");
			String tipus = dadesCapa.get("type").toString();

			switch (tipus) {
			case "tilelayer":
				JSONArray sprites = obtenirArraJSON(dadesCapa.get("data").toString());
				int[] spritesCapa = new int[sprites.size()];
				for (int j = 0; j < sprites.size(); j++) {
					int codiSprite = Integer.parseInt(sprites.get(j).toString());
					spritesCapa[j] = codiSprite - 1;
				}
				this.capesSprites.add(new CapaSprites(ampladaCapa, altCapa, xCapa, yCapa, spritesCapa));
				break;
			case "objectgroup":
				JSONArray rectangles = obtenirArraJSON(dadesCapa.get("objects").toString());
				Rectangle[] rectanglesCapa = new Rectangle[rectangles.size()];
				for (int j = 0; j < rectangles.size(); j++) {
					JSONObject dadesRectangle = obtenirObjecteJSON(rectangles.get(j).toString());

					int x = obtenirIntDesdeJSON(dadesRectangle, "x");
					int y = obtenirIntDesdeJSON(dadesRectangle, "y");
					int ample = obtenirIntDesdeJSON(dadesRectangle, "width");
					int alt = obtenirIntDesdeJSON(dadesRectangle, "height");

					if (x == 0)
						x = 1;
					if (y == 0)
						y = 1;
					if (ample == 0)
						ample = 1;
					if (alt == 0)
						alt = 1;

					Rectangle rectangle = new Rectangle(x, y, ample, alt);
					rectanglesCapa[j] = rectangle;
				}
				this.capesColisions.add(new CapaColisions(ampladaCapa, altCapa, xCapa, yCapa, rectanglesCapa));

				break;
			}
		}

		// COMBINAR COLISIONS EN UN SOL ARRAYLIST PER EFICIÈNCIA
		areesColisioOriginals = new ArrayList<>();
		for (int i = 0; i < capesColisions.size(); i++) {
			Rectangle[] rectangles = capesColisions.get(i).obtenirColisionables();

			for (int j = 0; j < rectangles.length; j++) {
				areesColisioOriginals.add(rectangles[j]);
			}
		}

		d = new Dijkstra(new Point(10, 10), ampladaMapaEnTiles, altMapaEnTiles, areesColisioOriginals);

		// ESBRINAR TOTAL D' SPRITES EXISTENTS A TOTES LES CAPES
		JSONArray coleccionsSprites = obtenirArraJSON(globalJSON.get("tilesets").toString());
		int totalSprites = 0;
		for (int i = 0; i < coleccionsSprites.size(); i++) {
			JSONObject dadesGrup = obtenirObjecteJSON(coleccionsSprites.get(i).toString());
			totalSprites += obtenirIntDesdeJSON(dadesGrup, "tilecount");
		}
		paletaSprites = new Sprite[totalSprites];

		// ASSIGNAR SPRITES NECESSARIS A LA PALETA A PARTIR DE LES CAPES
		for (int i = 0; i < coleccionsSprites.size(); i++) {
			JSONObject dadesGrup = obtenirObjecteJSON(coleccionsSprites.get(i).toString());

			String nomImatge = dadesGrup.get("image").toString();
			int ampleTiles = obtenirIntDesdeJSON(dadesGrup, "tilewidth");
			int altTiles = obtenirIntDesdeJSON(dadesGrup, "tileheight");
			FullSprites full = new FullSprites("/imatges/fullsTextures/" + nomImatge, ampleTiles, altTiles, false);

			int primerSpriteColeccio = obtenirIntDesdeJSON(dadesGrup, "firstgid") - 1;
			int darrerSpriteColeccio = primerSpriteColeccio + obtenirIntDesdeJSON(dadesGrup, "tilecount") - 1;

			for (int j = 0; j < this.capesSprites.size(); j++) {
				CapaSprites capaActual = this.capesSprites.get(j);
				int[] spritesCapa = capaActual.obtenirArraySprites();

				for (int k = 0; k < spritesCapa.length; k++) {
					int idSpriteActual = spritesCapa[k];
					if (idSpriteActual >= primerSpriteColeccio && idSpriteActual <= darrerSpriteColeccio) {
						if (paletaSprites[idSpriteActual] == null) {
							paletaSprites[idSpriteActual] = full.obtenirSprite(idSpriteActual - primerSpriteColeccio);
						}
					}
				}
			}
		}

		// OBTENIR OBJECTES
		objectesMapa = new ArrayList<>();
		JSONArray coleccioObjectes = obtenirArraJSON(globalJSON.get("objectes").toString());
		for (int i = 0; i < coleccioObjectes.size(); i++) {
			JSONObject dadesObjecte = obtenirObjecteJSON(coleccioObjectes.get(i).toString());

			int idObjeto = obtenirIntDesdeJSON(dadesObjecte, "id");
			int quantitatObjecte = obtenirIntDesdeJSON(dadesObjecte, "quantitat");
			int xObjeto = obtenirIntDesdeJSON(dadesObjecte, "x");
			int yObjeto = obtenirIntDesdeJSON(dadesObjecte, "y");

			Point posicioObjecte = new Point(xObjeto, yObjeto);
			Objecte objeto = RegistreObjectes.obtenerObjeto(idObjeto);
			ObjecteUnicTiled objetoUnico = new ObjecteUnicTiled(posicioObjecte, objeto);
			objectesMapa.add(objetoUnico);
		}

		// OBTENER ENEMIGOS
		enemicsMapa = new ArrayList<>();
		JSONArray coleccionsEnemics = obtenirArraJSON(globalJSON.get("enemics").toString());
		for (int i = 0; i < coleccionsEnemics.size(); i++) {
			JSONObject dadesEnemic = obtenirObjecteJSON(coleccionsEnemics.get(i).toString());

			int idEnemic = obtenirIntDesdeJSON(dadesEnemic, "id");
			int xEnemic = obtenirIntDesdeJSON(dadesEnemic, "x");
			int yEnemic = obtenirIntDesdeJSON(dadesEnemic, "y");

			Point posicioEnemic = new Point(xEnemic, yEnemic);
			Enemic enemic = RegistreEnemics.obtenirEnemic(idEnemic);
			enemic.establirPosicio(posicioEnemic.x, posicioEnemic.y);

			enemicsMapa.add(enemic);
		}

		areesColisioPerActualitzacio = new ArrayList<>();
	}

	public void actualitzar() {
		actualitzarAreesColisio();
		actualitzarRecollidaObjectes();
		actualitzarEnemics();
		actualitzarAtacs();

		Point punt = new Point(ElementsPrincipals.jugador.obtenirPosicioXInt(),
				ElementsPrincipals.jugador.obtenirPosicioYInt());
		Point puntCoincident = d.obtenirCoordenadesNodeCoincident(punt);
		d.reiniciarIAvaluar(puntCoincident);
	}

	private void actualitzarAreesColisio() {
		if (!areesColisioPerActualitzacio.isEmpty()) {
			areesColisioPerActualitzacio.clear();
		}

		for (int i = 0; i < areesColisioOriginals.size(); i++) {
			Rectangle rInicial = areesColisioOriginals.get(i);

			int puntX = rInicial.x - (int) ElementsPrincipals.jugador.obtenirPosicioX() + Constants.MARGE_X;
			int puntY = rInicial.y - (int) ElementsPrincipals.jugador.obtenirPosicioY() + Constants.MARGE_Y;

			final Rectangle rFinal = new Rectangle(puntX, puntY, rInicial.width, rInicial.height);

			areesColisioPerActualitzacio.add(rFinal);
		}
	}

	private void actualitzarRecollidaObjectes() {
		if (!objectesMapa.isEmpty()) {
			final Rectangle areaJugador = new Rectangle(ElementsPrincipals.jugador.obtenirPosicioXInt(),
					ElementsPrincipals.jugador.obtenirPosicioYInt(), Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);

			for (int i = 0; i < objectesMapa.size(); i++) {
				final ObjecteUnicTiled objetoActual = objectesMapa.get(i);

				final Rectangle posicioObjecteActual = new Rectangle(objetoActual.obtenirPosicio().x,
						objetoActual.obtenirPosicio().y, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);

				if (areaJugador.intersects(posicioObjecteActual) && GestorControls.teclat.recollint) {
					ElementsPrincipals.inventari.recollirObjectes(objetoActual);
					objectesMapa.remove(i);
				}
			}
		}
	}

	private void actualitzarEnemics() {
		if (!enemicsMapa.isEmpty()) {
			for (Enemic enemic : enemicsMapa) {
				enemic.canviarProperNode(d.trobarProperNodePerEnemic(enemic));
				enemic.actualitzar(enemicsMapa);
			}
		}
	}

	private void actualitzarAtacs() {

		if (enemicsMapa.isEmpty() || ElementsPrincipals.jugador.obtenirAbastActual().isEmpty()
				|| ElementsPrincipals.jugador.obtenirEmmagatzematgeEquip().obtenirArma1() instanceof Desarmat) {
			return;
		}

		if (GestorControls.teclat.atacant) {
			ArrayList<Enemic> enemicsEnRang = new ArrayList<>();

			if (ElementsPrincipals.jugador.obtenirEmmagatzematgeEquip().obtenirArma1().esPenetrant()) {
				for (Enemic enemic : enemicsMapa) {
					System.out.println(ElementsPrincipals.jugador.obtenirAbastActual().get(0));
					System.out.println(enemic.obtenirAreaPosicional());
					if (ElementsPrincipals.jugador.obtenirAbastActual().get(0).intersects(enemic.obtenirArea())) {
						System.out.println("impacte");
						enemicsEnRang.add(enemic);
					}
				}
			} else {
				Enemic enemicMesProper = null;
				Double distanciaMesPropera = null;

				for (Enemic enemic : enemicsMapa) {
					if (ElementsPrincipals.jugador.obtenirAbastActual().get(0).intersects(enemic.obtenirArea())) {
						Point puntJugador = new Point((int) ElementsPrincipals.jugador.obtenirPosicioX() / 32,
								(int) ElementsPrincipals.jugador.obtenirPosicioY() / 32);
						Point puntEnemic = new Point((int) enemic.obtenirPosicioX(), (int) enemic.obtenirPosicioY());

						Double distanciaActual = CalculadoraDistancia.obtenirDistanciaEntrePunts(puntJugador,
								puntEnemic);

						if (enemicMesProper == null) {
							enemicMesProper = enemic;
							distanciaMesPropera = distanciaActual;
						} else if (distanciaActual < distanciaMesPropera) {
							enemicMesProper = enemic;
							distanciaMesPropera = distanciaActual;
						}

					}
				}
				enemicsEnRang.add(enemicMesProper);
			}
			System.out.println(enemicsEnRang.size());
			ElementsPrincipals.jugador.obtenirEmmagatzematgeEquip().obtenirArma1().atacar(enemicsEnRang);
		}

		Iterator<Enemic> iterador = enemicsMapa.iterator();

		while (iterador.hasNext()) {
			Enemic enemic = iterador.next();

			if (enemic.obtenirVidaActual() <= 0) {
				iterador.remove();
				ElementsPrincipals.jugador.punts += 100;
			}
		}
	}

	public void dibuixar(Graphics g) {
		for (int i = 0; i < capesSprites.size(); i++) {
			int[] spritesCapa = capesSprites.get(i).obtenirArraySprites();

			for (int y = 0; y < altMapaEnTiles; y++) {
				for (int x = 0; x < ampladaMapaEnTiles; x++) {
					int idSpriteActual = spritesCapa[x + y * ampladaMapaEnTiles];
					if (idSpriteActual != -1) {
						int puntX = x * Constants.COSTAT_SPRITE - (int) ElementsPrincipals.jugador.obtenirPosicioX()
								+ Constants.MARGE_X;
						int puntY = y * Constants.COSTAT_SPRITE - (int) ElementsPrincipals.jugador.obtenirPosicioY()
								+ Constants.MARGE_Y;

						if (puntX < 0 - Constants.COSTAT_SPRITE || puntX > Constants.AMPLE_JOC
								|| puntY < 0 - Constants.COSTAT_SPRITE || puntY > Constants.ALT_JOC - 65) {
							continue;
						}

						DibuixDebug.dibuixarImatge(g, paletaSprites[idSpriteActual].obtenirImatge(), puntX, puntY);
					}
				}
			}
		}

		for (int i = 0; i < objectesMapa.size(); i++) {
			ObjecteUnicTiled objecteActual = objectesMapa.get(i);

			int puntX = objecteActual.obtenirPosicio().x - (int) ElementsPrincipals.jugador.obtenirPosicioX()
					+ Constants.MARGE_X;
			int puntY = objecteActual.obtenirPosicio().y - (int) ElementsPrincipals.jugador.obtenirPosicioY()
					+ Constants.MARGE_Y;

			if (puntX < 0 - Constants.COSTAT_SPRITE || puntX > Constants.AMPLE_JOC
					|| puntY < 0 - Constants.COSTAT_SPRITE || puntY > Constants.ALT_JOC - 65) {
				continue;
			}

			DibuixDebug.dibuixarImatge(g, objecteActual.obtenirObjecte().obtenirSprite().obtenirImatge(), puntX, puntY);
		}

		for (int i = 0; i < enemicsMapa.size(); i++) {
			Enemic enemic = enemicsMapa.get(i);
			int puntX = (int) enemic.obtenirPosicioX() - (int) ElementsPrincipals.jugador.obtenirPosicioX()
					+ Constants.MARGE_X;
			int puntY = (int) enemic.obtenirPosicioY() - (int) ElementsPrincipals.jugador.obtenirPosicioY()
					+ Constants.MARGE_Y;

			if (puntX < 0 - Constants.COSTAT_SPRITE || puntX > Constants.AMPLE_JOC
					|| puntY < 0 - Constants.COSTAT_SPRITE || puntY > Constants.ALT_JOC - 65) {
				continue;
			}

			enemic.dibuixar(g, puntX, puntY);
			// DibujoDebug.dibujarRectanguloContorno(g, puntoX, puntoY, 32, 32);
		}
	}

	private JSONObject obtenirObjecteJSON(final String codiJSON) {
		JSONParser lector = new JSONParser();
		JSONObject objecteJSON = null;

		try {
			Object recuperat = lector.parse(codiJSON);
			objecteJSON = (JSONObject) recuperat;
		} catch (ParseException e) {
			System.out.println("Posició: " + e.getPosition());
			System.out.println(e);
		}

		return objecteJSON;
	}

	private JSONArray obtenirArraJSON(final String codiJSON) {
		JSONParser lector = new JSONParser();
		JSONArray arrayJSON = null;

		try {
			Object recuperat = lector.parse(codiJSON);
			arrayJSON = (JSONArray) recuperat;
		} catch (ParseException e) {
			System.out.println("Posició: " + e.getPosition());
			System.out.println(e);
		}

		return arrayJSON;
	}

	private int obtenirIntDesdeJSON(final JSONObject objecteJSON, final String clau) {
		return Integer.parseInt(objecteJSON.get(clau).toString());
	}

	public Point obtenirPosicioInicial() {
		return puntInicial;
	}

	public Rectangle obtenirVores(final int posicioX, final int posicioY) {
		int x = Constants.MARGE_X - posicioX + ElementsPrincipals.jugador.obtenirAmplada();
		int y = Constants.MARGE_Y - posicioY + ElementsPrincipals.jugador.obtenirAlt();

		int ample = this.ampladaMapaEnTiles * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirAmplada() * 2;
		int alt = this.altMapaEnTiles * Constants.COSTAT_SPRITE - ElementsPrincipals.jugador.obtenirAlt() * 2;

		return new Rectangle(x, y, ample, alt);
	}
}
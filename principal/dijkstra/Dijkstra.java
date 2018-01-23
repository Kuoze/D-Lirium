package principal.dijkstra;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import principal.Constants;
import principal.ens.Enemic;

public class Dijkstra {

	private int ampladaMapaEnTiles;
	private int altMapaEnTiles;

	private ArrayList<Node> nodesMapa;
	private ArrayList<Node> pendents;
	private ArrayList<Node> visitats;
	private boolean constructor = true;

	public Dijkstra(final Point centreCalcul, final int ampladaMapaEnTiles, final int altMapaEnTiles,
			final ArrayList<Rectangle> zonesSortida) {

		this.ampladaMapaEnTiles = ampladaMapaEnTiles;
		this.altMapaEnTiles = altMapaEnTiles;
		nodesMapa = new ArrayList<>();

		for (int y = 0; y < altMapaEnTiles; y++) {
			for (int x = 0; x < ampladaMapaEnTiles; x++) {
				final int costat = Constants.COSTAT_SPRITE;
				final Rectangle ubicacioNode = new Rectangle(x * costat, y * costat, costat, costat);

				boolean transitable = true;
				for (Rectangle area : zonesSortida) {
					if (ubicacioNode.intersects(area)) {
						transitable = false;
						break;
					}
				}

				if (!transitable) {
					continue;
				}

				Node node = new Node(new Point(x, y), Double.MAX_VALUE);
				nodesMapa.add(node);
			}
		}
		pendents = new ArrayList<>(nodesMapa);

		reiniciarIAvaluar(centreCalcul);
		constructor = false;
	}

	public Point obtenirCoordenadesNodeCoincident(final Point puntJugador) {
		Rectangle rectanglePuntExacte = new Rectangle(puntJugador.x / Constants.COSTAT_SPRITE,
				puntJugador.y / Constants.COSTAT_SPRITE, 1, 1);

		Point puntExacte = null;

		for (Node node : nodesMapa) {
			if (node.obtenirArea().intersects(rectanglePuntExacte)) {
				puntExacte = new Point(rectanglePuntExacte.x, rectanglePuntExacte.y);
				return puntExacte;
			}
		}

		return puntExacte;
	}

	private ArrayList<Node> clonarNodesMapaANodesPendents() {
		ArrayList<Node> nodesClonats = new ArrayList<>();
		for (Node node : nodesMapa) {
			Point posicio = node.obtenirPosicio();
			double distancia = node.obtenirDistancia();
			Node nodeClonat = new Node(posicio, distancia);
			nodesClonats.add(nodeClonat);
		}

		return nodesClonats;
	}

	public void reiniciarIAvaluar(final Point centreCalcul) {
		if (!constructor) {
			if (visitats.size() == 0) {

				clonarNodesMapaANodesPendents();
			} else {
				pendents = new ArrayList<>(visitats);
				for (Node node : pendents) {
					node.canviarDistancia(Double.MAX_VALUE);
				}
			}
		}

		definirCentreCalculEnPendents(centreCalcul);
		visitats = new ArrayList<>();
		avaluarHeuristicaGlobal();
	}

	private void definirCentreCalculEnPendents(final Point centreCalcul) {
		for (Node node : pendents) {
			if (node.obtenirPosicio().equals(centreCalcul)) {
				node.canviarDistancia(0.0);
			}
		}
	}

	private void avaluarHeuristicaGlobal() {
		while (!pendents.isEmpty()) {
			int canvis = 0;

			for (Iterator<Node> iterador = pendents.iterator(); iterador.hasNext();) {
				Node node = iterador.next();

				if (node.obtenirDistancia() == Double.MAX_VALUE) {
					continue;
				} else {
					avaluarHeuristicaVeins(node);
					visitats.add(node);
					iterador.remove();
					canvis++;
				}
			}

			if (canvis == 0) {
				break;
			}
		}
	}

	private void avaluarHeuristicaVeins(final Node node) {

		int inicialY = node.obtenirPosicio().y;
		int inicialX = node.obtenirPosicio().x;

		final double DISTANCIA_DIAGONAL = 1.42412;

		for (int y = inicialY - 1; y < inicialY + 2; y++) {
			for (int x = inicialX - 1; x < inicialX + 2; x++) {
				// dentro del rango del mapa (-1 en anchoMapaEnTiles??? )
				if (x <= -1 || y <= -1 || x >= ampladaMapaEnTiles || y >= altMapaEnTiles) {
					continue;
				}

				// omitimos el propio nodo
				if (inicialX == x && inicialY == y) {
					continue;
				}

				// nodo existe en la posicion?
				int indexNode = obtenirIndexNodePerPosicioEnPendents(new Point(x, y));
				if (indexNode == -1) {
					continue;
				}

				// solo cambiamos la distancia si es transitable y si no ha sido cambiada
				if (pendents.get(indexNode).obtenirDistancia() == Double.MAX_VALUE - 1) {

					// distancia recta vs diagonal
					double distancia;
					if (inicialX != x && inicialY != y) {
						distancia = DISTANCIA_DIAGONAL;
					} else {
						distancia = 1;
					}

					pendents.get(indexNode).canviarDistancia(node.obtenirDistancia() + distancia);
				}
			}
		}
	}

	private ArrayList<Node> obtenirNodesVeins(Node node) {
		int inicialY = node.obtenirPosicio().y;
		int inicialX = node.obtenirPosicio().x;

		ArrayList<Node> nodesVeisn = new ArrayList<>();

		for (int y = inicialY - 1; y < inicialY + 2; y++) {
			for (int x = inicialX - 1; x < inicialX + 2; x++) {
				if (x <= -1 || y <= -1 || x >= ampladaMapaEnTiles || y >= altMapaEnTiles) {
					continue;
				}

				if (inicialX == x && inicialY == y) {
					continue;
				}

				int indiceNodo = obtenirIndexNodePerPosicioEnVisitats(new Point(x, y));
				if (indiceNodo == -1) {
					continue;
				}
				nodesVeisn.add(visitats.get(indiceNodo));
			}
		}

		return nodesVeisn;
	}

	public Node trobarProperNodePerEnemic(Enemic enemic) {
		ArrayList<Node> nodesAfectats = new ArrayList<>();

		Node properNode = null;

		for (Node node : visitats) {
			if (enemic.obtenirAreaPosicional().intersects(node.obtenirAreaPixels())) {
				nodesAfectats.add(node);
			}
		}

		if (nodesAfectats.size() == 1) {
			Node nodeBase = nodesAfectats.get(0);
			nodesAfectats = obtenirNodesVeins(nodeBase);
		}

		for (int i = 0; i < nodesAfectats.size(); i++) {
			if (i == 0) {
				properNode = nodesAfectats.get(0);
			} else {
				if (properNode.obtenirDistancia() > nodesAfectats.get(i).obtenirDistancia()) {
					properNode = nodesAfectats.get(i);
				}
			}
		}

		return properNode;
	}

	private int obtenirIndexNodePerPosicioEnPendents(final Point posicio) {
		for (Node node : pendents) {
			if (node.obtenirPosicio().equals(posicio)) {
				return pendents.indexOf(node);
			}
		}

		return -1;
	}

	private int obtenirIndexNodePerPosicioEnVisitats(final Point posicio) {
		for (Node node : visitats) {
			if (node.obtenirPosicio().equals(posicio)) {
				return visitats.indexOf(node);
			}
		}

		return -1;
	}

	public ArrayList<Node> obtenirVisitats() {
		return visitats;
	}

	public ArrayList<Node> obtenirPendents() {
		return pendents;
	}
}
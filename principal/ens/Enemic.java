package principal.ens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import principal.Constants;
import principal.ElementsPrincipals;
import principal.dijkstra.Node;
import principal.eines.CalculadoraDistancia;
import principal.eines.DibuixDebug;
import principal.so.So;

public class Enemic {

	private So lament;

	private long duracioLament;
	private long properLament = 0;

	private int idEnemic;

	private double posisioX;
	private double posicioY;

	private String nom;
	private int vidaMaxima;
	private float vidaActual;

	private Node properNode;

	public Enemic(final int idEnemic, final String nom, final int vidaMaxima, final String rutaLamento) {
		this.idEnemic = idEnemic;

		this.posisioX = 0;
		this.posicioY = 0;

		this.nom = nom;
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;

		this.lament = new So(rutaLamento);
		this.duracioLament = lament.obtenirDuracio();
	}

	public void actualitzar(ArrayList<Enemic> enemics) {
		if (properLament > 0) {
			properLament -= 1000000 / 60;
		}
		moureProperNode(enemics);
	}

	private void moureProperNode(ArrayList<Enemic> enemics) {
		if (properNode == null) {
			return;
		}
		// System.out.println("Nodo " + siguienteNodo.obtenerAreaPixeles());
		for (Enemic enemic : enemics) {
			// System.out.println("Enemigo " + enemigo.obtenerAreaPosicional());
			if (enemic.obtenirAreaPosicional().equals(this.obtenirAreaPosicional())) {
				continue;
			}

			if (enemic.obtenirAreaPosicional().intersects(properNode.obtenirAreaPixels())) {
				System.out.println("Enemic molestant!");
				return;
			}
		}

		double velocitat = 0.5;

		int xProperNode = properNode.obtenirPosicio().x * Constants.COSTAT_SPRITE;
		int yProperNode = properNode.obtenirPosicio().y * Constants.COSTAT_SPRITE;

		if (posisioX < xProperNode) {
			posisioX += velocitat;
		}

		if (posisioX > xProperNode) {
			posisioX -= velocitat;
		}

		if (posicioY < yProperNode) {
			posicioY += velocitat;
		}

		if (posicioY > yProperNode) {
			posicioY -= velocitat;
		}
	}

	public void dibuixar(final Graphics g, final int puntX, final int puntY) {
		if (vidaActual <= 0) {
			return;
		}

		dibuixarBarraVida(g, puntX, puntY);
		// DibujoDebug.dibujarRectanguloContorno(g, obtenerArea());
		// dibujarDistancia(g, puntoX, puntoY);
	}

	private void dibuixarBarraVida(final Graphics g, final int puntX, final int puntY) {
		g.setColor(Color.red);
		DibuixDebug.dibuixarRectangleFarcit(g, puntX, puntY - 5,
				Constants.COSTAT_SPRITE * (int) vidaActual / vidaMaxima, 2);
	}

	private void dibuixarDistancia(final Graphics g, final int puntX, final int puntY) {

		Point puntJugador = new Point((int) ElementsPrincipals.jugador.obtenirPosicioX(),
				(int) ElementsPrincipals.jugador.obtenirPosicioY());

		Point puntEnemic = new Point((int) posisioX, (int) posicioY);

		Double distancia = CalculadoraDistancia.obtenirDistanciaEntrePunts(puntJugador, puntEnemic);

		DibuixDebug.dibuixarString(g, String.format("%.2f", distancia), puntX, puntY - 8);
	}

	public void establirPosicio(final double posicioX, final double posicioY) {
		this.posisioX = posicioX;
		this.posicioY = posicioY;
	}

	public double obtenirPosicioX() {
		return posisioX;
	}

	public double obtenirPosicioY() {
		return posicioY;
	}

	public int obtenirIdEnemic() {
		return idEnemic;
	}

	public float obtenirVidaActual() {
		return vidaActual;
	}

	public Rectangle obtenirArea() {
		final int puntX = (int) posisioX - (int) ElementsPrincipals.jugador.obtenirPosicioX() + Constants.MARGE_X;
		final int puntY = (int) posicioY - (int) ElementsPrincipals.jugador.obtenirPosicioY() + Constants.MARGE_Y;

		return new Rectangle(puntX, puntY, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);
	}

	public void predreVida(float atacRebut) {
		if (properLament <= 0) {
			lament.reproduir();
			properLament = duracioLament;
		}

		if (vidaActual - atacRebut < 0) {
			vidaActual = 0;

		} else {
			vidaActual -= atacRebut;
		}
	}

	public Rectangle obtenirAreaPosicional() {
		return new Rectangle((int) posisioX, (int) posicioY, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);
	}

	public void canviarProperNode(Node node) {
		// cuidado con posible bug
		properNode = node;
	}

	public Node obtenirProperNode() {
		return properNode;
	}
}
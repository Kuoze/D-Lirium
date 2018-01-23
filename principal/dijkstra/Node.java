package principal.dijkstra;

import java.awt.Point;
import java.awt.Rectangle;

import principal.Constants;

public class Node {
	
	private Point posicio;
	private double distancia;
	
	public Node(final Point posicio, final double distancia) {
		this.posicio = posicio;
		this.distancia = distancia;
	}
	
	public Rectangle obtenirAreaPixels() {
		return new Rectangle(posicio.x * Constants.COSTAT_SPRITE, posicio.y * Constants.COSTAT_SPRITE,
				Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);
	}
	
	public Rectangle obtenirArea() {
		return new Rectangle(posicio.x, posicio.y, Constants.COSTAT_SPRITE, Constants.COSTAT_SPRITE);
	}
	
	public Point obtenirPosicio() {
		return posicio;
	}
	
	public void canviarDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public double obtenirDistancia() {
		return distancia;
	}
}
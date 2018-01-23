package principal.inventari;

import java.awt.Rectangle;

import principal.sprites.Sprite;

public abstract class Objecte {

	protected final int id;
	protected final String nom;
	protected final String descripcio;

	protected int quantitat;
	protected int quantitatMaxima;

	protected Rectangle posicioMenu;
	protected Rectangle posicioFlotant;

	public Objecte(final int id, final String nom, final String descripcio) {
		this.id = id;
		this.nom = nom;
		this.descripcio = descripcio;

		quantitat = 0;
		quantitatMaxima = 99;

		posicioMenu = new Rectangle(0, 0, 0, 0);
		posicioFlotant = new Rectangle(0, 0, 0, 0);
	}

	public Objecte(final int id, final String nom, final String descripcio, final int quantitat) {
		this(id, nom, descripcio);
		if (quantitat <= quantitatMaxima) {
			this.quantitat = quantitat;
		}
	}

	public abstract Sprite obtenirSprite();

	public boolean incrementarQuantitat(final int increment) {
		boolean incrementat = false;

		if (quantitat + increment <= quantitatMaxima) {
			quantitat += increment;
			incrementat = true;
		}

		return incrementat;
	}

	public boolean reduirQuantitat(final int reduccio) {
		boolean reduit = false;

		if (quantitat - reduccio >= 0) {
			quantitat -= reduccio;
			reduit = true;
		}

		return reduit;
	}

	public int obtenirQuantitat() {
		return quantitat;
	}

	public int obtenirID() {
		return id;
	}

	public String obtenirNom() {
		return nom;
	}

	public String obtenirDescripcio() {
		return descripcio;
	}

	public Rectangle obtenirPosicioMenu() {
		return posicioMenu;
	}

	public Rectangle obtenirPosicioFlotant() {
		return posicioFlotant;
	}

	public void establirPosicioMenu(final Rectangle posicioMenu) {
		this.posicioMenu = posicioMenu;
	}

	public void establirPosicioFlotant(final Rectangle posicioFlotant) {
		this.posicioFlotant = posicioFlotant;
	}
}

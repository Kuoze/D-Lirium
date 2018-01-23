package principal.inventari.armes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import principal.Constants;
import principal.ens.Enemic;
import principal.ens.Jugador;
import principal.inventari.Objecte;
import principal.so.So;
import principal.sprites.FullSprites;
import principal.sprites.Sprite;

public abstract class Arma extends Objecte {

	public So soTret;

	public static FullSprites fullArmes = new FullSprites(Constants.RUTA_ARMES, Constants.COSTAT_SPRITE, false);

	protected int atacMinim;
	protected int atacMaxim;
	protected boolean automatica;
	protected boolean penetrant;
	protected double atacsPerSegon;
	protected int actualitzacionsPerProperAtac;

	public Arma(final int id, final String nom, final String descripcio, final int atacMinim,
			final int atacMaxim, final boolean automatica, final boolean penetrant, final double atacsPerSegon,
			final String rutaSoTret) {

		super(id, nom, descripcio);

		this.atacMinim = atacMinim;
		this.atacMaxim = atacMaxim;
		this.automatica = automatica;
		this.penetrant = penetrant;
		this.atacsPerSegon = atacsPerSegon;
		this.actualitzacionsPerProperAtac = 0;
		this.soTret = new So(rutaSoTret);
	}

	public abstract ArrayList<Rectangle> obtenirAbast(final Jugador jugador);

	public void actualitzar() {
		if (actualitzacionsPerProperAtac > 0) {
			actualitzacionsPerProperAtac--;
		}
	}

	public void atacar(final ArrayList<Enemic> enemics) {

		if (enemics.isEmpty() || actualitzacionsPerProperAtac > 0) {
			return;
		}

		if (actualitzacionsPerProperAtac > 0) {
			return;
		}
		actualitzacionsPerProperAtac = (int) (atacsPerSegon * 60);

		soTret.reproduir();

		if (enemics.isEmpty()) {
			return;
		}

		float atacActual = obtenirMitjaAtac();

		for (Enemic enemic : enemics) {
			enemic.predreVida(atacActual);
		}
	}

	private float obtenirMitjaAtac() {
		Random r = new Random();

		return r.nextInt(atacMaxim - atacMinim) + atacMinim;
	}

	public Sprite obtenirSprite() {
		return fullArmes.obtenirSprite(id - 500);
	}

	public boolean esAutomatica() {
		return automatica;
	}

	public boolean esPenetrant() {
		return penetrant;
	}
}
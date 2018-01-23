package principal.ens;

import principal.inventari.armes.Arma;

public class EmmagatzematgeEquip {

	private Arma arma;

	public EmmagatzematgeEquip(final Arma arma1) {
		this.arma = arma1;
	}

	public Arma obtenirArma1() {
		return arma;
	}

	public void canviarArma1(final Arma arma1) {
		this.arma = arma1;
	}
}
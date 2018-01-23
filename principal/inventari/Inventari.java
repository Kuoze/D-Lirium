package principal.inventari;

import java.util.ArrayList;

import principal.inventari.armes.Arma;
import principal.inventari.consumibles.Consumible;

public class Inventari {

	public final ArrayList<Objecte> objectes;

	public Inventari() {
		objectes = new ArrayList<Objecte>();
	}

	public void recollirObjecte(final ContenidorObjectes co) {
		for (Objecte objecte : co.obtenirObjectes()) {
			if (existeixObjecte(objecte)) {
				incrementarObjecte(objecte, objecte.obtenirQuantitat());
			} else {
				objectes.add(objecte);
			}
		}
	}
	
	public void recollirObjectes(final ObjecteUnicTiled objecteUnic) {
		if (existeixObjecte(objecteUnic.obtenirObjecte())) {
			incrementarObjecte(objecteUnic.obtenirObjecte(), objecteUnic.obtenirObjecte().obtenirQuantitat());
		} else {
			objectes.add(objecteUnic.obtenirObjecte());
		}
	}

	public boolean incrementarObjecte(final Objecte objecte, final int quantitat) {
		boolean incrementat = false;

		for (Objecte objecteActual : objectes) {
			if (objecteActual.obtenirID() == objecte.obtenirID()) {
				objecteActual.incrementarQuantitat(quantitat);
				incrementat = true;
				break;
			}
		}

		return incrementat;
	}

	public boolean existeixObjecte(final Objecte objecte) {
		boolean existeix = false;

		for (Objecte objecteActual : objectes) {
			if (objecteActual.obtenirID() == objecte.obtenirID()) {
				existeix = true;
				break;
			}
		}

		return existeix;
	}

	public ArrayList<Objecte> obtenirConsumibles() {
		ArrayList<Objecte> consumibles = new ArrayList<Objecte>();

		for (Objecte objecte : objectes) {
			if (objecte instanceof Consumible) {
				consumibles.add(objecte);
			}
		}

		return consumibles;
	}

	public ArrayList<Objecte> obtenirArmes() {
		ArrayList<Objecte> armes = new ArrayList<Objecte>();

		for (Objecte objecte : objectes) {
			if (objecte instanceof Arma) {
				armes.add(objecte);
			}
		}

		return armes;
	}

	public Objecte obtenirObjecte(final int id) {
		for (Objecte objecteActual : objectes) {
			if (objecteActual.obtenirID() == id) {
				return objecteActual;
			}
		}

		return null;
	}
}
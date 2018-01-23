package principal.inventari;

import principal.inventari.armes.Desarmat;
import principal.inventari.armes.Pistola;
import principal.inventari.consumibles.Consumible;

public class RegistreObjectes {

	public static Objecte obtenerObjeto(final int idObjecte) {

		Objecte objecte = null;

		switch (idObjecte) {
		// 0-499: objectes consumibles
		case 0:
			objecte = new Consumible(idObjecte, "Poma vermella", "");
			break;
		case 1:
			objecte = new Consumible(idObjecte, "Poma groga", "");
			break;
		case 2:
			objecte = new Consumible(idObjecte, "Pastanaga", "");
			break;
		case 3:
			objecte = new Consumible(idObjecte, "Gal·leta", "");
			break;
		case 4:
			objecte = new Consumible(idObjecte, "Llimona ", "");
			break;
		case 5:
			objecte = new Consumible(idObjecte, "Fruita verda", "");
			break;
		// 500 - 599: armes
		case 500:
			objecte = new Pistola(idObjecte, "Default Black", "", 1, 3, false, false, 0.4);
			break;
		case 599:
			objecte = new Desarmat(idObjecte, "Desarmat", "", 0, 0);
			break;
		}

		return objecte;

	}
}
package principal.ens;

public class RegistreEnemics {
	public static Enemic obtenirEnemic(final int idEnemic) {
		Enemic enemic = null;

		switch (idEnemic) {
		case 1:
			enemic = new Zombie(idEnemic, "Zombie", 10, "/sons/rugitZombie.wav");
			break;
		}

		return enemic;
	}
}
package principal.control;

public class Tecla {

	private boolean pulsada = false;
	private long darreraPulsacio = System.nanoTime();

	public void teclaPulsada() {
		pulsada = true;
		darreraPulsacio = System.nanoTime();
	}

	public void teclaAlliberada() {
		pulsada = false;
	}

	public boolean estaPulsada() {
		return pulsada;
	}

	public long obtenirDarreraPulsacio() {
		return darreraPulsacio;
	}
}
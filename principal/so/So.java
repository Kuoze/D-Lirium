package principal.so;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import principal.eines.CarregadorRecursos;

public class So {

	final private Clip so;

	public So(final String ruta) {
		so = CarregadorRecursos.carregarSo(ruta);
	}
	
	public void reproduir() {
		so.stop();
		so.flush();
		so.setMicrosecondPosition(0);
		so.start();
	}
	
	public void repetir() {
		so.stop();
		so.flush();
		so.setMicrosecondPosition(0);
		
		so.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public long obtenirDuracio() {
		return so.getMicrosecondLength();
	}
}
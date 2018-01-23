package principal.grafics;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import principal.Constants;
import principal.eines.CarregadorRecursos;

public class Finestra extends JFrame {

	private static final long serialVersionUID = 5979421777239930009L;

	private String titol;
	private final ImageIcon icona;

	public Finestra(final String titol, final SuperficieDibuix sd) {
		this.titol = titol;

		BufferedImage imatge = CarregadorRecursos.cargarImagenCompatibleTranslucida(Constants.RUTA_ICONA_FINESTRA);
		this.icona = new ImageIcon(imatge);

		configurarFinestra(sd);
	}

	private void configurarFinestra(final SuperficieDibuix sd) {
		setTitle(titol);
		setIconImage(icona.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// setIconImage();
		setLayout(new BorderLayout());
		add(sd, BorderLayout.CENTER);
		setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
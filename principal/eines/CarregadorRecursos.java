package principal.eines;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class CarregadorRecursos {

	public static BufferedImage carregarImatgeCompatibleOpaca(final String ruta) {
		Image imatge = null;

		try {
			imatge = ImageIO.read(ClassLoader.class.getResource(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		BufferedImage imatgeAccelerada = gc.createCompatibleImage(imatge.getWidth(null), imatge.getHeight(null),
				Transparency.OPAQUE);

		Graphics g = imatgeAccelerada.getGraphics();
		g.drawImage(imatge, 0, 0, null);
		g.dispose();

		return imatgeAccelerada;
	}

	public static BufferedImage cargarImagenCompatibleTranslucida(final String ruta) {
		Image imatge = null;

		try {
			imatge = ImageIO.read(ClassLoader.class.getResource(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		BufferedImage imatgeAccelerada = gc.createCompatibleImage(imatge.getWidth(null), imatge.getHeight(null),
				Transparency.TRANSLUCENT);

		Graphics g = imatgeAccelerada.getGraphics();
		g.drawImage(imatge, 0, 0, null);
		g.dispose();

		return imatgeAccelerada;
	}

	public static String llegirFitxerText(final String ruta) {
		String contingut = "";

		InputStream entradaBytes = ClassLoader.class.getResourceAsStream(ruta);
		BufferedReader lector = new BufferedReader(new InputStreamReader(entradaBytes));

		String linia;

		try {
			while ((linia = lector.readLine()) != null) {
				contingut += linia;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (entradaBytes != null) {
					entradaBytes.close();
				}
				if (lector != null) {
					lector.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return contingut;
	}

	public static Font carregarFont(final String ruta) {
		Font font = null;

		InputStream entradaBytes = ClassLoader.class.getResourceAsStream(ruta);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, entradaBytes);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		font = font.deriveFont(12f);

		return font;
	}

	public static Clip carregarSo(final String ruta) {
		Clip clip = null;

		try {
			InputStream is = ClassLoader.class.getResourceAsStream(ruta);
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clip;
	}

	public static Clip carregarSoCanviarVolum(final String ruta, final float reduccioVolumDecibels) {
		Clip clip = null;

		try {
			InputStream is = ClassLoader.class.getResourceAsStream(ruta);
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-reduccioVolumDecibels);
			// NO FUNCIONA AMB OPENJDK Y PULSEAUDIO EN NUCLIS UBUNTU
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clip;
	}
}
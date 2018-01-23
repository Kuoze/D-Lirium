package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import principal.so.So;

public class Teclat implements KeyListener {

	So bang = new So("/sons/tret.wav");

	public Tecla amunt = new Tecla();
	public Tecla avall = new Tecla();
	public Tecla esquerra = new Tecla();
	public Tecla dreta = new Tecla();

	public boolean atacant = false;
	public boolean recollint = false;
	public boolean corrent = false;
	public boolean debug = false;
	public boolean inventariActiu = false;

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			amunt.teclaPulsada();
			break;
		case KeyEvent.VK_S:
			avall.teclaPulsada();
			break;
		case KeyEvent.VK_A:
			esquerra.teclaPulsada();
			break;
		case KeyEvent.VK_D:
			dreta.teclaPulsada();
			break;
		case KeyEvent.VK_E:
			recollint = true;
			break;
		case KeyEvent.VK_SHIFT:
			corrent = true;
			break;
		case KeyEvent.VK_F1:
			debug = !debug;
			break;
		case KeyEvent.VK_I:
			inventariActiu = !inventariActiu;
			break;
		case KeyEvent.VK_SPACE:
			atacant = true;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			amunt.teclaAlliberada();
			break;
		case KeyEvent.VK_S:
			avall.teclaAlliberada();
			break;
		case KeyEvent.VK_A:
			esquerra.teclaAlliberada();
			break;
		case KeyEvent.VK_D:
			dreta.teclaAlliberada();
			break;
		case KeyEvent.VK_E:
			recollint = false;
			break;
		case KeyEvent.VK_SHIFT:
			corrent = false;
			break;
		case KeyEvent.VK_SPACE:
			atacant = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}
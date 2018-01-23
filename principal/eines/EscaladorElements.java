package principal.eines;

import java.awt.Point;
import java.awt.Rectangle;

import principal.Constants;

public class EscaladorElements {

	public static Rectangle escalarRectangleAmunt(final Rectangle r) {
		return new Rectangle((int) (r.x * Constants.FACTOR_ESCALAT_X), (int) (r.y * Constants.FACTOR_ESCALAT_Y),
				(int) (r.width * Constants.FACTOR_ESCALAT_X), (int) (r.height * Constants.FACTOR_ESCALAT_Y));
	}

	public static Point escalarPuntAvall(final Point p) {
		return new Point((int) (p.x / Constants.FACTOR_ESCALAT_X), (int) (p.y / Constants.FACTOR_ESCALAT_Y));
	}

	public static Point escalarPuntAmunt(final Point p) {
		return new Point((int) (p.x * Constants.FACTOR_ESCALAT_X), (int) (p.y * Constants.FACTOR_ESCALAT_Y));
	}
}
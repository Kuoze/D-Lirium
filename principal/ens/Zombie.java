package principal.ens;


import principal.Constants;
import principal.eines.DibuixDebug;
import principal.so.So;
import principal.sprites.FullSprites;

import java.awt.*;

public class Zombie extends Enemic {

    private static FullSprites fullZombie;

    public Zombie(final int idEnemic, final String nom, final int vidaMaxima, final String rutaLament) {
        super(idEnemic, nom, vidaMaxima, rutaLament);

        if (fullZombie == null) {
            fullZombie = new FullSprites(Constants.RUTA_ENEMICS + idEnemic + ".png",
                    Constants.COSTAT_SPRITE, false);
        }
    }

    public void dibuixar(final Graphics g, final int puntX, final int puntY) {
        DibuixDebug.dibuixarImatge(g, fullZombie.obtenirSprite(0).obtenirImatge(), puntX, puntY);
        super.dibuixar(g, puntX, puntY);
    }
}
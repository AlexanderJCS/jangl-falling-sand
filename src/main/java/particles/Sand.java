package particles;

import jangl.coords.Coords;
import jangl.coords.PixelCoords;

public class Sand extends MovableParticle {
    public Sand(int x, int y) {
        super(x, y,
                new float[]{0.9f, 0.9f, 0.2f, 1},
                new Coords[]{
                        new PixelCoords(0, 1),
                        new PixelCoords(-1, 1),
                        new PixelCoords(1, 1)
                },
                true
        );
    }
}

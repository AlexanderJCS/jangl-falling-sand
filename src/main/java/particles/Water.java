package particles;

import jangl.coords.Coords;
import jangl.coords.PixelCoords;

public class Water extends MovableParticle {
    public Water(int x, int y) {
        super(x, y,
                new float[]{0.2f, 0.73f, 0.9f, 1},
                new Coords[]{
                        new PixelCoords(0, 1),
                        new PixelCoords(-1, 0),
                        new PixelCoords(1, 0)
                },
                true
        );
    }
}

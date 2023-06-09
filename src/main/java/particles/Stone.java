package particles;

import jangl.color.ColorFactory;
import jangl.coords.Coords;
import jangl.coords.NDCoords;

public class Stone extends MovableParticle {
    public Stone(int x, int y) {
        super(
                x, y,
                ColorFactory.fromNormalized(0.4f, 0.4f, 0.4f, 1),
                new Coords[]{new NDCoords(0, 1)},
                false
        );
    }
}

package particles;

import game.Particles;
import jangl.color.ColorFactory;

public class Barrier extends Particle {
    public Barrier(int x, int y) {
        super(x, y, ColorFactory.fromNormalized(0, 0, 0, 1));
    }

    @Override
    public void update(Particles particles) {
        // ignore
    }
}

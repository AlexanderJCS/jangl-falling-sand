package particles;

import game.Particles;
import jangl.color.ColorFactory;

public class Air extends Particle {
    public Air(int x, int y) {
        super(x, y, ColorFactory.fromNormalized(0, 0, 0, 0));
    }

    @Override
    public void update(Particles particles) {
        // ignore
    }
}

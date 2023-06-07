package particles;

import game.Particles;

public class Air extends Particle {
    public Air(int x, int y) {
        super(x, y, new float[4]);
    }

    @Override
    public void update(Particles particles) {
        // ignore
    }
}

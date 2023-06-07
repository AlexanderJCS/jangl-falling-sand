package particles;

import game.Particles;

public class Barrier extends Particle {
    public Barrier(int x, int y) {
        super(x, y, new float[]{0, 0, 0, 1});
    }

    @Override
    public void update(Particles particles) {
        // ignore
    }
}

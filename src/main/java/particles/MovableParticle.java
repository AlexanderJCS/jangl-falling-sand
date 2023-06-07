package particles;

import game.Particles;
import jangl.coords.Coords;

public class MovableParticle extends Particle {
    private final Coords[] deltas;
    private final boolean randomize;

    public MovableParticle(int x, int y, float[] rgba, Coords[] deltas, boolean randomize) {
        super(x, y, rgba);

        this.deltas = deltas;
        this.randomize = randomize;
    }

    private void randomize() {
        boolean swap = Math.random() > 0.5;

        if (swap) {
            Coords temp = this.deltas[1];
            this.deltas[1] = this.deltas[2];
            this.deltas[2] = temp;
        }
    }

    private void move(Particles particles) {
        for (Coords delta : this.deltas) {
            try {
                if (particles.getParticleAt(this.x + (int) delta.x, this.y + (int) delta.y) == null) {
                    this.x += delta.x;
                    this.y += delta.y;

                    break;
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }

    @Override
    public void update(Particles particles) {
        // Prevent the particle from going out of bounds
        if (this.x >= particles.getWidth() || this.x < 0 ||
                this.y >= particles.getHeight() - 1 || this.y < 0) {

            return;
        }

        // If the second and third indices should be random in order
        // This is useful for making sure water and sand doesn't have a directional bias when the space under
        // the particle is not empty
        if (this.randomize) {
            this.randomize();
        }

        this.move(particles);
    }
}

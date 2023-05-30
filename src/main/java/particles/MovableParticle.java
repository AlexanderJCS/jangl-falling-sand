package particles;

import jangl.coords.Coords;

public class MovableParticle extends Particle {
    private final Coords[] deltas;
    private final boolean randomize;

    public MovableParticle(int x, int y, float[] rgba, Coords[] deltas, boolean randomize) {
        super(x, y, rgba);

        this.deltas = deltas;
        this.randomize = randomize;
    }

    @Override
    public void update(Particles particles) {
        /*
         * This code is garbage, but I can't be bothered fixing it right now
         * It would benefit from extraction
         */

        if (this.x >= particles.getWidth() || this.x < 0 ||
                this.y >= particles.getHeight() - 1 || this.y < 0) {

            return;
        }

        boolean swapped = false;
        if (this.randomize) {
            boolean swap = Math.random() > 0.5;

            if (swap) {
                swapped = true;
                Coords temp = this.deltas[1];
                this.deltas[1] = this.deltas[2];
                this.deltas[2] = temp;
            }
        }


        for (Coords delta : this.deltas) {
            try {
                if (particles.getParticleAt(this.x + (int) delta.x, this.y + (int) delta.y) == null) {
                    this.x += delta.x;
                    this.y += delta.y;

                    break;
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }

        if (swapped) {
            Coords temp = this.deltas[1];
            this.deltas[1] = this.deltas[2];
            this.deltas[2] = temp;
        }
    }
}

package particles;

import game.Particles;
import jangl.color.Color;

public abstract class Particle {
    protected int x, y;
    protected Color color;

    public Particle(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Color getColor() {
        return this.color;
    }

    public abstract void update(Particles particles);
}

package particles;

public abstract class Particle {
    protected int x, y;
    protected float[] rgba;

    public Particle(int x, int y, float[] rgba) {
        this.x = x;
        this.y = y;
        this.rgba = rgba;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public float[] getRGBA() {
        return this.rgba;
    }

    public abstract void update(Particles particles);
}

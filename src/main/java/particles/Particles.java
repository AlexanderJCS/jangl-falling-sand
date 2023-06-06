package particles;

import jangl.coords.NDCoords;
import jangl.coords.PixelCoords;
import jangl.graphics.MutableTexture;
import jangl.graphics.Texture;
import jangl.io.Window;
import jangl.shapes.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Particles {
    private final MutableTexture mutableTexture;
    private final Rect rect;
    private final Particle[][] particles;

    public Particles() {
        this.mutableTexture = new MutableTexture("src/main/resources/black.png");
        this.particles = new Particle[this.getHeight()][this.getWidth()];
        this.rect = new Rect(new NDCoords(-1, 1), 2, 2);
    }

    public void addParticle(Particle particle) {
        this.particles[particle.getY()][particle.getX()] = particle;
        this.mutableTexture.setPixelAt(particle.getX(), particle.getY(), particle.getRGBA());
    }

    public PixelCoords getPixelCoords(NDCoords ndCoords) {
        float x = NDCoords.distXtoPixelCoords(ndCoords.x + 1) * this.getWidth() / Window.getScreenWidth();
        float y = (Window.getScreenHeight() - NDCoords.distYtoPixelCoords(ndCoords.y + 1)) * this.getHeight() / Window.getScreenHeight();

        return new PixelCoords(x, y);
    }

    public Particle getParticleAt(int x, int y) {
        return this.particles[y][x];
    }

    public void update() {
        // Make the column order random for every row to prevent biases in the direction of iteration
        // with certain particles such as water. Before this was added, water would be biased towards the left.
        List<Integer> columnOrder = new ArrayList<>(this.particles[0].length);

        for (int i = 0; i < this.particles[0].length; i++) {
            columnOrder.add(i);
        }

        for (int r = this.particles.length - 1; r >= 0; r--) {
            Collections.shuffle(columnOrder);

            for (int c : columnOrder) {
                Particle particle = this.particles[r][c];

                if (particle == null) {
                    continue;
                }

                particle.update(this);

                if (particle.x != c || particle.y != r) {
                    this.particles[particle.y][particle.x] = particle;
                    this.particles[r][c] = null;
                    this.mutableTexture.setPixelAt(c, r, 0, 0, 0, 1);
                    this.mutableTexture.setPixelAt(particle.x, particle.y, particle.getRGBA());
                }
            }
        }
    }

    public int getWidth() {
        return this.mutableTexture.width;
    }

    public int getHeight() {
        return this.mutableTexture.height;
    }

    public void draw() {
        this.mutableTexture.bind();
        this.rect.draw();
        Texture.unbind();
    }
}
